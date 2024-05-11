package com.algorithmswiki.apps.backend.backend;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class UserCredentials {
    private static UserCredentials instance;
    private List<AbstractMap.SimpleEntry<String, Boolean>> credentialsRevokeStatus;

    private UserCredentials() { 
        if (credentialsRevokeStatus == null) {
            credentialsRevokeStatus = new ArrayList<>();
        }
    }

    public static synchronized UserCredentials getInstance() {
        if (instance == null) {
            instance = new UserCredentials();
        }

        return instance;
    }

    public boolean getRevokedStatus(String access_token) {
        for (AbstractMap.SimpleEntry<String, Boolean> entry : credentialsRevokeStatus) {
            if (entry.getKey().equals(access_token)) {
                return entry.getValue();
            }

            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        return false;
    }

    public void setRevokedStatus(String access_token, boolean isRevoked) {
        try {
            for (AbstractMap.SimpleEntry<String, Boolean> entry : credentialsRevokeStatus) {
                if (entry.getKey().equals(access_token)) {
                    entry.setValue(isRevoked);
                }

                System.out.println(entry.getKey() + ": " + entry.getValue());
                return;
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            credentialsRevokeStatus.add(new AbstractMap.SimpleEntry<>(access_token, isRevoked));
        }
    }
}