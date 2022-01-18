/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.dto;

import com.boommotors.btp.user.dto.*;
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
public class VariantCompatibilityDTO {

    private static final long serialVersionUID = 1L;

//    @JsonProperty("variant_compatibility_id")
//    private String variantCompatibilityId;

    @JsonProperty("compatibility")
    private Integer compatibility;

//    @JsonProperty("variant_id")
//    private String variantId;
//    
//    @JsonProperty("created_by")
//    private String createdBy;
//     
//    @JsonProperty("updated_by")
//    private String updatedBy;
//
//    @JsonProperty("created_date")
//    private Timestamp createdDate;
//
//    @JsonProperty("updated_date")
//    private Timestamp updatedDate;
//
//    @JsonProperty("real_range")
//    private String realRange;
//
//    @JsonProperty("customizations")
//    private String customizations;

}
