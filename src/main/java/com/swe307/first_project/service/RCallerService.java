package com.swe307.first_project.service;

import jakarta.annotation.PostConstruct;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class RCallerService {

    private static final String FORMULA_FILE = "script/plot.R";

    private Context context;
    private Source source;





    @PostConstruct
    private void loadTheSourceCode() {
        ClassPathResource R_CODE = new ClassPathResource(FORMULA_FILE);

        try (InputStream scriptFile = R_CODE.getInputStream();
             InputStreamReader reader = new InputStreamReader(scriptFile)) {
            source = Source.newBuilder(
                    "R",
                    reader,
                    "R"
            ).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
