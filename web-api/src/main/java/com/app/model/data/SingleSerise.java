package com.app.model.data;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SingleSerise  {
    private String name;
    private BigDecimal value;

    public SingleSerise( String name, BigDecimal value){
        this.name  = name;
        this.value = value;
    }
}
