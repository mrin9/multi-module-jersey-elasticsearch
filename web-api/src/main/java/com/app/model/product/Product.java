package com.app.model.product;

import lombok.*;
import java.util.*;
import io.swagger.annotations.ApiModelProperty;

@Data
public class Product  {
    private String  productId;
    private String  productName;
    private String  unit;
    private int  reorderLevel;
    private int  quantityOnHand;
    private long listPrice;
    
    @ApiModelProperty(allowableValues = "camera, laptop, tablet, phone, sd-card") 
    private String productType;
    
    public Product(){}

    public Product(String productId, String productName, String unit, int reorderLevel, int quantityOnHand, long listPrice, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.unit = unit;
        this.reorderLevel = reorderLevel;
        this.quantityOnHand = quantityOnHand;
        this.listPrice = listPrice;
        this.productType = productType;
    }
    
    
}

