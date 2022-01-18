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
public class CategoriesDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("categories_id")
    private String categoriesId;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("image")
    private String image;
        
    @JsonProperty("items")
    private String items;
    
    @JsonProperty("parent")
    private String parent;
    
    @JsonProperty("layout")
    private String layout;
    
    @JsonProperty("customFields")
    private CategoriesCustomfieldsDTO customFields;

}
