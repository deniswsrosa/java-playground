package example;

import java.io.Serializable;
import java.util.List;

public class CodeResult implements Serializable {

    private boolean isSuccessful = true;
    private String output;
    private String exceptionStackTrace;
    private List<String> compilationErrors;

    public List<String> getCompilationErrors() {
        return compilationErrors;
    }

    public void setCompilationErrors(List<String> compilationErrors) {
        this.compilationErrors = compilationErrors;
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

    public String getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public void setExceptionStackTrace(String exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
    }

    @Override
    public String toString() {
        return "CodeResult{" +
                "isSuccessful=" + isSuccessful +
                ", output='" + output + '\'' +
                ", exceptionStackTrace='" + exceptionStackTrace + '\'' +
                ", compilationErrors=" + compilationErrors +
                '}';
    }
}
