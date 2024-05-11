package com.algorithmswiki.apps.backend.backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algorithmswiki.apps.backend.backend.JSONHelper;
import com.algorithmswiki.apps.backend.backend.Object.DefaultObject;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class Home {
    @GetMapping("/api")
    public String getIndex() throws JsonProcessingException {
        // Create object
        DefaultObject defaultObject = new DefaultObject("status", "working", "200");
        
        String JSON = JSONHelper.toJSON(defaultObject);

        return JSON;
    }
}
