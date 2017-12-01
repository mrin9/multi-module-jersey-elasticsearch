/**
 * OrderInfo contains extra details of the order
 * Such as Customer Info (Name, Company , Email)
 */
package com.app.model.order;

import lombok.*;
import java.util.*;
import java.math.*;
import io.swagger.annotations.ApiModelProperty;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderInfo {
    private Integer orderId;
    private Integer employeeId;
    private Integer customerId;
    private Date    orderDate;
    private Date    shippedDate;
    private Date    paidDate;
    private String  shipName;
    private String  shipAddress1;
    private String  shipAddress2;
    private String  shipCity;
    private String  shipState;
    private String  shipPostalCode;
    private String  shipCountry;
    private BigDecimal shippingFee;
    @ApiModelProperty(allowableValues = "Check, Cash, Card") private String paymentType;
    @ApiModelProperty(allowableValues = "On Hold, Shipped, Complete, New")private String orderStatus;

    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerCompany;
    private String employeeName;
    private String employeeDepartment;
    private String employeeJobTitle;

    public OrderInfo(){}

}
