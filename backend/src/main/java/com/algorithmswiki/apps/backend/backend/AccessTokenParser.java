package com.algorithmswiki.apps.backend.backend;

public class AccessTokenParser {

    public AccessTokenParser() { }

    public boolean validateToken(String access_token) throws Exception {
        if (access_token != null) {
            String user = JWTUtil.extractUsername(access_token);
            if (user != null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String fetchUserDetails(String access_token) throws Exception {
        if (!validateToken(access_token)) {
            return "Your access_token is expired or invalid. You must login to continue your action";
        } else {
            return JWTUtil.extractUsername(access_token);
        }
    }
}
