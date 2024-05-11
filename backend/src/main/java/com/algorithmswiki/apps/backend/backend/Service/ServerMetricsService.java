package com.algorithmswiki.apps.backend.backend.Service;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

public class ServerMetricsService {
    private SystemInfo systemInfo = new SystemInfo();
    private CentralProcessor processor = systemInfo.getHardware().getProcessor();

    public double getCpuUsagePercentage() {
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(processor.getSystemCpuLoadTicks());
        return cpuLoad; // Convert to percentage
    }

    public double getRamUsagePercentage() {
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long availableMemory = memory.getAvailable();
        long totalMemory = memory.getTotal();
        return (double) (totalMemory - availableMemory) / totalMemory * 100;
    }
}
