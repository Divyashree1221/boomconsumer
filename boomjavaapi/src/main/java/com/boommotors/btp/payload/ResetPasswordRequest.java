/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author divyashree
 */
public class ResetPasswordRequest { 
    
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
       public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
