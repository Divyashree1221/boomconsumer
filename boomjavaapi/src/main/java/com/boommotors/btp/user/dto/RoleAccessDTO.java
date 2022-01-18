/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author rjanumpally
 */
@Getter
@Setter
@NoArgsConstructor
public class RoleAccessDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("role_id")
    private Integer roleId;

    @JsonProperty("source")
    private Integer source;
}
