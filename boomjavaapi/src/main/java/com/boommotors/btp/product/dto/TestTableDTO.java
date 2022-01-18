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
import org.w3c.dom.Text;

/**
 *
 * @author Ramya
 */
@Getter
@Setter
@NoArgsConstructor
public class TestTableDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("testfield")
    private String testField;

    @JsonProperty("test2")
    private String test2;
    
    @JsonProperty("dataarray")
    public String[] test2Array = null;

}
