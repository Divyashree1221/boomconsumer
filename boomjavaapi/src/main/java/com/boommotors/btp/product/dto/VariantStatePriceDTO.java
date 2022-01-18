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
public class VariantStatePriceDTO {
    
    private static final long serialVersionUID = 1L;

    @JsonProperty("variant_state_price_id")
    private String variantStatePriceId;

    @JsonProperty("variant_id")
    private String variantId;
    
    @JsonProperty("state_name")
    private String stateName;
    
    @JsonProperty("base_price")
    private Double basePrice;
    
    @JsonProperty("created_by")
    private String createdBy;
    
    @JsonProperty("created_date")
    private Timestamp createdDate;
    
    @JsonProperty("updated_by")
    private String updatedBy;
    
    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    
}
