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

    private static final int MAX_EXECUTIONS = 100;

    private final RCallerService rCallerService;
    private int executionCounter = 0;


    private List<Double> allCsvData;
    private int csvIndex = 0;

    public PlotGeneratorService(RCallerService rCallerService) {
        this.rCallerService = rCallerService;
        allCsvData = null;
    }

    @PostConstruct
    public void init() {
        System.out.println("Loading CSV Data...");
        allCsvData = CSVLoader.loadCsvData();
        System.out.println("CSV Data loaded. allCsvData: " + allCsvData.size());
    }

    public void readTheDataAndPushToR() {
        if (executionCounter >= MAX_EXECUTIONS) return;

        if (allCsvData != null && csvIndex < allCsvData.size()) {
            System.out.println("allCsvData = " + allCsvData.size());
            System.out.println("csvIndex = " + csvIndex);
            System.out.println("executionCounter = " + executionCounter);
            double nextValue = allCsvData.get(csvIndex);
            csvIndex++;
            rCallerService.updateAndGetPlotSvg(nextValue);
        }
        executionCounter++;
    }

}
