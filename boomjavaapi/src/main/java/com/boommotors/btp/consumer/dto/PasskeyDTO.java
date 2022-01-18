/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.dto;

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
public class PasskeyDTO {

    private static final long serialVersionUID = 1L;
    
    
    @JsonProperty("userid")
    private String userid;
    
    @JsonProperty("im")
    private Long im;
    
    @JsonProperty("mac")
    private String mac;
    
    @JsonProperty("passkey")
    private String passkey;
    
}
