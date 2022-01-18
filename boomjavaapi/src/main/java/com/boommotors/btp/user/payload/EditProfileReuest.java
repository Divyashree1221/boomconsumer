/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.payload;
import com.boommotors.btp.payment.payload.*;
import com.boommotors.btp.payload.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Ramya
 */
public class EditProfileReuest {
    
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("first_name")
    private String firstName;
    
     @JsonProperty("last_name")
    private String lastName;

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
     
     

}
