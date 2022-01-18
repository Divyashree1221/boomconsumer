/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.dto;

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
public class DealerReferencesDTO {

    @JsonProperty("dealer_references_id")
    private String dealerReferencesId;

    @JsonProperty("dealer_id")
    private String dealerId;

    @JsonProperty("nameaddress")
    private String nameaddress;

    @JsonProperty("occupation")
    private String occupation;
    
    @JsonProperty("years_known")
    private String yearsKown;

    @JsonProperty("contact_person")
    private String contactPerson;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

}
