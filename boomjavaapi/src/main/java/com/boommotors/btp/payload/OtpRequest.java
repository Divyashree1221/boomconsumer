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
public class OtpRequest {
    
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("loginotp")
    private Integer loginotp;
    
     @JsonProperty("mobile_number")
    private String mobileNumber;
     
        private Integer  type; 
        private Integer page;
        

    public Integer getLoginotp() {
        return loginotp;
    }

    public void setLoginotp(Integer loginotp) {
        this.loginotp = loginotp;
    }
    
       public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
     public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    
     public Integer getType() {
        return type;
    }
    
    public void setType(Integer type){
         this.type = type;
    }
    
       public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer source){
         this.page = page;
    }
    
}
