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
public class DealershipEnquiryRequest {
    
   @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("contact_no")
    private String contactNo;

    @JsonProperty("is_registered")
    private Boolean isRegistered;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_address")
    private String companyAddress;

    @JsonProperty("designation")
    private String designation;

    @JsonProperty("preferred_state1")
    private String preferredState1;

    @JsonProperty("preferred_state2")
    private String preferredState2;

    @JsonProperty("district_city")
    private String districtCity;

    @JsonProperty("is_owned_leased")
    private Boolean isOwnedLeased;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("is_experienced")
    private Boolean isExperienced;

    @JsonProperty("heard_from_where")
    private String heardFromWhere;
    
    @JsonProperty("comments")
    private String comments;
    
}
