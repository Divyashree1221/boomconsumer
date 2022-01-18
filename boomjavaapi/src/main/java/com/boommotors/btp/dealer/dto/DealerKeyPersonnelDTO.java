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
public class DealerKeyPersonnelDTO {

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

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

}
