package com.swe307.first_project.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "R-values")
public class RValue {

    @Id
    private String id;
    private int index;
    private double value;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}
