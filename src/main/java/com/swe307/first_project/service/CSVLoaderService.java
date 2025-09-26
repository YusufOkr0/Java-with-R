package com.swe307.first_project.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Csv dosyasindan tüm veri java tarafina alinsin. sonra bir scheculed job baslatilsin.
 * her saniye bir bölme okunur R tarafina atilir.
 */
@Service
public class CSVLoaderService {

    private static String CSV_FILE = "swe307_pro1.csv";
    private List<Double> cvsDataColumn;
    private int csvIndex;


    @PostConstruct
    public void init() {
        loadCsvData();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::readTheDataAndPushToR, 3, 1, TimeUnit.SECONDS);
    }

    public void loadCsvData(){
        ClassPathResource resource = new ClassPathResource(CSV_FILE);

        try (InputStream dataStream = resource.getInputStream();
             BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataStream)))
        {
            cvsDataColumn = dataReader.lines()
                    .map(line -> line.split(",")[0])
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readTheDataAndPushToR() {
        if (csvIndex < cvsDataColumn.size()) {
            double nextValue = cvsDataColumn.get(csvIndex);
            csvIndex++;

            String newSvg = rPlottingService.updateAndGetPlotSvg(nextValue);

        }
    }


}
