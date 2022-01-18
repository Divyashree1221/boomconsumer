/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.payload;

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
public class ConsumerchangePasswordRequest {
    
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("current_password")
    private String currentPassword;
    
    @JsonProperty("new_password")
    private String newPassword;
}
