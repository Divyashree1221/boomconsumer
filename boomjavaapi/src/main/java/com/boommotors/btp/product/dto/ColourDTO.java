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
public class ColourDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("colour_id")
    private String colourId;
    
    @JsonProperty("colour_code")
    private String colourCode;

    @JsonProperty("colour_name")
    private String colourName;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

    @JsonProperty("variant_id")
    private String variantId;
    
    @JsonProperty("image_url")
    private String imageUrl;
    
    @JsonProperty("colour_code1")
    private String colourCode1;
    
    @JsonProperty("colour_code2")
    private String colourCode2;

}
