package com.swe307.first_project.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlotGeneratorService {

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

    public void readTheDataAndPushToR() {
        if (allCsvData != null && csvIndex < allCsvData.size()) {
            double nextValue = allCsvData.get(csvIndex);
            csvIndex++;
            rCallerService.updateAndGetPlotSvg(nextValue);
        }
    }

}
