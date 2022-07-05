package com.couchbase.playground;

import java.util.HashMap;
import java.util.Map;

public class ExamplesStore {

    public static final String CONNECTION_STRING = "\\{credentials-connectionString\\}";
    public static final String USERNAME = "\\{credentials-username\\}";
    public static final String PASSWORD = "\\{credentials-password\\}";
    public static final String BUCKET = "\\{credentials-bucket\\}";

    public static Map<String, CodeExample> map;
    static {
        map = new HashMap<>();

        //################### KV_GET
        map.put("KV_GET",
                new CodeExample(
                "    var bucket = cluster.bucket(\"{credentials-bucket}\");\n" +
                        "    var collection = bucket.defaultCollection();\n" +
                        "\n" +
                        "    try {\n" +
                        "      var result = collection.get(\"airline_10\");\n" +
                        "      System.out.println(result.toString());\n" +
                        "\n" +
                        "    } catch (DocumentNotFoundException ex) {\n" +
                        "      System.out.println(\"Document not found!\");\n" +
                        "    }\n" +
                        "  }"

                        ,"import com.couchbase.client.core.error.DocumentNotFoundException;\n" +
                "import com.couchbase.client.java.*;\n" +
                "import com.couchbase.client.java.kv.*;\n" +
                "\n" +
                "\n" +
                "import com.couchbase.client.java.env.ClusterEnvironment;\n" +
                "import com.couchbase.client.core.deps.io.netty.handler.ssl.util.InsecureTrustManagerFactory;\n" +
                "import com.couchbase.client.core.env.IoConfig;\n" +
                "import com.couchbase.client.core.env.SecurityConfig;\n" +
                "import com.couchbase.client.java.ClusterOptions;\n" +
                "\n" +
                "class Program {\n" +
                "  public static void main(String[] args) {\n" +
                "    ClusterEnvironment env = ClusterEnvironment.builder()\n" +
                "\t\t\t\t.securityConfig(SecurityConfig.enableTls(true)\n" +
                "\t\t\t\t\t\t.trustManagerFactory(InsecureTrustManagerFactory.INSTANCE))\n" +
                "\t\t\t\t.ioConfig(IoConfig.enableDnsSrv(true))\n" +
                "\t\t\t\t.build();\n" +
                "\tCluster cluster = Cluster.connect(\"{credentials-connectionString}\",\n" +
                "      ClusterOptions.clusterOptions(\"{credentials-username}\", \"{credentials-password}\").environment(env)\n" +
                "    );\n" +
                "\n" +
                "    var bucket = cluster.bucket(\"{credentials-bucket}\");\n" +
                "    var collection = bucket.defaultCollection();\n" +
                "\n" +
                "    try {\n" +
                "      var result = collection.get(\"airline_10\");\n" +
                "      System.out.println(result.toString());\n" +
                "\n" +
                "    } catch (DocumentNotFoundException ex) {\n" +
                "      System.out.println(\"Document not found!\");\n" +
                "    }\n" +
                "  }\n" +
                "}\n"));


        //################### QUERY
        map.put("QUERY", new CodeExample(
                "    var query =\n" +
                "        \"SELECT h.name, h.city, h.state \" +\n" +
                "        \"FROM `travel-sample` h \" +\n" +
                "        \"WHERE h.type = 'hotel' \" +\n" +
                "          \"AND h.city = 'Malibu' LIMIT 5;\";\n" +
                "    \n" +
                "    QueryResult result = cluster.query(query);\n" +
                "    for (JsonObject row : result.rowsAsObject()) {\n" +
                "        System.out.println(\"Hotel: \" + row);\n" +
                "    }"


                , "import com.couchbase.client.core.error.DocumentNotFoundException;\n" +
                "import com.couchbase.client.java.*;\n" +
                "import com.couchbase.client.java.kv.*;\n" +
                "import com.couchbase.client.java.json.JsonObject;\n" +
                "import com.couchbase.client.java.query.QueryResult;\n" +
                "\n" +
                "\n" +
                "import com.couchbase.client.java.env.ClusterEnvironment;\n" +
                "import com.couchbase.client.core.deps.io.netty.handler.ssl.util.InsecureTrustManagerFactory;\n" +
                "import com.couchbase.client.core.env.IoConfig;\n" +
                "import com.couchbase.client.core.env.SecurityConfig;\n" +
                "import com.couchbase.client.java.ClusterOptions;\n" +
                "\n" +
                "class Program {\n" +
                "  public static void main(String[] args) {\n" +
                "    ClusterEnvironment env = ClusterEnvironment.builder()\n" +
                "\t\t\t\t.securityConfig(SecurityConfig.enableTls(true)\n" +
                "\t\t\t\t\t\t.trustManagerFactory(InsecureTrustManagerFactory.INSTANCE))\n" +
                "\t\t\t\t.ioConfig(IoConfig.enableDnsSrv(true))\n" +
                "\t\t\t\t.build();\n" +
                "\tCluster cluster = Cluster.connect(\"{credentials-connectionString}\",\n" +
                "      ClusterOptions.clusterOptions(\"{credentials-username}\", \"{credentials-password}\").environment(env)\n" +
                "    );\n" +
                "\n" +
                "    try {\n" +
                "      var query =\n" +
                "        \"SELECT h.name, h.city, h.state \" +\n" +
                "        \"FROM `travel-sample` h \" +\n" +
                "        \"WHERE h.type = 'hotel' \" +\n" +
                "          \"AND h.city = 'Malibu' LIMIT 5;\";\n" +
                "\n" +
                "      QueryResult result = cluster.query(query);\n" +
                "      for (JsonObject row : result.rowsAsObject()) {\n" +
                "        System.out.println(\"Hotel: \" + row);\n" +
                "      }\n" +
                "\n" +
                "    } catch (DocumentNotFoundException ex) {\n" +
                "      System.out.println(\"Document not found!\");\n" +
                "    }\n" +
                "  }\n" +
                "}\n"));




    }


}
