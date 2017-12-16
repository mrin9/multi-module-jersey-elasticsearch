package com.app.model.stats;

import lombok.*;

@Data
public class SingleSerise  {
    private String name;
    private Double value;

    public SingleSerise( String name, Double value){
        this.name  = name;
        this.value = value;
    }
}
