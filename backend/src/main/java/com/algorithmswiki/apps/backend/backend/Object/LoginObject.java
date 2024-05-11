package com.algorithmswiki.apps.backend.backend.Object;

public class LoginObject {
    private static LoginObject instance;
    private String access_token;
    private int status_code;
    private String error;

    private LoginObject() { }

    public static synchronized LoginObject getInstance() {
        if (instance == null) {
            instance = new LoginObject();
        }

        return instance;
    }

    public LoginObject(String access_token, String error, int status_code) {
        this.access_token = access_token;
        this.status_code = status_code;
        this.error = error;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return status_code;
    }

    public void setStatusCode(int status_code) {
        this.status_code = status_code;
    }
}
