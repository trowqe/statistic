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
public class Memory extends Statistic{
    //   cpu usage for the whole system
    private long freePhysicalMemorySize;

    private long totalPhysicalMemorySize;

    @Override
    public String toString() {
        return "Memory{" +
                "freePhysicalMemorySize=" + freePhysicalMemorySize +
                ", totalPhysicalMemorySize=" + totalPhysicalMemorySize +
                '}';
    }

    @Override
    public Memory convertObjectFromToString(String file) {
        Memory memory = new Memory();
        Pattern pattern = Pattern.compile("Memory.+?}");

        Matcher matcher = pattern.matcher(file);
        if (matcher.find()) {
            String text = file.substring(matcher.start(), matcher.end());

            text = text.replaceAll("[{}]", "");
            String[] commaDelimitArray = text.split(",");
            for (String s : commaDelimitArray) {
                String[] keyValuePair = s.split("=");
                String key = keyValuePair[0].trim();
                String value = keyValuePair[1].trim();
                if ("MemoryfreePhysicalMemorySize".equals(key)) {
                    memory.setFreePhysicalMemorySize(Long.parseLong(value));
                } else if ("totalPhysicalMemorySize".equals(key)) {
                    memory.setTotalPhysicalMemorySize(Long.parseLong(value));
                }
            }
        }
        return memory;
    }
}

