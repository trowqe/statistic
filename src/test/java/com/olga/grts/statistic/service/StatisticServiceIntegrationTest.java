package com.olga.grts.statistic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatisticServiceIntegrationTest {

    @Autowired
    private StatisticServiceMock statisticServiceMock;

    @Test
    public void Test2() throws InterruptedException {
        Thread childTread = new Thread(statisticServiceMock);
        childTread.start();

        Thread.sleep(600000); //60000 * 10 = 10 files

        statisticServiceMock.setWork(false);
        childTread.join();
        System.out.println("End");
    }

}
