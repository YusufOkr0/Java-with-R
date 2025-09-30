package com.swe307.first_project.service;

import jakarta.annotation.PostConstruct;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class RCallerService {

    private static final String FORMULA_FILE = "script/plot.R";

    private Context context;

    @PostConstruct
    private void loadTheSourceCode() {
        ClassPathResource R_SOURCE_CODE = new ClassPathResource(FORMULA_FILE);

        try (InputStream scriptFile = R_SOURCE_CODE.getInputStream();
             InputStreamReader reader = new InputStreamReader(scriptFile)) {
            Source source = Source.newBuilder(
                    "R",
                    reader,
                    "R"
            ).build();

            context = Context.newBuilder("R")
                    .allowAllAccess(true)
                    .build();
            context.eval(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAndGetPlotSvg(double value) {
        try {
            Value rFunction = context
                    .getBindings("R")
                    .getMember("update_plot_svg");
            rFunction.execute(value);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update R plot", e);
        }
    }





}
