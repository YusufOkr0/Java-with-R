package com.swe307.first_project.service;

import com.swe307.first_project.entity.RValue;
import org.springframework.stereotype.Service;


@Service
public class PlotGeneratorService {

    private static int indexCounter = 1;
    private final RCallerService rCallerService;
    private final MongoDBService mongoDBService;


    public PlotGeneratorService(RCallerService rCallerService, MongoDBService mongoDBService) {
        this.rCallerService = rCallerService;
        this.mongoDBService = mongoDBService;
    }


    public void readTheDataAndPushToR() {
        RValue rValue = mongoDBService.findByIndex(indexCounter);
        rCallerService.updateAndGetPlotSvg(rValue.getValue());
        indexCounter++;
    }

}
