package com.olga.grts.statistic.service;

import com.olga.grts.statistic.model.Statistic;
import com.olga.grts.statistic.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StatisticServiceIntegrationTest {

    @Autowired
    private StatisticServiceMock statisticServiceMock;

    @Autowired
    private StatisticService statisticService;


    @Test
    public void TestStatisticMockService() throws InterruptedException {
        Thread childTread = new Thread(statisticServiceMock);
        childTread.start();

        Thread.sleep(600000); //60000 * 10 = 10 files

        statisticServiceMock.setWork(false);
        childTread.join();
        System.out.println("End");
    }



    @Test
    public void geWriteToFileAndThenReadStatistics() {

        List<Statistic> statisticList = statisticService.getStatistics();
        Path path = Utils.generateFilePath();

        statisticService.writeStatisticToFile(path, statisticList);
        List<Statistic> statisticListFromFile = statisticService.getStatisticsFromFile(path);

        assertEquals(statisticListFromFile, statisticList);
    }


    @Test
    public void getStatisticForTimePeriod() {

        LocalDateTime finishStatistic = LocalDateTime.parse("2021-01-05T11:56:19", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime startStatistic = finishStatistic.minusMinutes(10);

        statisticService.getOncePer10Min();

        assertEquals(10, statisticService.getOncePer10Min().size());

    }

}
