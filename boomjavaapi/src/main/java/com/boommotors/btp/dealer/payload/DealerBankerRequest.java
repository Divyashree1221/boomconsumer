/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.payload;

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
public class DealerBankerRequest {
    
    @JsonProperty("dealer_banker_id")
    private String dealerBankerId;

    @JsonProperty("dealer_id")
    private String dealerId;

    @JsonProperty("nameofbank")
    private String nameofbank;

    @JsonProperty("branch")
    private String branch;

    @JsonProperty("duration_of_relationship")
    private String durationOfRelationship;

    @JsonProperty("typeoffacility")
    private String typeoffacility;

    @JsonProperty("current_limit")
    private String currentLimit;
}
