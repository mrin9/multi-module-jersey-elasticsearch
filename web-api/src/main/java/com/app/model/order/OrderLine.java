package com.app.model.order;

import lombok.Data;

@Data
public class OrderLine {
    String productId;
    String productName;
    String productType;
    String quantity;
    String price;
    
     public OrderLine(){}
    
}
