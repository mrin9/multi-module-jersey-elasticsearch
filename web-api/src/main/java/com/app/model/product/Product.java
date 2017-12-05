package com.app.model.product;

import lombok.*;
import java.util.*;
import io.swagger.annotations.ApiModelProperty;

@Data
public class Product  {
    private String  productId;
    private String  productName;
    private Long    listPrice;
    
    @ApiModelProperty(allowableValues = "camera, laptop, tablet, phone, sd-card") 
    private String productType;
    private String  unit;
    
    public Product(){}
}
