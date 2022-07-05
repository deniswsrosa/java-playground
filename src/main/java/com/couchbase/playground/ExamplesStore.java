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
        map.put("N1QL_QUERY", new CodeExample(
                "    var query =\n" +
                "        \"SELECT h.name, h.city, h.state \" +\n" +
                "        \"FROM `{credentials-bucket}` h \" +\n" +
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
                "        \"FROM `{credentials-bucket}` h \" +\n" +
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

        //##################### QUERY_NAMED_PARAM

        map.put("N1QL_QUERY_NAMED_PARAM", new CodeExample(
        "    try {\n" +
                "      var query =\n" +
                "        \"SELECT h.name, h.city, h.state \" +\n" +
                "        \"FROM `{credentials-bucket}` h \" +\n" +
                "        \"WHERE h.type = $type \" +\n" +
                "          \"AND h.city = $city LIMIT 5;\";\n" +
                "\n" +
                "      QueryResult result = cluster.query(query,\n" +
                "        queryOptions().parameters(\n" +
                "          JsonObject.create()\n" +
                "            .put(\"type\", \"hotel\")\n" +
                "            .put(\"city\", \"Malibu\")\n" +
                "        ));\n" +
                "      result.rowsAsObject().stream().forEach(\n" +
                "        e-> System.out.println(\n" +
                "          \"Hotel: \"+e.getString(\"name\")+\", \"+e.getString(\"city\"))\n" +
                "      );\n" +
                "\n" +
                "    } catch (CouchbaseException ex) {\n" +
                "      System.out.println(\"Exception: \" + ex.toString());\n" +
                "    }",



                "import com.couchbase.client.core.error.CouchbaseException;\n" +
                        "import com.couchbase.client.java.*;\n" +
                        "import com.couchbase.client.java.kv.*;\n" +
                        "import com.couchbase.client.java.json.JsonObject;\n" +
                        "import com.couchbase.client.java.query.QueryResult;\n" +
                        "import com.couchbase.client.java.query.QueryOptions;\n" +
                        "import static com.couchbase.client.java.query.QueryOptions.queryOptions;\n" +
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
                        "        \"FROM `{credentials-bucket}` h \" +\n" +
                        "        \"WHERE h.type = $type \" +\n" +
                        "          \"AND h.city = $city LIMIT 5;\";\n" +
                        "\n" +
                        "      QueryResult result = cluster.query(query,\n" +
                        "        queryOptions().parameters(\n" +
                        "          JsonObject.create()\n" +
                        "            .put(\"type\", \"hotel\")\n" +
                        "            .put(\"city\", \"Malibu\")\n" +
                        "        ));\n" +
                        "      result.rowsAsObject().stream().forEach(\n" +
                        "        e-> System.out.println(\n" +
                        "          \"Hotel: \"+e.getString(\"name\")+\", \"+e.getString(\"city\"))\n" +
                        "      );\n" +
                        "\n" +
                        "    } catch (CouchbaseException ex) {\n" +
                        "      System.out.println(\"Exception: \" + ex.toString());\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n"
        ));


        //##################### QUERY_NAMED_PARAM
        map.put("N1QL_QUERY_POSIT_PARAM", new CodeExample(
                "    try {\n" +
                        "      var query =\n" +
                        "        \"SELECT h.name, h.city, h.state \" +\n" +
                        "        \"FROM `{credentials-bucket}` h \" +\n" +
                        "        \"WHERE h.type = $1 \" +\n" +
                        "          \"AND h.city = $2 LIMIT 5;\";\n" +
                        "\n" +
                        "      QueryResult result = cluster.query(query,\n" +
                        "        queryOptions().parameters(JsonArray.from(\"hotel\", \"Malibu\"))\n" +
                        "      );\n" +
                        "      result.rowsAsObject().stream().forEach(\n" +
                        "        e-> System.out.println(\n" +
                        "          \"Hotel: \" + e.getString(\"name\") + \", \" + e.getString(\"city\"))\n" +
                        "      );\n" +
                        "\n" +
                        "    } catch (CouchbaseException ex) {\n" +
                        "      System.out.println(\"Exception: \" + ex.toString());\n" +
                        "    }",


                "import com.couchbase.client.core.error.CouchbaseException;\n" +
                        "import com.couchbase.client.java.*;\n" +
                        "import com.couchbase.client.java.kv.*;\n" +
                        "import com.couchbase.client.java.json.JsonArray;\n" +
                        "import com.couchbase.client.java.query.QueryResult;\n" +
                        "import com.couchbase.client.java.query.QueryOptions;\n" +
                        "import static com.couchbase.client.java.query.QueryOptions.queryOptions;\n" +
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
                        "        \"FROM `{credentials-bucket}` h \" +\n" +
                        "        \"WHERE h.type = $1 \" +\n" +
                        "          \"AND h.city = $2 LIMIT 5;\";\n" +
                        "\n" +
                        "      QueryResult result = cluster.query(query,\n" +
                        "        queryOptions().parameters(JsonArray.from(\"hotel\", \"Malibu\"))\n" +
                        "      );\n" +
                        "      result.rowsAsObject().stream().forEach(\n" +
                        "        e-> System.out.println(\n" +
                        "          \"Hotel: \" + e.getString(\"name\") + \", \" + e.getString(\"city\"))\n" +
                        "      );\n" +
                        "\n" +
                        "    } catch (CouchbaseException ex) {\n" +
                        "      System.out.println(\"Exception: \" + ex.toString());\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n"
        ));



        //##################### KV_SUBDOC_LOOKUP
        map.put("KV_SUBDOC_LOOKUP", new CodeExample(
                "    var bucket = cluster.bucket(\"{credentials-bucket}\");\n" +
                        "    var collection = bucket.defaultCollection();\n" +
                        "\n" +
                        "    try {\n" +
                        "      LookupInResult result = collection.lookupIn(\n" +
                        "        \"airport_1254\",\n" +
                        "        Collections.singletonList(get(\"geo.alt\"))\n" +
                        "      );\n" +
                        "\n" +
                        "      var str = result.contentAs(0, String.class);\n" +
                        "      System.out.println(\"Altitude = \" + str);\n" +
                        "\n" +
                        "    } catch (DocumentNotFoundException ex) {\n" +
                        "      System.out.println(\"Document not found!\");\n" +
                        "    }",

                "import com.couchbase.client.core.error.DocumentNotFoundException;\n" +
                        "import com.couchbase.client.java.*;\n" +
                        "import com.couchbase.client.java.kv.LookupInResult;\n" +
                        "import static com.couchbase.client.java.kv.LookupInSpec.get;\n" +
                        "import java.util.Collections;\n" +
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
                        "      ClusterOptions.clusterOptions(\"majaxo7537\", \"{credentials-password}\").environment(env)\n" +
                        "    );\n" +
                        "    var bucket = cluster.bucket(\"{credentials-bucket}\");\n" +
                        "    var collection = bucket.defaultCollection();\n" +
                        "\n" +
                        "    try {\n" +
                        "      LookupInResult result = collection.lookupIn(\n" +
                        "        \"airport_1254\",\n" +
                        "        Collections.singletonList(get(\"geo.alt\"))\n" +
                        "      );\n" +
                        "\n" +
                        "      var str = result.contentAs(0, String.class);\n" +
                        "      System.out.println(\"Altitude = \" + str);\n" +
                        "\n" +
                        "    } catch (DocumentNotFoundException ex) {\n" +
                        "      System.out.println(\"Document not found!\");\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n"
        ));


        //##################### KV_SUBDOC_MUTATE
        map.put("KV_SUBDOC_MUTATE", new CodeExample(
                "    var bucket = cluster.bucket(\"{credentials-bucket}\");\n" +
                        "    var collection = bucket.defaultCollection();\n" +
                        "\n" +
                        "    try {\n" +
                        "      LookupInResult result = collection.lookupIn(\n" +
                        "        \"airline_10\", Collections.singletonList(get(\"country\"))\n" +
                        "      );\n" +
                        "\n" +
                        "      var str = result.contentAs(0, String.class);\n" +
                        "      System.out.println(\"Sub-doc before: \");\n" +
                        "      System.out.println(str);\n" +
                        "\n" +
                        "    } catch (PathNotFoundException e) {\n" +
                        "      System.out.println(\"Sub-doc path not found!\");\n" +
                        "    }\n" +
                        "\n" +
                        "    try {\n" +
                        "      collection.mutateIn(\"airline_10\", Arrays.asList(\n" +
                        "        upsert(\"country\", \"Canada\")\n" +
                        "      ));\n" +
                        "    } catch (PathExistsException e) {\n" +
                        "      System.out.println(\"Sub-doc path exists!\");\n" +
                        "    }\n" +
                        "\n" +
                        "    try {\n" +
                        "      LookupInResult result = collection.lookupIn(\n" +
                        "        \"airline_10\", Collections.singletonList(get(\"country\"))\n" +
                        "      );\n" +
                        "\n" +
                        "      var str = result.contentAs(0, String.class);\n" +
                        "      System.out.println(\"Sub-doc after: \");\n" +
                        "      System.out.println(str);\n" +
                        "\n" +
                        "    } catch (PathNotFoundException e) {\n" +
                        "      System.out.println(\"Sub-doc path not found!\");\n" +
                        "    }",

                "import com.couchbase.client.core.error.subdoc.PathNotFoundException;\n" +
                        "import com.couchbase.client.core.error.subdoc.PathExistsException;\n" +
                        "import com.couchbase.client.java.*;\n" +
                        "import com.couchbase.client.java.kv.LookupInResult;\n" +
                        "import static com.couchbase.client.java.kv.LookupInSpec.get;\n" +
                        "import static com.couchbase.client.java.kv.MutateInSpec.upsert;\n" +
                        "import java.util.Collections;\n" +
                        "import java.util.Arrays;\n" +
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
                        "    var bucket = cluster.bucket(\"{credentials-bucket}\");\n" +
                        "    var collection = bucket.defaultCollection();\n" +
                        "\n" +
                        "    try {\n" +
                        "      LookupInResult result = collection.lookupIn(\n" +
                        "        \"airline_10\", Collections.singletonList(get(\"country\"))\n" +
                        "      );\n" +
                        "\n" +
                        "      var str = result.contentAs(0, String.class);\n" +
                        "      System.out.println(\"Sub-doc before: \");\n" +
                        "      System.out.println(str);\n" +
                        "\n" +
                        "    } catch (PathNotFoundException e) {\n" +
                        "      System.out.println(\"Sub-doc path not found!\");\n" +
                        "    }\n" +
                        "\n" +
                        "    try {\n" +
                        "      collection.mutateIn(\"airline_10\", Arrays.asList(\n" +
                        "        upsert(\"country\", \"Canada\")\n" +
                        "      ));\n" +
                        "    } catch (PathExistsException e) {\n" +
                        "      System.out.println(\"Sub-doc path exists!\");\n" +
                        "    }\n" +
                        "\n" +
                        "    try {\n" +
                        "      LookupInResult result = collection.lookupIn(\n" +
                        "        \"airline_10\", Collections.singletonList(get(\"country\"))\n" +
                        "      );\n" +
                        "\n" +
                        "      var str = result.contentAs(0, String.class);\n" +
                        "      System.out.println(\"Sub-doc after: \");\n" +
                        "      System.out.println(str);\n" +
                        "\n" +
                        "    } catch (PathNotFoundException e) {\n" +
                        "      System.out.println(\"Sub-doc path not found!\");\n" +
                        "    }\n" +
                        "\n" +
                        "  }\n" +
                        "}\n"
        ));

        //##################### KV_TRANSACTION
        map.put("KV_TRANSACTION", new CodeExample(
                "",
                "import com.couchbase.client.core.cnc.Event;\n" +
                        "import com.couchbase.client.core.error.DocumentNotFoundException;\n" +
                        "import com.couchbase.client.java.Bucket;\n" +
                        "import com.couchbase.client.java.Cluster;\n" +
                        "import com.couchbase.client.java.Collection;\n" +
                        "import com.couchbase.client.java.json.JsonArray;\n" +
                        "import com.couchbase.client.java.json.JsonObject;\n" +
                        "import com.couchbase.transactions.TransactionDurabilityLevel;\n" +
                        "import com.couchbase.transactions.TransactionGetResult;\n" +
                        "import com.couchbase.transactions.Transactions;\n" +
                        "import com.couchbase.transactions.config.TransactionConfigBuilder;\n" +
                        "import com.couchbase.transactions.error.TransactionCommitAmbiguous;\n" +
                        "import com.couchbase.transactions.error.TransactionFailed;\n" +
                        "import com.couchbase.transactions.log.TransactionEvent;\n" +
                        "\n" +
                        "import java.time.Duration;\n" +
                        "import java.util.UUID;\n" +
                        "import java.util.concurrent.atomic.AtomicReference;\n" +
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
                        "    var bucketName = \"{credentials-bucket}\";\n" +
                        "\n" +
                        "    ClusterEnvironment env = ClusterEnvironment.builder()\n" +
                        "\t\t\t\t.securityConfig(SecurityConfig.enableTls(true)\n" +
                        "\t\t\t\t\t\t.trustManagerFactory(InsecureTrustManagerFactory.INSTANCE))\n" +
                        "\t\t\t\t.ioConfig(IoConfig.enableDnsSrv(true))\n" +
                        "\t\t\t\t.build();\n" +
                        "\tCluster cluster = Cluster.connect(\"{credentials-connectionString}\",ClusterOptions.clusterOptions(\"{credentials-username}\", \"{credentials-password}\").environment(env));\n" +
                        "    Bucket bucket = cluster.bucket(bucketName);\n" +
                        "    Collection collection = bucket.defaultCollection();\n" +
                        "    bucket.waitUntilReady(Duration.ofSeconds(30));\n" +
                        "\n" +
                        "    TransactionConfigBuilder config = TransactionConfigBuilder.create()\n" +
                        "        .durabilityLevel(TransactionDurabilityLevel.MAJORITY_AND_PERSIST_TO_ACTIVE);\n" +
                        "    Transactions transactions = Transactions.create(cluster, config);\n" +
                        "\n" +
                        "    var route1Id = \"route_46586\";\n" +
                        "    var route2Id = \"route_35816\";\n" +
                        "\n" +
                        "    // Pre-transaction\n" +
                        "    JsonObject preroute1 = collection.get(route1Id).contentAsObject();\n" +
                        "    JsonObject preroute2 = collection.get(route2Id).contentAsObject();\n" +
                        "\n" +
                        "    System.out.printf(\n" +
                        "        \"Before transaction - got %s's details: source airport: %s, destination airport: %s, airline: %s:%s \\n\",\n" +
                        "        route1Id, preroute1.getString(\"sourceairport\"), preroute1.getString(\"destinationairport\"),\n" +
                        "        preroute1.getString(\"airline\"), preroute1.getString(\"airlineid\"));\n" +
                        "    System.out.printf(\n" +
                        "        \"Before transaction - got %s's details: source airport: %s, destination airport: %s, airline: %s:%s \\n\",\n" +
                        "        route2Id, preroute2.getString(\"sourceairport\"), preroute2.getString(\"destinationairport\"),\n" +
                        "        preroute2.getString(\"airline\"), preroute2.getString(\"airlineid\"));\n" +
                        "\n" +
                        "    // In-transaction\n" +
                        "    try {\n" +
                        "      String swapId = swapAirlineRoute(transactions, collection, route1Id, route2Id);\n" +
                        "      if (swapId != null) {\n" +
                        "        JsonObject swapRecord = collection.get(swapId).contentAsObject();\n" +
                        "        System.out.println(\"After transaction - swap route record: \" + swapRecord);\n" +
                        "      }\n" +
                        "    } catch (RuntimeException err) {\n" +
                        "      System.err.println(\"Transaction failed with: \" + err.toString());\n" +
                        "    }\n" +
                        "\n" +
                        "    // Post-transaction\n" +
                        "    JsonObject postroute1 = collection.get(route1Id).contentAsObject();\n" +
                        "    JsonObject postroute2 = collection.get(route2Id).contentAsObject();\n" +
                        "\n" +
                        "    System.out.printf(\n" +
                        "        \"After transaction - got %s's details: source airport: %s, destination airport: %s, airline: %s:%s \\n\",\n" +
                        "        route1Id, postroute1.getString(\"sourceairport\"), postroute1.getString(\"destinationairport\"),\n" +
                        "        postroute1.getString(\"airline\"), postroute1.getString(\"airlineid\"));\n" +
                        "    System.out.printf(\n" +
                        "        \"After transaction - got %s's details: source airport: %s, destination airport: %s, airline: %s:%s \\n\",\n" +
                        "        route2Id, postroute2.getString(\"sourceairport\"), postroute2.getString(\"destinationairport\"),\n" +
                        "        postroute2.getString(\"airline\"), postroute2.getString(\"airlineid\"));\n" +
                        "\n" +
                        "    System.exit(0);\n" +
                        "  }\n" +
                        "\n" +
                        "  private static String swapAirlineRoute(Transactions transactions, Collection collection,\n" +
                        "    String route1Id,\n" +
                        "    String route2Id) {\n" +
                        "    AtomicReference<String> swapId = new AtomicReference<>();\n" +
                        "    try {\n" +
                        "      transactions.run(ctx -> {\n" +
                        "        TransactionGetResult route1 = ctx.get(collection, route1Id);\n" +
                        "        TransactionGetResult route2 = ctx.get(collection, route2Id);\n" +
                        "\n" +
                        "        JsonObject route1Content = route1.contentAsObject();\n" +
                        "        JsonObject route2Content = route2.contentAsObject();\n" +
                        "\n" +
                        "        System.out.printf(\"In transaction - got %s's details: %s\\n\", route1Id,\n" +
                        "            route1Content.getString(\"airlineid\"));\n" +
                        "        System.out.printf(\"In transaction - got %s's details: %s\\n\", route2Id,\n" +
                        "            route2Content.getString(\"airlineid\"));\n" +
                        "\n" +
                        "        String airline1 = route1Content.getString(\"airline\");\n" +
                        "        String airlineid1 = route1Content.getString(\"airlineid\");\n" +
                        "        JsonArray schedule1 = route1Content.getArray(\"schedule\");\n" +
                        "\n" +
                        "        String airline2 = route2Content.getString(\"airline\");\n" +
                        "        String airlineid2 = route2Content.getString(\"airlineid\");\n" +
                        "        JsonArray schedule2 = route2Content.getArray(\"schedule\");\n" +
                        "\n" +
                        "        JsonObject swapRecord = JsonObject.create().put(\"from_route\", route1Id).put(\"to_route\", route2Id)\n" +
                        "            .put(\"from_airline\", airline1).put(\"from_airlineid\", airlineid1).put(\"to_airline\", airline2)\n" +
                        "            .put(\"to_airlineid\", airlineid2).put(\"type\", \"Transactions_History\")\n" +
                        "            .put(\"created\", new java.util.Date().toString());\n" +
                        "\n" +
                        "        swapId.set(UUID.randomUUID().toString());\n" +
                        "\n" +
                        "        ctx.insert(collection, swapId.get(), swapRecord);\n" +
                        "\n" +
                        "        System.out.println(\"In transaction - creating a record of swap routes with UUID: \" + swapId.get());\n" +
                        "\n" +
                        "        route1Content.put(\"airline\", airline2);\n" +
                        "        route1Content.put(\"airlineid\", airlineid2);\n" +
                        "        route1Content.put(\"schedule\", schedule2);\n" +
                        "\n" +
                        "        route2Content.put(\"airline\", airline1);\n" +
                        "        route2Content.put(\"airlineid\", airlineid1);\n" +
                        "        route2Content.put(\"schedule\", schedule1);\n" +
                        "\n" +
                        "        System.out.printf(\"In transaction - changing %s's airline to: %s\\n\", route1Id,\n" +
                        "            route1Content.getString(\"airlineid\"));\n" +
                        "        System.out.printf(\"In transaction - changing %s's airline to: %s\\n\", route2Id,\n" +
                        "            route2Content.getString(\"airlineid\"));\n" +
                        "\n" +
                        "        ctx.replace(route1, route1Content);\n" +
                        "        ctx.replace(route2, route2Content);\n" +
                        "\n" +
                        "        System.out.println(\"In transaction - about to commit\");\n" +
                        "        ctx.commit();\n" +
                        "      });\n" +
                        "\n" +
                        "      return swapId.get();\n" +
                        "    } catch (TransactionCommitAmbiguous err) {\n" +
                        "      System.err.println(\"Transaction \" + err.result().transactionId() + \" possibly committed:\");\n" +
                        "      err.result().log().logs().forEach(System.err::println);\n" +
                        "    } catch (TransactionFailed err) {\n" +
                        "      if (err.getCause() instanceof DocumentNotFoundException) {\n" +
                        "        new IllegalArgumentException(\"The specified route wasn't found\");\n" +
                        "      } else {\n" +
                        "        System.err.println(\"Transaction \" + err.result().transactionId() + \" did not reach commit:\");\n" +
                        "        err.result().log().logs().forEach(System.err::println);\n" +
                        "      }\n" +
                        "    }\n" +
                        "\n" +
                        "    return null;\n" +
                        "  }\n" +
                        "}\n"
        ));


        //##################### N1QL_TRANSACTION
        map.put("N1QL_TRANSACTION", new CodeExample(
                "",
                "import com.couchbase.client.core.error.DocumentNotFoundException;\n" +
                        "import com.couchbase.client.java.Bucket;\n" +
                        "import com.couchbase.client.java.Cluster;\n" +
                        "import com.couchbase.client.java.Collection;\n" +
                        "import com.couchbase.client.java.json.JsonArray;\n" +
                        "import com.couchbase.client.java.json.JsonObject;\n" +
                        "import com.couchbase.client.java.query.QueryResult;\n" +
                        "import com.couchbase.transactions.TransactionDurabilityLevel;\n" +
                        "import com.couchbase.transactions.TransactionGetResult;\n" +
                        "import com.couchbase.transactions.Transactions;\n" +
                        "import com.couchbase.transactions.config.TransactionConfigBuilder;\n" +
                        "import com.couchbase.transactions.error.TransactionCommitAmbiguous;\n" +
                        "import com.couchbase.transactions.error.TransactionFailed;\n" +
                        "import com.couchbase.transactions.log.TransactionEvent;\n" +
                        "\n" +
                        "import java.time.Duration;\n" +
                        "import java.util.UUID;\n" +
                        "import java.util.concurrent.atomic.AtomicReference;\n" +
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
                        "    var bucketName = \"{credentials-bucket}\";\n" +
                        "\n" +
                        "    ClusterEnvironment env = ClusterEnvironment.builder()\n" +
                        "\t\t\t\t.securityConfig(SecurityConfig.enableTls(true)\n" +
                        "\t\t\t\t\t\t.trustManagerFactory(InsecureTrustManagerFactory.INSTANCE))\n" +
                        "\t\t\t\t.ioConfig(IoConfig.enableDnsSrv(true))\n" +
                        "\t\t\t\t.build();\n" +
                        "\tCluster cluster = Cluster.connect(\"{credentials-connectionString}\",ClusterOptions.clusterOptions(\"{credentials-username}\", \"{credentials-password}\").environment(env));\n" +
                        "    Bucket bucket = cluster.bucket(bucketName);\n" +
                        "    bucket.waitUntilReady(Duration.ofSeconds(30));\n" +
                        "\n" +
                        "    TransactionConfigBuilder config = TransactionConfigBuilder.create()\n" +
                        "        .durabilityLevel(TransactionDurabilityLevel.MAJORITY_AND_PERSIST_TO_ACTIVE)\n" +
                        "        .expirationTime(Duration.ofSeconds(60));\n" +
                        "    Transactions transactions = Transactions.create(cluster, config);\n" +
                        "\n" +
                        "    var route1Id = \"46586\";\n" +
                        "    var route2Id = \"35816\";\n" +
                        "\n" +
                        "    // Pre-transaction\n" +
                        "    JsonObject preroute1 = getDocByWhere(cluster, bucketName, \"type='route' and id=\"+route1Id);\n" +
                        "    JsonObject preroute2 = getDocByWhere(cluster, bucketName, \"type='route' and id=\"+route2Id);\n" +
                        "\n" +
                        "    System.out.printf(\n" +
                        "        \"Before transaction - got %s's details: source airport: %s, destination airport: %s, airline: %s:%s \\n\",\n" +
                        "        route1Id, preroute1.getString(\"sourceairport\"), preroute1.getString(\"destinationairport\"),\n" +
                        "        preroute1.getString(\"airline\"), preroute1.getString(\"airlineid\"));\n" +
                        "    System.out.printf(\n" +
                        "        \"Before transaction - got %s's details: source airport: %s, destination airport: %s, airline: %s:%s \\n\",\n" +
                        "        route2Id, preroute2.getString(\"sourceairport\"), preroute2.getString(\"destinationairport\"),\n" +
                        "        preroute2.getString(\"airline\"), preroute2.getString(\"airlineid\"));\n" +
                        "\n" +
                        "    // In-transaction\n" +
                        "    try {\n" +
                        "      String swapId = swapAirlineRoute(transactions, cluster, bucketName, route1Id, route2Id);\n" +
                        "      if (swapId != null) {\n" +
                        "        JsonObject swapRecord = getDocByWhere(cluster, bucketName,\"id='\"+swapId+\"'\");\n" +
                        "        if (swapRecord != null) {\n" +
                        "          System.out.println(\"After transaction - swap route record: \" + swapRecord);\n" +
                        "        }\n" +
                        "      }\n" +
                        "    } catch (RuntimeException err) {\n" +
                        "      System.err.println(\"Transaction failed with: \" + err.toString());\n" +
                        "    }\n" +
                        "\n" +
                        "    // Post-transaction\n" +
                        "    JsonObject postroute1 = getDocByWhere(cluster, bucketName, \"type='route' and id=\"+route1Id);\n" +
                        "    JsonObject postroute2 = getDocByWhere(cluster, bucketName, \"type='route' and id=\"+route2Id);\n" +
                        "\n" +
                        "    System.out.printf(\n" +
                        "        \"After transaction - got %s's details: source airport: %s, destination airport: %s, airline: %s:%s \\n\",\n" +
                        "        route1Id, postroute1.getString(\"sourceairport\"), postroute1.getString(\"destinationairport\"),\n" +
                        "        postroute1.getString(\"airline\"), postroute1.getString(\"airlineid\"));\n" +
                        "    System.out.printf(\n" +
                        "        \"After transaction - got %s's details: source airport: %s, destination airport: %s, airline: %s:%s \\n\",\n" +
                        "        route2Id, postroute2.getString(\"sourceairport\"), postroute2.getString(\"destinationairport\"),\n" +
                        "        postroute2.getString(\"airline\"), postroute2.getString(\"airlineid\"));\n" +
                        "    System.exit(0);\n" +
                        "  }\n" +
                        "\n" +
                        "  private static JsonObject getDocByWhere(Cluster cluster, String bucketName, String where) {\n" +
                        "    String sqry = \"SELECT `\" + bucketName + \"`.* \" +\n" +
                        "                  \"FROM `\" + bucketName + \"` \" +\n" +
                        "                  \"WHERE \"+where;\n" +
                        "    QueryResult qr = cluster.query(sqry);\n" +
                        "    return qr.rowsAsObject().size()>0? qr.rowsAsObject().get(0): null;\n" +
                        "  }\n" +
                        "\n" +
                        "  private static String swapAirlineRoute(Transactions transactions, Cluster cluster, String bucketName,\n" +
                        "    String route1Id,\n" +
                        "    String route2Id) {\n" +
                        "    AtomicReference<String> swapId = new AtomicReference<>();\n" +
                        "    try {\n" +
                        "      transactions.run(ctx -> {\n" +
                        "        JsonObject route1Content = getDocByWhere(cluster, bucketName, \"type='route' and id=\"+route1Id);\n" +
                        "        JsonObject route2Content = getDocByWhere(cluster, bucketName, \"type='route' and id=\"+route2Id);\n" +
                        "\n" +
                        "        System.out.printf(\"In transaction - got %s's details: %s\\n\", route1Id,\n" +
                        "            route1Content.getString(\"airlineid\"));\n" +
                        "        System.out.printf(\"In transaction - got %s's details: %s\\n\", route2Id,\n" +
                        "            route2Content.getString(\"airlineid\"));\n" +
                        "\n" +
                        "        String airline1 = route1Content.getString(\"airline\");\n" +
                        "        String airlineid1 = route1Content.getString(\"airlineid\");\n" +
                        "        JsonArray schedule1 = route1Content.getArray(\"schedule\");\n" +
                        "\n" +
                        "        String airline2 = route2Content.getString(\"airline\");\n" +
                        "        String airlineid2 = route2Content.getString(\"airlineid\");\n" +
                        "        JsonArray schedule2 = route2Content.getArray(\"schedule\");\n" +
                        "\n" +
                        "        swapId.set(UUID.randomUUID().toString());\n" +
                        "\n" +
                        "        JsonObject swapRecord = JsonObject.create().put(\"id\", swapId.get())\n" +
                        "            .put(\"from_route\", route1Id).put(\"to_route\", route2Id)\n" +
                        "            .put(\"from_airline\", airline1).put(\"from_airlineid\", airlineid1).put(\"to_airline\", airline2)\n" +
                        "            .put(\"to_airlineid\", airlineid2).put(\"type\", \"Transactions_History\")\n" +
                        "            .put(\"created\", new java.util.Date().toString());\n" +
                        "\n" +
                        "        System.out.println(\"In transaction - creating a record of swap routes with UUID: \" + swapId.get());\n" +
                        "        ctx.query(\"INSERT INTO `\"+bucketName+\"` VALUES ('\"+swapId.get()+\"', \"+swapRecord+\")\");\n" +
                        "\n" +
                        "        System.out.printf(\"In transaction - changing %s's airline to: %s\\n\", route1Id,\n" +
                        "            route2Content.getString(\"airlineid\"));\n" +
                        "        System.out.printf(\"In transaction - changing %s's airline to: %s\\n\", route2Id,\n" +
                        "            route1Content.getString(\"airlineid\"));\n" +
                        "\n" +
                        "        String updateRoute1 = \"UPDATE `\"+bucketName+\"` \" +\n" +
                        "                              \"SET airline='\"+airline2+\"', airlineid='\"+airlineid2+\"', schedule=\"+schedule2 +\n" +
                        "                              \" WHERE type='route' and id = \"+route1Id;\n" +
                        "        ctx.query(updateRoute1);\n" +
                        "\n" +
                        "        String updateRoute2 = \"UPDATE `\"+bucketName+\"` \" +\n" +
                        "                              \"SET airline='\"+airline1+\"', airlineid='\"+airlineid1+\"', schedule=\"+schedule1 +\n" +
                        "                              \" WHERE type='route' and id = \"+route2Id;\n" +
                        "        ctx.query(updateRoute2);\n" +
                        "\n" +
                        "        System.out.println(\"In transaction - about to commit\");\n" +
                        "        ctx.commit();\n" +
                        "      });\n" +
                        "\n" +
                        "      return swapId.get();\n" +
                        "    } catch (TransactionCommitAmbiguous err) {\n" +
                        "      System.err.println(\"Transaction \" + err.result().transactionId() + \" possibly committed:\");\n" +
                        "      err.result().log().logs().forEach(System.err::println);\n" +
                        "    } catch (TransactionFailed err) {\n" +
                        "      if (err.getCause() instanceof DocumentNotFoundException) {\n" +
                        "        new IllegalArgumentException(\"The specified route wasn't found\");\n" +
                        "      } else {\n" +
                        "        System.err.println(\"Transaction \" + err.result().transactionId() + \" did not reach commit:\");\n" +
                        "        err.result().log().logs().forEach(System.err::println);\n" +
                        "      }\n" +
                        "    }\n" +
                        "\n" +
                        "    return null;\n" +
                        "  }\n" +
                        "}\n"));

        //##################### KV_UPSERT
        map.put("KV_UPSERT", new CodeExample(
                "    var bucket = cluster.bucket(\"{credentials-bucket}\");\n" +
                        "    var collection = bucket.defaultCollection();\n" +
                        "\n" +
                        "    JsonObject content = JsonObject.create()\n" +
                        "      .put(\"country\", \"Iceland\")\n" +
                        "      .put(\"callsign\", \"ICEAIR\")\n" +
                        "      .put(\"iata\", \"FI\")\n" +
                        "      .put(\"icao\", \"ICE\")\n" +
                        "      .put(\"id\", 123)\n" +
                        "      .put(\"name\", \"Icelandair\")\n" +
                        "      .put(\"type\", \"airline\");\n" +
                        "\n" +
                        "    collection.upsert(\"airline_123\", content);\n" +
                        "\n" +
                        "    try {\n" +
                        "      LookupInResult lookupResult = collection.lookupIn(\n" +
                        "        \"airline_123\", Collections.singletonList(get(\"name\"))\n" +
                        "      );\n" +
                        "\n" +
                        "      var str = lookupResult.contentAs(0, String.class);\n" +
                        "      System.out.println(\"New Document name = \" + str);\n" +
                        "\n" +
                        "    } catch (PathNotFoundException ex) {\n" +
                        "      System.out.println(\"Document not found!\");\n" +
                        "    }",

                "import com.couchbase.client.core.error.subdoc.PathNotFoundException;\n" +
                        "import com.couchbase.client.java.*;\n" +
                        "import com.couchbase.client.java.kv.*;\n" +
                        "import com.couchbase.client.java.kv.MutationResult;\n" +
                        "import com.couchbase.client.java.json.JsonObject;\n" +
                        "import com.couchbase.client.java.kv.LookupInResult;\n" +
                        "import static com.couchbase.client.java.kv.LookupInSpec.get;\n" +
                        "import static com.couchbase.client.java.kv.MutateInSpec.upsert;\n" +
                        "import java.util.Collections;\n" +
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
                        "    var bucket = cluster.bucket(\"{credentials-bucket}\");\n" +
                        "    var collection = bucket.defaultCollection();\n" +
                        "\n" +
                        "    JsonObject content = JsonObject.create()\n" +
                        "      .put(\"country\", \"Iceland\")\n" +
                        "      .put(\"callsign\", \"ICEAIR\")\n" +
                        "      .put(\"iata\", \"FI\")\n" +
                        "      .put(\"icao\", \"ICE\")\n" +
                        "      .put(\"id\", 123)\n" +
                        "      .put(\"name\", \"Icelandair\")\n" +
                        "      .put(\"type\", \"airline\");\n" +
                        "\n" +
                        "    collection.upsert(\"airline_123\", content);\n" +
                        "\n" +
                        "    try {\n" +
                        "      LookupInResult lookupResult = collection.lookupIn(\n" +
                        "        \"airline_123\", Collections.singletonList(get(\"name\"))\n" +
                        "      );\n" +
                        "\n" +
                        "      var str = lookupResult.contentAs(0, String.class);\n" +
                        "      System.out.println(\"New Document name = \" + str);\n" +
                        "\n" +
                        "    } catch (PathNotFoundException ex) {\n" +
                        "      System.out.println(\"Document not found!\");\n" +
                        "    }\n" +
                        "\n" +
                        "  }\n" +
                        "}\n"
        ));


        //##################### FTS_ALL_FIELDS
        map.put("FTS_ALL_FIELDS", new CodeExample(
                "    try {\n" +
                        "      final SearchResult result = cluster.searchQuery(\"travel-fts-index\",\n" +
                        "                                    SearchQuery.queryString(\"swanky\"),\n" +
                        "                                    SearchOptions.searchOptions().limit(10));\n" +
                        "      for (SearchRow row : result.rows()) {\n" +
                        "        System.out.println(\"Found row: \" + row);\n" +
                        "      }\n" +
                        "      System.out.println(\"Reported total rows: \" + result.metaData().metrics().totalRows());\n" +
                        "    } catch (CouchbaseException ex) {\n" +
                        "      ex.printStackTrace();\n" +
                        "    }",
                "import com.couchbase.client.core.error.CouchbaseException;\n" +
                        "import com.couchbase.client.java.*;\n" +
                        "import com.couchbase.client.java.kv.*;\n" +
                        "import com.couchbase.client.java.search.SearchQuery;\n" +
                        "import com.couchbase.client.java.search.SearchOptions;\n" +
                        "import com.couchbase.client.java.search.result.SearchResult;\n" +
                        "import com.couchbase.client.java.search.result.SearchRow;\n" +
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
                        "      final SearchResult result = cluster.searchQuery(\"travel-fts-index\",\n" +
                        "                                    SearchQuery.queryString(\"swanky\"),\n" +
                        "                                    SearchOptions.searchOptions().limit(10));\n" +
                        "      for (SearchRow row : result.rows()) {\n" +
                        "        System.out.println(\"Found row: \" + row);\n" +
                        "      }\n" +
                        "      System.out.println(\"Reported total rows: \" + result.metaData().metrics().totalRows());\n" +
                        "    } catch (CouchbaseException ex) {\n" +
                        "      ex.printStackTrace();\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n"
        ));

    }


}
