package com.olga.grts.statistic.model;

import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Disk extends Statistic{

    private static final int CONVERT_TO_GD = 1073741824;

    private long totalSpace;
    private long freeSpace;
    private long usableSpace;

    private double convertToGB(long metric) {
        return (double) metric / CONVERT_TO_GD;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "totalSpace=" + totalSpace +
                ", freeSpace=" + freeSpace +
                ", usableSpace=" + usableSpace +
                '}';
    }

    @Override
    public Disk convertObjectFromToString(String file) {
        Disk disk = new Disk();
        Pattern pattern = Pattern.compile("Disk.+?}");

        Matcher matcher = pattern.matcher(file);
        if (matcher.find()) {
            String text = file.substring(matcher.start(), matcher.end());

            text = text.replaceAll("[{}]", "");
            String[] commaDelimitArray = text.split(",");
            for (String s : commaDelimitArray) {
                String[] keyValuePair = s.split("=");
                String key = keyValuePair[0].trim();
                String value = keyValuePair[1].trim();
                switch (key) {
                    case "DisktotalSpace":
                        disk.setTotalSpace(Long.parseLong(value));
                        break;
                    case "freeSpace":
                        disk.setFreeSpace(Long.parseLong(value));
                        break;
                    case "usableSpace":
                        disk.setUsableSpace(Long.parseLong(value));
                        break;
                }
            }
        }
        return disk;
    }
}
