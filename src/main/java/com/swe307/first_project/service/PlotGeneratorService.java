package com.swe307.first_project.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class PlotGeneratorService {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final RCallerService rCallerService;

    private List<Double> allCsvData;
    private int csvIndex = 0;

    public PlotGeneratorService(RCallerService rCallerService) {
        this.rCallerService = rCallerService;
        allCsvData = null;
    }

    @PostConstruct
    public void init() {
        allCsvData = CSVLoader.loadCsvData();
    }

    @Scheduled(fixedRate = 1000, initialDelay = 3000)
    private void readTheDataAndPushToR() {
        if (allCsvData != null && csvIndex < allCsvData.size()) {
            double nextValue = allCsvData.get(csvIndex);
            csvIndex++;
            rCallerService.updateAndGetPlotSvg(nextValue);
        }
    }

}
