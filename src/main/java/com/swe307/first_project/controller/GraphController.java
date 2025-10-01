package com.swe307.first_project.controller;

import com.swe307.first_project.service.PlotGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping
public class GraphController {

    private final static String FILE_PATH = "src/main/resources/static/plot.svg";

    private final PlotGeneratorService plotGeneratorService;

    public GraphController(PlotGeneratorService plotGeneratorService) {
        this.plotGeneratorService = plotGeneratorService;
    }

    @GetMapping("/plot")
    public ResponseEntity<byte[]> getPlot() {
        try {
            synchronized (plotGeneratorService){
                plotGeneratorService.readTheDataAndPushToR();
            }
            Path path = Path.of(FILE_PATH);
            byte[] svgBytes = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(svgBytes);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
}
