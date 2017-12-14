package com.app.model.order;

import lombok.*;
import java.util.*;
import java.math.*;
import io.swagger.annotations.ApiModelProperty;

@Data
public class Order  {
    private String orderId;
    private String userId;
    private String userName;
    private String userEmail;
    @ApiModelProperty(allowableValues = "Check, Cash, Card") private String paymentType;
    @ApiModelProperty(allowableValues = "On Hold, Shipped, Complete, New")private String orderStatus;
    private Date   orderDate;
    private Date   shippedDate;
    private String address1;
    private String address2;
    private String postal;
    private String city;
    private String state;
    private String country;
    private Long totalPrice;
    private List<OrderLine> orderLines;

    /*
    //Constructors
    public Order(){}
    public Order(Integer  id   , Integer employeeId  , Integer customerId  , Date   orderDate   , String orderStatus,
        Date       shippedDate , String  shipName    , String  shipAddress1, String shipAddress2, String shipCity   , String shipState, String shipPostalCode, String shipCountry,
        BigDecimal shippingFee , String  paymentType , Date    paidDate
    ){
        this.id=id;
        this.employeeId  = employeeId ;
        this.customerId  = customerId ;
        this.orderDate   = orderDate;
        this.orderStatus = orderStatus;
        this.shippedDate = shippedDate;
        this.shipName    = shipName;
        this.shipAddress1= shipAddress1;
        this.shipAddress2= shipAddress2;
        this.shipCity    = shipCity;
        this.shipState   = shipState;
        this.shipPostalCode= shipPostalCode;
        this.shipCountry = shipCountry;
        this.shippingFee = shippingFee;
        this.paymentType = paymentType;
        this.paidDate    = paidDate;
    }
    */
}
