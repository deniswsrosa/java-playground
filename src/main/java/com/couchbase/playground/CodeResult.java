package com.couchbase.playground;

import java.io.Serializable;
import java.util.List;

public class CodeResult implements Serializable {

    private boolean isSuccessful = true;
    private String output;
    private String exception;
    private String compilationError;

    public String getCompilationError() {
        return compilationError;
    }

    public void setCompilationError(String compilationError) {
        this.compilationError = compilationError;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "CodeResult{" +
                "isSuccessful=" + isSuccessful +
                ", output='" + output + '\'' +
                ", exceptionStackTrace='" + exception + '\'' +
                ", compilationError=" + compilationError +
                '}';
    }
}
