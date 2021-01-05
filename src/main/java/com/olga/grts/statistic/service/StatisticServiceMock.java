package com.olga.grts.statistic.service;

import com.olga.grts.statistic.model.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticServiceMock implements Runnable {

    private static final String PATH_TO_FILES = "/src/main/resources/static/";

    @Value("${isStatisticServiceMockWork}")
    private boolean isWork;

    @Value("${statisticFrequencyTime}")
    private long statisticFrequencyTime;

    @Autowired
    public StatisticServiceMock(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    private StatisticService statisticService;


    //   /home/olga/Downloads/statistic/src/main/resources/static
    // Метрики содержат информацию о CPU, Мемory, Dick Usage

    public void getStatisticToFile() {

        File drive = new File("/");

        List<Statistic> statisticList = statisticService.getStatistics();

        String basePath = new File("").getAbsolutePath();
        Path path = Paths.get(basePath+ PATH_TO_FILES + LocalDateTime.now() +".txt");

        statisticService.writeStatisticToFile(path, statisticList);
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
