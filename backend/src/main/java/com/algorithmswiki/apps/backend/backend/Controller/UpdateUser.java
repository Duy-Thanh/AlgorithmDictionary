package com.algorithmswiki.apps.backend.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algorithmswiki.apps.backend.backend.EmailValidator;
import com.algorithmswiki.apps.backend.backend.JSONHelper;
import com.algorithmswiki.apps.backend.backend.Object.ErrorObject;
import com.algorithmswiki.apps.backend.backend.Object.UpdateUserObject;
import com.algorithmswiki.apps.backend.backend.Service.CustomSQLService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class UpdateUser {
    @Autowired
    private CustomSQLService customSQLService;

    @GetMapping("/api/update_user")
    public String updateUser(@RequestParam Long id, @RequestParam String username, @RequestParam String password,
                             @RequestParam String fullname, @RequestParam String email) throws JsonProcessingException {
        boolean validateEmail = EmailValidator.isValidEmail(email);

        if (!validateEmail) {
            ErrorObject errorObject = new ErrorObject(500, "Your email is not valid email format");

            return JSONHelper.toJSON(errorObject).toString();
        } else {
            boolean isSucceeded = customSQLService.updateUser(id, username, password, fullname, email);

            if (isSucceeded) {
                UpdateUserObject updateUserObject = new UpdateUserObject("succeeded", 200);

                return JSONHelper.toJSON(updateUserObject).toString();
            } else {
                ErrorObject errorObject = new ErrorObject(500, "Failed to complete update user. Please try again.");

                return JSONHelper.toJSON(errorObject).toString();
            }
        }
    }
}
