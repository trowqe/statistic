package com.olga.grts.statistic.model;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryParserTest {

    @Test
    public void test(){
        String file = "Disk{totalSpace=250438021120, freeSpace=160226119680, usableSpace=147433230336}Cpu{systemCpuLoad=0.0, processCpuLoad=0.0}Memory{freePhysicalMemorySize=817610752, totalPhysicalMemorySize=16625799168}";
        Pattern pattern = Pattern.compile("Memory.+?}");

        Matcher matcher = pattern.matcher(file);
        if(matcher.find()) {
            String text = file.substring(matcher.start(), matcher.end());
            System.out.println(text);

            text=text.replaceAll("[{}]", "");
            String[] commaDelimitArray=text.split(",");
            Memory memory = new Memory();
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
            System.out.println(memory.toString());
        }

    }

}
