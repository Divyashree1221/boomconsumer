/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author NandiniC
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("order_detail_id")
    private String orderDetailId;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("razorpay_order_id")
    private String razorpayOrderId;
    
    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

}
