/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.dto;

import com.boommotors.btp.user.dto.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.List;
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
public class ModelAccessoriesDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("model_accessories_id")
    private String modelAccessoriesId;
    
    @JsonProperty("model_accessories_details")
    private String modelAccessoriesDetails; 
        
    @JsonProperty("price")
    private String price;
    
}
