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
public class TypeAttributeGroupsDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("type_attributegroups_id")
    private String typeAttributegroupsId;
    
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("attributes")
    private String[] attributes;
}
