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
public class CategoriesCustomfieldsDTO {

 private static final long serialVersionUID = 1L;

    @JsonProperty("categories_customfields_id")
    private String categoriesCustomfieldsId;
    
    @JsonProperty("categories_id")
    private String categoriesId; 
   
    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;
    
    @JsonProperty("updated_by")
    private String updatedBy;
    
    @JsonProperty("updated_date")
    private Timestamp updatedDate;
}
