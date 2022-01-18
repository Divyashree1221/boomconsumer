/* * To change this license header, choos... by Divyashree H N
Divyashree H N
10:10 AM
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/



package com.boommotors.btp.product.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ProductDTO {



@JsonProperty("id")
private String id;

@JsonProperty("name")
private String name;



@JsonProperty("excerpt")
private String excerpt;



@JsonProperty("description")
private String description;



@JsonProperty("slug")
private String slug;



@JsonProperty("sku")
private String sku;



@JsonProperty("partNumber")
private String partNumber;



@JsonProperty("stock")
private String stock;



@JsonProperty("price")
private Double price;



@JsonProperty("compareAtPrice")
private Double compareAtPrice;



//@JsonProperty("images")
//private List<VariantImageDTO> images;
@JsonProperty("images")
private String[] images;



//@JsonProperty("badges")
//private List<VariantBadgesDTO> badges;
@JsonProperty("badges")
private String[] badges;



@JsonProperty("rating")
private Double rating;



@JsonProperty("reviews")
private Double reviews;



@JsonProperty("availability")
private String availability;

@JsonProperty("compatibility")
private Integer[] compatibility;



//@JsonProperty("brand")
//private List<BrandDTO> brand;

@JsonProperty("brand")
private BrandDTO brand;


@JsonProperty("type")
private TypeDTO type;



@JsonProperty("attributes")
private List<AttributeDTO> attributes;



@JsonProperty("options")
private List<OptionsDTO> options;



//@JsonProperty("tags")
//private List<VariantTagDTO> tags;
@JsonProperty("tags")
private String[] tags;



@JsonProperty("categories")
private List<CategoriesDTO> categories;



@JsonProperty("customFields")
private VariantCustomFieldsDTO customFields;



}