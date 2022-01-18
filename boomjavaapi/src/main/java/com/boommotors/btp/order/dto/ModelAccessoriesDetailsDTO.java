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
public class ModelAccessoriesDetailsDTO {
    
    private static final long serialVersionUID = 1L;

    @JsonProperty("model_accessories_id")
    private String modelAccessoriesId;

    @JsonProperty("model_accessories_details")
    private String modelAccessoriesDetails;

    @JsonProperty("price")
    private Double price;
    
}
