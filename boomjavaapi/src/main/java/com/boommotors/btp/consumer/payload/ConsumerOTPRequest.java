/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.payload;

import com.boommotors.btp.dealer.payload.*;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ConsumerOTPRequest {
    
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("otp")
    private Integer otp;
}
