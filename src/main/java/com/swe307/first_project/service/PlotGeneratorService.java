package com.swe307.first_project.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class PlotGeneratorService {

    private static final String CSV_FILE = "swe307_pro1.csv";
    private final RCallerService rCallerService;

    private List<Double> allCsvData;
    private int csvIndex = 0;

    public PlotGeneratorService(RCallerService rCallerService) {
        this.rCallerService = rCallerService;
    }

    @PostConstruct
    public void init() {
        loadCsvData();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                this::readTheDataAndPushToR,
                3,
                1,
                TimeUnit.SECONDS);
    }

    private void loadCsvData() {
        ClassPathResource resource = new ClassPathResource(CSV_FILE);

        try (InputStream dataStream = resource.getInputStream();
             BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataStream))) {
            allCsvData = dataReader.lines()
                    .map(line -> line.split(",")[0])
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readTheDataAndPushToR() {
        if (csvIndex < allCsvData.size()) {
            double nextValue = allCsvData.get(csvIndex);
            csvIndex++;
            rCallerService.updateAndGetPlotSvg(nextValue);
        }
    }


}
