/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
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
public class UserOrderDetailsDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("email_id")
    private String emailId;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;
    
    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("colour_name")
    private String colourName;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("razorpay_payment_id")
    private String razorpayPaymentId;

    @JsonProperty("razorpay_order_id")
    private String razorpayOrderId;

}
