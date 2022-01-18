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
public class OrderPartsImgUrlDTO {
    
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("order_parts_colour_id")
    private String orderPartsColourId;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;
    
    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("base_image")
    private String baseImage;

    @JsonProperty("variant_parts_colour_id")
    private String variantPartsColourId;
    
    @JsonProperty("image_url")
    private String imageUrl;
}
