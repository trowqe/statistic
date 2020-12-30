package com.olga.grts.statistic;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class Memory {

    private static final int CONVERT_TO_GD = 1073741824;

    private long totalSpace;
    private long freeSpace;
    private long usableSpace;

    private double convertToGB(long metric) {
        return (double) metric / CONVERT_TO_GD;
    }

    @Override
    public String toString() {
        return convertToGB(totalSpace) + "\n" + freeSpace + "\n" + usableSpace + "\n";
    }
}
