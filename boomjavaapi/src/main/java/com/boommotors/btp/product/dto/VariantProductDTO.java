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
public class VariantProductDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("varint_name")
    private String varintName;

    @JsonProperty("excerpt")
    private String excerpt;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("variant_slug")
    private String variantSlug;
    
    @JsonProperty("variant_sku")
    private String variantSku;
    
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
    
    @JsonProperty("variant_images")
    private String variantImages;
    
    @JsonProperty("badges")
    private String badges;
 
    @JsonProperty("compatibility")
    private Integer compatibility;
    
    @JsonProperty("brand_slug")
    private String brandSlug;
    
    @JsonProperty("brand_name")
    private String brandName;
    
    @JsonProperty("brand_image")
    private String brandImage;
    
    @JsonProperty("country")
    private String country;
    
    @JsonProperty("type_slug")
    private String typeSlug;
    
    @JsonProperty("type_name")
    private String typeName;
    
    @JsonProperty("type_attributegroups_name")
    private String typeAttributegroupsName;
    
    @JsonProperty("type_attributegroups_slug")
    private String typeAttributegroupsSlug;
    
    @JsonProperty("type_attributegroups_attributes")
    private String typeAttributegroupsAttributes;
    
    @JsonProperty("attributes_name")
    private String attributesName;
    
    @JsonProperty("attributes_slug")
    private String attributesSlug;
    
    @JsonProperty("attributes_featured")
    private String attributesFeatured;
    
    @JsonProperty("attributes_values_name")
    private String attributesValuesName;
    
    @JsonProperty("attributes_values_slug")
    private String attributesValuesSlug;
    
    @JsonProperty("options_type")
    private String optionsType;
    
    @JsonProperty("options_slug")
    private String optionsSlug;
    
    @JsonProperty("options_name")
    private String optionsName;
    
    @JsonProperty("options_values_slug")
    private String optionsValuesSlug;
    
    @JsonProperty("options_values_name")
    private String optionsValuesName;
    
    @JsonProperty("options_values_colour")
    private String optionsValuesColour;
    
    @JsonProperty("tags")
    private String tags;
    
    @JsonProperty("categories_id")
    private Integer categoriesId;
    
    @JsonProperty("categories_type")
    private String categoriesType;
    
    @JsonProperty("categories_name")
    private String categoriesName;
    
    @JsonProperty("categories_slug")
    private String categoriesSlug;
    
    @JsonProperty("categories_image")
    private String categoriesImage;
    
    @JsonProperty("categories_items")
    private Integer categoriesItems;
    
    @JsonProperty("categories_parent")
    private String categoriesParent;
    
    @JsonProperty("categories_layout")
    private String categoriesLayout;
    
    @JsonProperty("customfields")
    private String customfields;
    
    @JsonProperty("cc_customfileds")
    private String ccCustomfileds;
    
    @JsonProperty("vcf_customfield")
    private String vcfCustomfield;
    
}
