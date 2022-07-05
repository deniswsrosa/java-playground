package com.couchbase.playground;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import jdk.jshell.SnippetEvent;
import jdk.jshell.SourceCodeAnalysis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostHandler implements RequestStreamHandler {
  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    JsonObject event = (JsonObject) JsonParser.parseReader(reader);

    CBCredentials credentials = new Gson().fromJson(event.get("body").getAsString(), CBCredentials.class);
    String key = event.getAsJsonObject("queryStringParameters").get("id").getAsString();
    JsonObject response = runJavaCode(key, credentials);

    OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
    writer.write(response.toString());
    writer.close();
  }

  public JsonObject runJavaCode(String key, CBCredentials credentials)  {

    String code = ExamplesStore.map.get(key).getFullCode();
    code = code.replaceAll(ExamplesStore.CONNECTION_STRING, credentials.getConnectionString() == null ? "\"\"":credentials.getConnectionString())
            .replaceAll(ExamplesStore.BUCKET, credentials.getBucketName() == null ? "\"\"":credentials.getBucketName())
            .replaceAll(ExamplesStore.USERNAME, credentials.getUsername() == null ? "\"\"":credentials.getUsername())
            .replaceAll(ExamplesStore.PASSWORD, credentials.getPassword() == null ? "\"\"":credentials.getPassword());

    JShell jShell = JShell.create();
    jShell.addToClasspath(PostHandler.class.getClassLoader().getResource("classpath/commons-collections4-4.2.jar").getPath());
    jShell.addToClasspath(PostHandler.class.getClassLoader().getResource("classpath/jackson-databind-2.13.3.jar").getPath());
    jShell.addToClasspath(PostHandler.class.getClassLoader().getResource("classpath/blockhound-1.0.6.RELEASE.jar").getPath());
    jShell.addToClasspath(PostHandler.class.getClassLoader().getResource("classpath/reactive-streams-1.0.4.jar").getPath());
    jShell.addToClasspath(PostHandler.class.getClassLoader().getResource("classpath/reactor-core-3.4.19.jar").getPath());
    jShell.addToClasspath(PostHandler.class.getClassLoader().getResource("classpath/core-io-2.3.1.jar").getPath());
    jShell.addToClasspath(PostHandler.class.getClassLoader().getResource("classpath/java-client-3.3.1.jar").getPath());
    jShell.addToClasspath(PostHandler.class.getClassLoader().getResource("classpath/couchbase-transactions-1.2.4.jar").getPath());

    redirectOutput(jShell);

    SourceCodeAnalysis sca = jShell.sourceCodeAnalysis();
    List<String> snippets = new ArrayList<>();
    do {
      SourceCodeAnalysis.CompletionInfo info = sca.analyzeCompletion(code);
      snippets.add(info.source());
      code = info.remaining();
    } while (code.length() > 0);

    CodeResult codeResult = new CodeResult();

    //if the code has a class, it will try to call the main method of the first class.
    boolean hasClass = false;

    for (String snippet: snippets) {

      List<SnippetEvent> evt = jShell.eval(snippet);
      if(!hasClass && evt.get(0).snippet().subKind() == Snippet.SubKind.CLASS_SUBKIND) {
        hasClass = true;

//TODO: grab the name of the class programmatically
//                Field privateField
//                        = Snippet.class.getDeclaredField("unitName");
//                privateField.setAccessible(true);
//                String className = (String)privateField.get(evt.get(0).snippet());
      }


      //check if there are any exceptions while running the code
      if(evt.get(0).exception() != null) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(buffer);
        evt.get(0).exception().printStackTrace(ps);
        codeResult.setException( buffer.toString());
        buffer.reset();
        codeResult.setSuccessful(false);
        break;
      }

      //check if there are any compilation errors
      if(evt.get(0).status() != Snippet.Status.VALID ) {
        List<String> errors = new ArrayList<>();
        jShell.diagnostics(evt.get(0).snippet()).forEach(x -> errors.add(x.getMessage(Locale.ENGLISH)));
        codeResult.setCompilationError(errors.get(0));
        codeResult.setSuccessful(false);
        break;
      }
    }

    if(hasClass) {
      jShell.eval(" new Program().main(new String[]{});");
    }

    //get the system.out.println output of the code
    codeResult.setOutput(flushOutput(jShell));

    JsonObject response = new JsonObject();
    response.addProperty("statusCode", 200);
    response.addProperty("body", new Gson().toJson(codeResult) );

    return response;
  }



  private void redirectOutput(JShell jShell) {
    jShell.eval("import java.io.ByteArrayOutputStream;");
    jShell.eval("import java.io.FileDescriptor;");
    jShell.eval("import java.io.FileOutputStream;");
    jShell.eval("import java.io.PrintStream;");
    jShell.eval("ByteArrayOutputStream buffer = new ByteArrayOutputStream();");
    jShell.eval("PrintStream ps = new PrintStream(buffer);");
    jShell.eval("PrintStream old = System.out");
    jShell.eval( "System.setOut(ps);");
  }

  private String flushOutput(JShell jShell) {
    jShell.eval("System.out.flush();");
    jShell.eval("System.setOut(old);");
    jShell.eval("String bufferOutput = buffer.toString();");
    return jShell.eval("bufferOutput").get(0).value();
  }
}