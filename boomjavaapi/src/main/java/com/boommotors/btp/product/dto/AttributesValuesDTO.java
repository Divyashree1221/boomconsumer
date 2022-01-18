/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boommotors.btp.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.List;
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
public class AttributesValuesDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("attributes_values_id")
    private String attributesValuesId;
     
    @JsonProperty("name")
    private String name;
   
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("attributes_id")
    private String attributesId; 
//   
//    @JsonProperty("created_by")
//    private String createdBy;
//
//    @JsonProperty("created_date")
//    private Timestamp createdDate;
//    
//    @JsonProperty("updated_by")
//    private String updatedBy;
//    
//    @JsonProperty("updated_date")
//    private Timestamp updatedDate;
}

