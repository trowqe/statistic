package com.olga.grts.statistic.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Statistic {

    private LocalDateTime dateTime;
    public Statistic convertObjectFromToString(String file) {
        return null;
    }
}
