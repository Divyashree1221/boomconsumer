/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Ramya
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("amount_paid")
    private Integer amountPaid;

    @JsonProperty("amount_due")
    private Integer amountDue;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("receipt")
    private String receipt;

    @JsonProperty("offer_id")
    private String offerId;
    
    @JsonProperty("status")
    private String status;

    @JsonProperty("attempts")
    private Integer attempts;
    
    @JsonProperty("order_detail_id")
    private String orderDetailId;

}
