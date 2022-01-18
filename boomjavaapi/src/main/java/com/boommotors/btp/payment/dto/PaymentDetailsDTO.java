/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author divyashree
 */
@Getter
@Setter
@NoArgsConstructor
public class PaymentDetailsDTO {
       
    private static final long serialVersionUID = 1L;


    @JsonProperty("razorpay_payment_id")
    private String razorpayPaymentId;
    
    @JsonProperty("razorpay_order_id")
    private String razorpayOrderId;   

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("model_name")
    private String modelName;

    @JsonProperty("advance_amount")
    private Double advanceAmount;

    @JsonProperty("amount_paid")
    private Double amountPaid;
   
}
