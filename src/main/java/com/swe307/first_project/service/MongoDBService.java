package com.swe307.first_project.service;

import com.swe307.first_project.entity.RValue;
import com.swe307.first_project.repository.ValueRepository;
import org.springframework.stereotype.Service;

@Service
public class MongoDBService {
    private final ValueRepository valueRepository;

    public MongoDBService(ValueRepository valueRepository) {
        this.valueRepository = valueRepository;
    }

    public RValue findByIndex(int index) {
        return valueRepository.findByIndex(index)
                .orElseThrow(() -> new RuntimeException("CANNOT FIND THE DOCUMENT"));
    }

}
