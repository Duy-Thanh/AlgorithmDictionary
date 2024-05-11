package com.algorithmswiki.apps.backend.backend.Object;

public class ErrorObject {
    private int error_code;
    private String error_details;
    
    public ErrorObject() { }

    public ErrorObject(int error_code, String error_details) {
        this.error_code = error_code;
        this.error_details = error_details;
    }

    public int getErrorCode() {
        return error_code;
    }

    public void setErrorCode(int error_code) {
        this.error_code = error_code;
    }

    public String getErrorDetails() {
        return error_details;
    }

    public void setErrorDetails(String error_details) {
        this.error_details = error_details;
    }
}
