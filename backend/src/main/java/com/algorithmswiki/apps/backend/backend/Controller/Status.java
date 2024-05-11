package com.algorithmswiki.apps.backend.backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algorithmswiki.apps.backend.backend.JSONHelper;
import com.algorithmswiki.apps.backend.backend.Object.DefaultObject;
import com.algorithmswiki.apps.backend.backend.Service.ServerMetricsService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class Status {
    private ServerMetricsService systemMetricsService;

    @GetMapping("/api/status")
    public String getStatus(@RequestParam(required = false, defaultValue = "false") boolean details) throws JsonProcessingException {
        systemMetricsService = new ServerMetricsService();

        double cpuUsagePercentage = systemMetricsService.getCpuUsagePercentage();
        double ramUsagePercentage = systemMetricsService.getRamUsagePercentage();

        // Determine the demand level based on CPU and RAM usage
        String demandLevel;

        if (cpuUsagePercentage > 90 || ramUsagePercentage > 90) {
            demandLevel = "high";
        } else if ((cpuUsagePercentage >= 50 && cpuUsagePercentage < 90) ||
                   (ramUsagePercentage >= 50 && ramUsagePercentage < 90)) {
            demandLevel = "medium";
        } else if ((cpuUsagePercentage >= 10 && cpuUsagePercentage < 50) ||
                   (ramUsagePercentage >= 10 && ramUsagePercentage < 50)){
            demandLevel = "normal";
        } else {
            demandLevel = "minimal";
        }

        DefaultObject statusObject = new DefaultObject();

        if (!details) {
            statusObject.setMessage("server_pressure_status");
            statusObject.setValue(demandLevel);
            statusObject.setStatusCode("200");
        } else {
            String details_value = "CPU Usage: " + cpuUsagePercentage + "%, RAM Usage: " + ramUsagePercentage + "%, Demand Level: " + demandLevel;

            statusObject.setMessage("server_pressure_status_details");
            statusObject.setValue(details_value);
            statusObject.setStatusCode("200");
        }

        systemMetricsService = null;

        return JSONHelper.toJSON(statusObject);
    }
}