package com.algorithmswiki.apps.backend.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algorithmswiki.apps.backend.backend.AccessTokenParser;
import com.algorithmswiki.apps.backend.backend.JSONHelper;
import com.algorithmswiki.apps.backend.backend.UserCredentials;
import com.algorithmswiki.apps.backend.backend.Object.ErrorObject;
import com.algorithmswiki.apps.backend.backend.Object.LoginObject;
import com.algorithmswiki.apps.backend.backend.Service.CustomSQLService;

@RestController
public class Algorithms {
    @Autowired
    private CustomSQLService customSQLService;

    @GetMapping("/api/get_algorithms")
    public String getAlgorithms() throws Exception {
        LoginObject object = LoginObject.getInstance();

        if (object.getAccessToken() == "" || object.getAccessToken() == null) {
            ErrorObject errorObject = new ErrorObject(403, "Forbidden");

            return JSONHelper.toJSON(errorObject).toString();
        } else {
            AccessTokenParser accessTokenParser = new AccessTokenParser();
            
            UserCredentials cred = UserCredentials.getInstance();

            if (cred.getRevokedStatus(object.getAccessToken())) {
                ErrorObject errorObject = new ErrorObject(500, "Your access_token is expired or invalid. You must login to continue your action");

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
                    ErrorObject errorObject = new ErrorObject(500, "Cannot retrieve neccessary information. Due to security reason, you are prohibited to continue your action.");
    
                    return JSONHelper.toJSON(errorObject).toString();
                } else {
                    // Integer Id = customSQLService.getUserIdByUsername(user);
                    // String fullName = customSQLService.getFullNameByUsername(user);
                    // String email = customSQLService.getEmailByUsername(user);
                    String permission = customSQLService.getPermissionByUsername(user);
                    String stat = customSQLService.getDeletedOrDeactivatedStateByUsername(user);
    
                    if (stat == "true") {
                        ErrorObject errObject = new ErrorObject(500, "This account has been deleted or deactivated by the owner.");
    
                        return JSONHelper.toJSON(errObject).toString();
                    } else {
                        // We can make sure that permission is setted
                        if (permission == "" || permission == null) {
                            ErrorObject errorObject = new ErrorObject(403, "Forbidden");
    
                            return JSONHelper.toJSON(errorObject).toString();
                        } else {
                            String AlgorithmJSON = customSQLService.getAlgorithmsAsJSON();
    
                            return AlgorithmJSON;
                        } 
                    }
                }
            }
        }
    }

    @GetMapping("/api/get_algorithms_by_search_term")
    public String getAlgorithmsBySearchTerm(@RequestParam String query) throws Exception {
        LoginObject object = LoginObject.getInstance();

        if (object.getAccessToken() == "" || object.getAccessToken() == null) {
            ErrorObject errorObject = new ErrorObject(403, "Forbidden");

            return JSONHelper.toJSON(errorObject).toString();
        } else {
            AccessTokenParser accessTokenParser = new AccessTokenParser();
            
            UserCredentials cred = UserCredentials.getInstance();

            if (cred.getRevokedStatus(object.getAccessToken())) {
                ErrorObject errorObject = new ErrorObject(500, "Your access_token is expired or invalid. You must login to continue your action");

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
                    ErrorObject errorObject = new ErrorObject(500, "Cannot retrieve neccessary information. Due to security reason, you are prohibited to continue your action.");
    
                    return JSONHelper.toJSON(errorObject).toString();
                } else {
                    // Integer Id = customSQLService.getUserIdByUsername(user);
                    // String fullName = customSQLService.getFullNameByUsername(user);
                    // String email = customSQLService.getEmailByUsername(user);
                    String permission = customSQLService.getPermissionByUsername(user);
                    String stat = customSQLService.getDeletedOrDeactivatedStateByUsername(user);
    
                    if (stat == "true") {
                        ErrorObject errObject = new ErrorObject(500, "This account has been deleted or deactivated by the owner.");
    
                        return JSONHelper.toJSON(errObject).toString();
                    } else {
                        // We can make sure that permission is setted
                        if (permission == "" || permission == null) {
                            ErrorObject errorObject = new ErrorObject(403, "Forbidden");
    
                            return JSONHelper.toJSON(errorObject).toString();
                        } else {
                            String AlgorithmJSON = customSQLService.getAlgorithmsSearchResultAsJSON(query);
    
                            return AlgorithmJSON;
                        } 
                    }
                }
            }
        }
    }
}
