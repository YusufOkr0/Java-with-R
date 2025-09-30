package com.swe307.first_project.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVLoader {

    private static final String CSV_FILE = "swe307_pro1.csv";

    public static List<Double> loadCsvData() {
        ClassPathResource resource = new ClassPathResource(CSV_FILE);

        try (InputStream dataStream = resource.getInputStream();
             BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataStream))) {

            return dataReader.lines()
                    .map(line -> line.split(",")[0])
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
