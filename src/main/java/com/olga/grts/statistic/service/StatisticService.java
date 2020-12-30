package com.olga.grts.statistic.service;

import com.olga.grts.statistic.Memory;
import com.sun.management.OperatingSystemMXBean;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class StatisticService {

    private static final int CONVERT_TO_GD = 1073741824;

    private StringBuilder statistic = new StringBuilder();

    //   /home/olga/Downloads/statistic/src/main/resources/static
    // Метрики содержат информацию о CPU, Мемory, Dick Usage

    public void getStatistic() {


        File cDrive = new File("/");
        System.out.println(String.format("Total space: %.2f GB",
                (double) cDrive.getTotalSpace() / CONVERT_TO_GD));
        statistic.append(cDrive.getTotalSpace());
        System.out.println(String.format("Free space: %.2f GB",
                (double) cDrive.getFreeSpace() / CONVERT_TO_GD));
        System.out.println(String.format("Usable space: %.2f GB",
                (double) cDrive.getUsableSpace() / CONVERT_TO_GD));

        Memory memory = Memory.builder()
                .totalSpace(cDrive.getTotalSpace())
                .freeSpace(cDrive.getFreeSpace())
                .usableSpace(cDrive.getUsableSpace())
                .build();

        System.out.println("-------" + memory.toString());

        /*
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        // What % load the overall system is at, from 0.0-1.0
        System.out.println(osBean.getSystemCpuLoad());

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println(String.format("Initial memory: %.2f GB",
                (double)memoryMXBean.getHeapMemoryUsage().getInit() /1073741824));
        System.out.println(String.format("Used heap memory: %.2f GB",
                (double)memoryMXBean.getHeapMemoryUsage().getUsed() /1073741824));
        System.out.println(String.format("Max heap memory: %.2f GB",
                (double)memoryMXBean.getHeapMemoryUsage().getMax() /1073741824));
        System.out.println(String.format("Committed memory: %.2f GB",
                (double)memoryMXBean.getHeapMemoryUsage().getCommitted() /1073741824));


        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        for(Long threadID : threadMXBean.getAllThreadIds()) {
            ThreadInfo info = threadMXBean.getThreadInfo(threadID);
            System.out.println("Thread name: " + info.getThreadName());
            System.out.println("Thread State: " + info.getThreadState());
            System.out.println(String.format("CPU time: %s ns",
                    threadMXBean.getThreadCpuTime(threadID)));
        }

         */
    }

    public void getStatisticToFile(){
        File drive = new File("/");

        Memory memory = Memory.builder()
                .totalSpace(drive.getTotalSpace())
                .freeSpace(drive.getFreeSpace())
                .usableSpace(drive.getUsableSpace())
                .build();

        Path path = Paths.get("/home/olga/Downloads/statistic/src/main/resources/static/" + LocalDateTime.now() +".txt");
        try {
            Files.writeString(path, memory.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
