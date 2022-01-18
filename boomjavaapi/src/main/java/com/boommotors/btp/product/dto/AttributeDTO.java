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
public class AttributeDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("attributes_id")
    private String attributesId;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("featured")
    private Boolean featured;
    
    @JsonProperty("values")
    private AttributesValuesDTO values;
//        
//    @JsonProperty("variant_id")
//    private Integer variantId;
//    
//    @JsonProperty("type_attributeGroups_id")
//    private String typeAttributeGroupsId;
//    
//    @JsonProperty("values_name")
//    private String valuesName;
//    
//    @JsonProperty("values_slug")
//    private String valuesSlug;
//    
//    @JsonProperty("created_by")
//    private Integer createdBy;
//    
//    @JsonProperty("created_date")
//    private Timestamp createdDate;
//    
//    @JsonProperty("updated_by")
//    private Integer updatedBy;
//    
//    @JsonProperty("updated_date")
//    private Timestamp updatedDate;

}
