package com.olga.grts.statistic.controllers;

import com.olga.grts.statistic.model.Statistic;
import com.olga.grts.statistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    StatisticService statisticService;

    @Autowired
    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }
    @GetMapping(value = "/hour")
    public Map<LocalDateTime, List<Statistic>> getOncePerHour() {
        return statisticService.getOncePerHour();
    }


    //getOncePerDay

    //getOncePerWeek
}
