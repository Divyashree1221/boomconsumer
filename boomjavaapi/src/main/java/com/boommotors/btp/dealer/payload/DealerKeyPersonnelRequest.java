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
public class DealerKeyPersonnelRequest {
    
    @JsonProperty("personnel_id")
    private String personnelId;
    
    @JsonProperty("dealer_id")
    private String dealerId;

    @JsonProperty("designation")
    private String designation;

    @JsonProperty("qualification")
    private String qualification;

    @JsonProperty("personnel_name")
    private String personnelName;

    @JsonProperty("currently_working")
    private String currentlyWorking;

}
