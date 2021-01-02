package com.olga.grts.statistic.service;

import com.olga.grts.statistic.model.Statistic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void main() throws IOException {

        List<Statistic> statisticList = statisticService.getStatistics();
        Path path = Paths.get("src/main/resources/static/test.txt");
        statisticService.writeStatisticToFile(path, statisticList);
        List<Statistic> statisticListFromFile = statisticService.getStatisticsFromFile(path);

        assertTrue(statisticList.equals(statisticListFromFile));
    }

}
