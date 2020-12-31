package com.olga.grts.statistic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendEmailServiceTest {

    @Autowired
    private SendEmailService sendEmailService;

    @Test
    public void TestStatisticService() {
      sendEmailService.sendEmail("trowqe@gmail.com", "body", "topic");
    }
}
