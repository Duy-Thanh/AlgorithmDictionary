package com.algorithmswiki.apps.backend.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algorithmswiki.apps.backend.backend.AccessTokenParser;
import com.algorithmswiki.apps.backend.backend.JSONHelper;
import com.algorithmswiki.apps.backend.backend.UserCredentials;
import com.algorithmswiki.apps.backend.backend.Object.AccountObject;
import com.algorithmswiki.apps.backend.backend.Object.ErrorObject;
import com.algorithmswiki.apps.backend.backend.Object.LoginObject;
import com.algorithmswiki.apps.backend.backend.Service.CustomSQLService;

@RestController
public class CurrentUser {
    @Autowired
    private CustomSQLService customSQLService;

    private String exceptionString;

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    @GetMapping("/api/get_current_user")
    public String getCurrentUser() throws Exception {
        AccessTokenParser accessTokenParser = new AccessTokenParser();

        LoginObject object = LoginObject.getInstance();

        UserCredentials cred = UserCredentials.getInstance();

        if (cred.getRevokedStatus(object.getAccessToken())) {
            ErrorObject errorObject = new ErrorObject(500, "Your access_token is revoked. You must login to continue your action");

            return JSONHelper.toJSON(errorObject).toString();
        } else {
            String user = accessTokenParser.fetchUserDetails(object.getAccessToken());

            if (user == "Your access_token is expired or invalid. You must login to continue your action") {
                ErrorObject errorObject = new ErrorObject(500, "Your access_token is expired or invalid. You must login to continue your action");
    
                return JSONHelper.toJSON(errorObject).toString();
            } else if (user == "Cannot verify integrity of token or your token is corrupted. Due to security reason, you are prohibited to continue your action.") {
                ErrorObject errorObject = new ErrorObject(500, "Cannot verify integrity of token or your token is corrupted. Due to security reason, you are prohibited to continue your action.");
    
                return JSONHelper.toJSON(errorObject).toString();
            } else if (user.contains("Cannot retrieve neccessary information. Due to security reason, you are prohibited to continue your action. Error details: ")) {
                ErrorObject errorObject = new ErrorObject(500, "Cannot retrieve neccessary information. Due to security reason, you are prohibited to continue your action." + exceptionString);
    
                return JSONHelper.toJSON(errorObject).toString();
            } else {
                Integer Id = customSQLService.getUserIdByUsername(user);
                String fullName = customSQLService.getFullNameByUsername(user);
                String email = customSQLService.getEmailByUsername(user);
                String permission = customSQLService.getPermissionByUsername(user);
                String stat = customSQLService.getDeletedOrDeactivatedStateByUsername(user);
    
                if (stat == "true") {
                    ErrorObject errObject = new ErrorObject(500, "This account has been deleted or deactivated by the owner.");
    
                    return JSONHelper.toJSON(errObject).toString();
                } else {
                    AccountObject accountObject = new AccountObject(Id, user, fullName, email, permission, stat);
    
                    return JSONHelper.toJSON(accountObject).toString();    
                }
            }
        }
    }
}