/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dto;

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
public class TransactionDetailsDTO {
    
    private static final long serialVersionUID = 1L;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("particulars")
    private String particulars;
    
    @JsonProperty("payments")
    private Double payments;

    @JsonProperty("seq")
    private Long seq;
    
}
