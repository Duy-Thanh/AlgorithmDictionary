package com.algorithmswiki.apps.backend.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algorithmswiki.apps.backend.backend.EmailValidator;
import com.algorithmswiki.apps.backend.backend.JSONHelper;
import com.algorithmswiki.apps.backend.backend.Object.ErrorObject;
import com.algorithmswiki.apps.backend.backend.Object.RegisterObject;
import com.algorithmswiki.apps.backend.backend.Service.CustomSQLService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class Register {
    @Autowired
    private CustomSQLService customSQLService;

    @GetMapping("/api/register")
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String fullname, @RequestParam String email) throws JsonProcessingException {
        boolean validateEmail = EmailValidator.isValidEmail(email);
        if (!validateEmail) {
            ErrorObject errorObject = new ErrorObject(403, "Your email entered is not corrected format");

            return JSONHelper.toJSON(errorObject).toString();
        }
        else {
            boolean isSucceeded = customSQLService.registerUser(username, password, fullname, email, "user"); // Default permission always 'user'

            if (isSucceeded) {
                RegisterObject registerObject = new RegisterObject("succeeded", 200);

                return JSONHelper.toJSON(registerObject).toString();
            } else {
                ErrorObject errorObject = new ErrorObject(500, "Failed to complete register. Please try again");

                return JSONHelper.toJSON(errorObject).toString();
            }
        }
    }
}
