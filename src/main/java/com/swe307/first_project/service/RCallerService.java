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
    private Value rFunction;


    public void updateAndGetPlotSvg(double value) {
        try {
            rFunction.execute(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update R plot", e);
        }
    }

    @PostConstruct
    private void load_R_Engine() {
        createContextAndGetRFunction();
    }


    private void createContextAndGetRFunction() {
        ClassPathResource rSourceCode = new ClassPathResource(FORMULA_FILE);
        try (InputStream scriptFile = rSourceCode.getInputStream();
             InputStreamReader reader = new InputStreamReader(scriptFile)) {

            System.out.println("Loading R Contex...");

            Source source = Source.newBuilder("R", reader, "R").build();
            Context context = Context.newBuilder("R").allowAllAccess(true).build();
            context.eval(source);

            System.out.println("Context loaded");

            System.out.println("Loading R Function...");

            rFunction = context
                    .getBindings("R")
                    .getMember("update_plot_svg");

            System.out.println("R Function loaded");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
