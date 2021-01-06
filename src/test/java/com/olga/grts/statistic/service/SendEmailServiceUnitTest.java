package com.olga.grts.statistic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class SendEmailServiceUnitTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendEmailService() {

        Mockito.doNothing().when(javaMailSender).send(any (SimpleMailMessage.class));

        emailService.sendEmail("trowqe@gmail.com", "body", "topic");
    }
}
