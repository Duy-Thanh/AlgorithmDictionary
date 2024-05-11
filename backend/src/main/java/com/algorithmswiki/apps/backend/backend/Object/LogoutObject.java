package com.algorithmswiki.apps.backend.backend.Object;

public class LogoutObject {
    private String status;
    private int error_code;

    public LogoutObject() { }

    public LogoutObject(String status, int error_code) {
        this.status = status;
        this.error_code = error_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getErrorCode() {
        return error_code;
    }

    public void setErrorCode(int error_code) {
        this.error_code = error_code;
    }
}
