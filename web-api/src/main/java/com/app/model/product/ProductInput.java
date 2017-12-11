package com.app.model.product;

import lombok.*;
import java.util.*;
import io.swagger.annotations.ApiModelProperty;

@Data
public class ProductInput  {
    
    @ApiModelProperty(value = "Product Name", example = "NewProduct")
    private String  productName;
    
    @ApiModelProperty(value = "Unit of measure", example = "peice")
    private String  unit;

    @ApiModelProperty(value = "Reaorder, when stock fall below this level", example = "5")
    private int  reorderLevel;

    @ApiModelProperty(value = "Quantity In Hand", example = "10")
    private int  quantityOnHand;

    @ApiModelProperty(value = "Price in dollars", example = "10")
    private long listPrice;

    @ApiModelProperty(allowableValues = "camera, laptop, tablet, phone, sd-card") 
    private String productType;
    
    public ProductInput(){}
    
    
}

