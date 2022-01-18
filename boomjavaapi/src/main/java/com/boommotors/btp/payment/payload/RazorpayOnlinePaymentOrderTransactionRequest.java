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
 * @author Ramya
 */
public class RazorpayOnlinePaymentOrderTransactionRequest {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("razorpay_payment_id")
    private String razorpayPaymentId;

    @JsonProperty("razorpay_order_id")
    private String razorpayOrderId;

    @JsonProperty("order_detail_id")
    private String orderDetailId;
    
    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("signature")
    private String signature;

    public String getUserId() {
        return userId;
    }

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public String getSignature() {
        return signature;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    

}
