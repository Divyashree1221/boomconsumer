/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
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
public class UserRoleDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("user_role_id")
    private String userRoleId;

    @JsonProperty("user_role")
    private String userRole;

    @JsonProperty("role_type")
    private String roleType;
    
    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;
}
