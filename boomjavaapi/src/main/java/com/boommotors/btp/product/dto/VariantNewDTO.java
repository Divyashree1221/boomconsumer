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
public class VariantNewDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("model_id")
    private String modelId;
    
    @JsonProperty("excerpt")
    private String excerpt;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("sku")
    private String sku;
    
    @JsonProperty("part_number")
    private String partNumber;
    
    @JsonProperty("stock")
    private String stock;
    
    @JsonProperty("price")
    private Double price;
    
    @JsonProperty("compareatprice")
    private Double compareAtPrice;
    
    @JsonProperty("rating")
    private Double rating;
    
    @JsonProperty("reviews")
    private Double reviews;
    
    @JsonProperty("availability")
    private String availability;
    
    @JsonProperty("brand_id")
    private String brandId;
    
    @JsonProperty("created_by")
    private String createdBy;
    
    @JsonProperty("created_date")
    private Timestamp createdDate;
    
    @JsonProperty("updated_by")
    private String updatedBy;
    
    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    
}
