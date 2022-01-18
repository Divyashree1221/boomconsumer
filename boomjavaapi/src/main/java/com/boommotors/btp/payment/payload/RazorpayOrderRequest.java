/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.payload;
import com.boommotors.btp.payload.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author NandiniC
 */
public class RazorpayOrderRequest {
    
    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("payment_type")
    private String paymentType;
    
    @JsonProperty("amount")
    private Double amount;

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    
    
}
