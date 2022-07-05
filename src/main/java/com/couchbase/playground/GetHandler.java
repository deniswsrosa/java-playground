package com.couchbase.playground;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class GetHandler implements RequestStreamHandler {
  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    JsonObject event = (JsonObject) JsonParser.parseReader(reader);

    String key = event.getAsJsonObject("queryStringParameters").get("id").getAsString();

    JsonObject response = new JsonObject();
    response.addProperty("statusCode", 200);
    response.addProperty("body", new Gson().toJson(ExamplesStore.map.get(key)) );
    OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
    writer.write(response.toString());
    writer.close();
  }
}