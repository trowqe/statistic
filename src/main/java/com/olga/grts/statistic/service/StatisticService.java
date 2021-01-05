package com.olga.grts.statistic.service;

import com.olga.grts.statistic.model.Cpu;
import com.olga.grts.statistic.model.Disk;
import com.olga.grts.statistic.model.Memory;
import com.olga.grts.statistic.model.Statistic;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StatisticService {

    public List<Statistic> getStatistics() {

        List<Statistic> statistics = new ArrayList<>(3);
        File drive = new File("/");

        Disk disk = Disk.builder()
                .totalSpace(drive.getTotalSpace())
                .freeSpace(drive.getFreeSpace())
                .usableSpace(drive.getUsableSpace())
                .build();

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        Cpu cpu = Cpu.builder()
                .systemCpuLoad(osBean.getSystemCpuLoad())
                .processCpuLoad(osBean.getProcessCpuLoad())
                .build();

        Memory memory = Memory.builder()
                .freePhysicalMemorySize(osBean.getFreePhysicalMemorySize())
                .totalPhysicalMemorySize(osBean.getTotalPhysicalMemorySize())
                .build();

        statistics.add(disk);
        statistics.add(cpu);
        statistics.add(memory);

        return statistics;
    }

    public void writeStatisticToFile(Path path, List<Statistic> statistics) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            statistics.forEach(i -> stringBuilder.append(i.toString()));
            Files.writeString(path, stringBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Statistic> getStatisticsFromFile(Path path) {
        List<Statistic> statistics = new ArrayList<>(3);
        String read = null;
        try {
            read = Files.readAllLines(path).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Disk disk = new Disk();
        Memory memory = new Memory();
        Cpu cpu = new Cpu();

        try {
            read = Files.readAllLines(path).get(0);
            disk = disk.convertObjectFromToString(read);
            memory = memory.convertObjectFromToString(read);
            cpu = cpu.convertObjectFromToString(read);

            statistics.add(disk);
            statistics.add(cpu);
            statistics.add(memory);

            LocalDateTime dateTime = convertFileNameToLocalDateTime(path);
            statistics.forEach(x->x.setDateTime(dateTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statistics;
    }


    public Map<LocalDateTime, List<Statistic>> getOncePerHour() {

        LocalDateTime finish = LocalDateTime.now();
        LocalDateTime start = finish.minusHours(1);

        return getStatisticsForTimePeriod(start, finish);
    }

    public Map<LocalDateTime, List<Statistic>> getOncePer10Min() {

        LocalDateTime finishStatistic = LocalDateTime.parse("2021-01-05T11:56:19", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime startStatistic = finishStatistic.minusMinutes(10);

        return getStatisticsForTimePeriod(startStatistic, finishStatistic);
    }

    private Map<LocalDateTime, List<Statistic>> getStatisticsForTimePeriod(LocalDateTime start, LocalDateTime finish) {

        Map<LocalDateTime, List<Statistic>> result = null;

        try (Stream<Path> walk = Files.walk(Paths.get("src/main/resources/static"))) {

            result = walk.filter(Files::isRegularFile)
                    .filter(x -> isBetween(x, start, finish))
                    .collect(Collectors.toMap(StatisticService::convertFileNameToLocalDateTime, StatisticService::getStatisticsFromFile));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean isBetween(Path path, LocalDateTime startTime, LocalDateTime finishTime) {

        LocalDateTime fileTime = convertFileNameToLocalDateTime(path);
        return isInclude(startTime, finishTime, fileTime);
    }

    /*
    for input in format "src/main/resources/static/2021-01-03T00:42:01.281780.txt";
     */
    public static LocalDateTime convertFileNameToLocalDateTime(Path path) {
        LocalDateTime ldt = null;
        String pathStr = path.toString();
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(pathStr);

        if (matcher.find()) {
            String text = pathStr.substring(matcher.start(), matcher.end());
            ldt = LocalDateTime.parse(text);
        }
        return ldt;
    }

    private static boolean isInclude(LocalDateTime startStatistic, LocalDateTime finishStatistic, LocalDateTime x) {
        //TODO: equals
        if (x.isEqual(startStatistic) || x.isEqual(finishStatistic)) return true;
        return x.isAfter(startStatistic) && x.isBefore(finishStatistic);
    }


}
