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
public class TypeDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("type_id")
    private String typeId;
    
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("attributeGroups")
    private List<TypeAttributeGroupsDTO> attributeGroups;
    
//    @JsonProperty("variant_id")
//    private Integer variantId;
//    
//    @JsonProperty("created_by")
//    private Integer created_by;
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
