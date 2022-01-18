/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.payload;
import com.boommotors.btp.payment.payload.*;
import com.boommotors.btp.payload.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Ramya
 */
public class MyTemplateLikesRequest {
    
    @JsonProperty("contest_templates_id")
    private String contestTemplatesId;
    
    @JsonProperty("user_id")
    private String userId;

    public String getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }
    
    @JsonProperty("logged_in_user_id")
    private String loggedInUserId;


    public String getContestTemplatesId() {
        return contestTemplatesId;
    }

    public String getUserId() {
        return userId;
    }

    public void setContestTemplatesId(String contestTemplatesId) {
        this.contestTemplatesId = contestTemplatesId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
 
}
