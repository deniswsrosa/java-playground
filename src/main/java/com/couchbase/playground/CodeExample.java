package com.couchbase.playground;

public class CodeExample {

    private String snippet;
    private String fullCode;

    public CodeExample(String snippet, String fullCode) {
        this.snippet = snippet;
        this.fullCode = fullCode;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }
}
