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
public class NewDealershipEnquiryDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("is_experienced")
    private Boolean isExperienced;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("since_when")
    private String sinceWhen;

    @JsonProperty("pincode1")
    private String pincode1;

    @JsonProperty("pincode2")
    private String pincode2;

    @JsonProperty("pincode3")
    private String pincode3;

    @JsonProperty("amount")
    private Double amount;
    
    @JsonProperty("comments")
    private String comments;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

}
