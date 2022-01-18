/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author NandiniC
 */
public class EmailOtpRequest {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("email_otp")
    private Integer emailOtp;
    
    @JsonProperty("email_id")
    private String emailId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getEmailOtp() {
        return emailOtp;
    }

    public void setEmailOtp(Integer emailOtp) {
        this.emailOtp = emailOtp;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
