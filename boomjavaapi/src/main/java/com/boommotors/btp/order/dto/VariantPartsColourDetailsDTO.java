/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dto;

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
public class VariantPartsColourDetailsDTO {
    
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("variant_parts_colour_id")
    private String variantPartsColourId;
    
    @JsonProperty("variant_part_name")
    private String variantPartName;
   
    @JsonProperty("colour_name")
    private String colourName;

   
}
