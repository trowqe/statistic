package com.olga.grts.statistic.service;

import com.olga.grts.statistic.Memory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class StatisticServiceMock implements Runnable {

    private static final String PATH_TO_FILES = "/src/main/resources/static/";

    @Value("${isStatisticServiceMockWork}")
    private boolean isWork;

    @Value("${statisticFrequencyTime}")
    private long statisticFrequencyTime;


    //   /home/olga/Downloads/statistic/src/main/resources/static
    // Метрики содержат информацию о CPU, Мемory, Dick Usage

    public void getStatisticToFile() {

        File drive = new File("/");

        Memory memory = Memory.builder()
                .totalSpace(drive.getTotalSpace())
                .freeSpace(drive.getFreeSpace())
                .usableSpace(drive.getUsableSpace())
                .build();

        String basePath = new File("").getAbsolutePath();
        Path path = Paths.get(basePath+ PATH_TO_FILES + LocalDateTime.now() +".txt");
        try {
            Files.writeString(path, memory.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isWork) {
            getStatisticToFile();
            try {
                Thread.sleep(statisticFrequencyTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWork(boolean work) {
        isWork = work;
    }
}
