package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import jdk.jshell.SnippetEvent;
import jdk.jshell.SourceCodeAnalysis;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// Handler value: example.Handler
public class Handler implements RequestHandler<String, CodeResult>{
  @Override
  public CodeResult handleRequest(String event, Context context) {
    return runJavaCode(event);
  }

  public CodeResult runJavaCode(String code)  {

    JShell jShell = JShell.create();
    jShell.addToClasspath(Handler.class.getClassLoader().getResource("classpath/commons-collections4-4.2.jar").getPath());
    jShell.addToClasspath(Handler.class.getClassLoader().getResource("classpath/jackson-databind-2.13.3.jar").getPath());
    jShell.addToClasspath(Handler.class.getClassLoader().getResource("classpath/blockhound-1.0.6.RELEASE.jar").getPath());
    jShell.addToClasspath(Handler.class.getClassLoader().getResource("classpath/reactive-streams-1.0.4.jar").getPath());
    jShell.addToClasspath(Handler.class.getClassLoader().getResource("classpath/reactor-core-3.4.19.jar").getPath());
    jShell.addToClasspath(Handler.class.getClassLoader().getResource("classpath/core-io-2.3.1.jar").getPath());
    jShell.addToClasspath(Handler.class.getClassLoader().getResource("classpath/java-client-3.3.1.jar").getPath());
    jShell.addToClasspath(Handler.class.getClassLoader().getResource("classpath/couchbase-transactions-1.2.4.jar").getPath());

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
        codeResult.setExceptionStackTrace(buffer.toString());
        buffer.reset();
        codeResult.setSuccessful(false);
        break;
      }

      //check if there are any compilation errors
      if(evt.get(0).status() != Snippet.Status.VALID ) {
        List<String> errors = new ArrayList<>();
        jShell.diagnostics(evt.get(0).snippet()).forEach(x -> errors.add(x.getMessage(Locale.ENGLISH)));
        codeResult.setCompilationErrors(errors);
        codeResult.setSuccessful(false);
        break;
      }
    }

    if(hasClass) {
      jShell.eval(" new Program().main(new String[]{});");
    }

    //get the system.out.println output of the code
    codeResult.setOutput(flushOutput(jShell));

    return codeResult;
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