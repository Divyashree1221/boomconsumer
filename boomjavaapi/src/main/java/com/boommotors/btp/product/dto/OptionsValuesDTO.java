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
public class OptionsValuesDTO {
 private static final long serialVersionUID = 1L;

    @JsonProperty("options_values_id")
    private String optionsValuesId;
    
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("colour")
    private String colour; 

}


