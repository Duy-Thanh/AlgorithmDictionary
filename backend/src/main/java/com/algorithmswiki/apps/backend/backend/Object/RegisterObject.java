package com.algorithmswiki.apps.backend.backend.Object;

public class RegisterObject {
    private String status;
    private int status_code;

    public RegisterObject() { }

    public RegisterObject(String status, int status_code) {
        this.status = status;
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return status_code;
    }

    public void setStatusCode(int status_code) {
        this.status_code = status_code;
    }
}
