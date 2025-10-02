package com.swe307.first_project.repository;

import com.swe307.first_project.entity.RValue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ValueRepository extends MongoRepository<RValue,String> {
    Optional<RValue> findByIndex(int index);

}
