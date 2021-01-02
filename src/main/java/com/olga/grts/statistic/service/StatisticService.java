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
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService {

    public List<Statistic> getStatistics() {

        List <Statistic> statistics = new ArrayList<>(3);
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

    public void writeStatisticToFile(Path path, List<Statistic> statistics){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            statistics.forEach(i->stringBuilder.append(i.toString()));
            Files.writeString(path, stringBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Statistic> getStatisticsFromFile(Path path) throws IOException {
        List <Statistic> statistics = new ArrayList<>(3);
        String read = Files.readAllLines(path).get(0);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statistics;
    }

}
