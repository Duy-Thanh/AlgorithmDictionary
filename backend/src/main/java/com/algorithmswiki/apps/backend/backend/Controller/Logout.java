package com.algorithmswiki.apps.backend.backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algorithmswiki.apps.backend.backend.JSONHelper;
import com.algorithmswiki.apps.backend.backend.UserCredentials;
import com.algorithmswiki.apps.backend.backend.Object.LoginObject;
import com.algorithmswiki.apps.backend.backend.Object.LogoutObject;

@RestController
public class Logout {
    @GetMapping("/api/logout")
    public String logout() throws Exception {
        LoginObject object = LoginObject.getInstance();
        UserCredentials statusCredentials = UserCredentials.getInstance();

        statusCredentials.setRevokedStatus(object.getAccessToken(), true);
        object.setAccessToken(null);

        LogoutObject logoutObject = new LogoutObject("succeeded", 200);

        return JSONHelper.toJSON(logoutObject).toString();
    }
}
