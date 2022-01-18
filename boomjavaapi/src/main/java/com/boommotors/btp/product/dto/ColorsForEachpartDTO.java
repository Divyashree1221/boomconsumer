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
 * @author Ramya
 */
@Getter
@Setter
@NoArgsConstructor
public class ColorsForEachpartDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("variant_parts_colour_id")
    private String variantPartsColourId;
    
    @JsonProperty("variant_id")
    private String variantId;
    
    @JsonProperty("colour_code")
    private String colourCode;

    @JsonProperty("colour_name")
    private String colourName;
    
    @JsonProperty("image_url")
    private String imageUrl;
     
    @JsonProperty("created_by")
    private String createdBy;
    
    @JsonProperty("created_date")
    private Timestamp createdDate;
    
    @JsonProperty("updated_by")
    private String updatedBy;
    
    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    
    @JsonProperty("variant_part_name")
    private String variantPartName;
    
    @JsonProperty("base_image")
    private String baseImage;

 
}
