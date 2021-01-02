package com.olga.grts.statistic.model;

import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cpu extends Statistic {
    private double systemCpuLoad;

    private double processCpuLoad;

    @Override
    public String toString() {
        return "Cpu{" +
                "systemCpuLoad=" + systemCpuLoad +
                ", processCpuLoad=" + processCpuLoad +
                '}';
    }

    @Override
    public Cpu convertObjectFromToString(String file) {
        Cpu cpu = new Cpu();
        Pattern pattern = Pattern.compile("Cpu.+?}");
        Matcher matcher = pattern.matcher(file);
        if (matcher.find()) {
            String text = file.substring(matcher.start(), matcher.end());

            text = text.replaceAll("[{}]", "");
            String[] commaDelimitArray = text.split(",");
            for (String s : commaDelimitArray) {
                String[] keyValuePair = s.split("=");
                String key = keyValuePair[0].trim();
                String value = keyValuePair[1].trim();
                if ("CpusystemCpuLoad".equals(key)) {
                    cpu.setSystemCpuLoad(Double.parseDouble(value));
                } else if ("processCpuLoad".equals(key)) {
                    cpu.setProcessCpuLoad(Double.parseDouble(value));
                }
            }
        }
        return cpu;
    }
}
