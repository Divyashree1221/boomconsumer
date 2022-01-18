/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.dto;

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
public class MainAddOnsDTO {
   
    private static final long serialVersionUID = 1L;

    @JsonProperty("main_add_ons_id")
    private String mainAddOnsId;
    
    @JsonProperty("main_add_ons_name")
    private String mainAddOnsName;
    
    @JsonProperty("model_id")
    private String modelId;
    
    @JsonProperty("created_by")
    private String createdBy;
    
    @JsonProperty("created_date")
    private Timestamp createdDate;
    
    @JsonProperty("updated_by")
    private String updatedBy;
    
    @JsonProperty("updated_date")
    private Timestamp updatedDate;
        
    
}
