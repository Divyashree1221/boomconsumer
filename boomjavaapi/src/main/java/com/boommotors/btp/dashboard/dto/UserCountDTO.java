/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boommotors.btp.dashboard.dto;

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
public class UserCountDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("user_count")
    private Integer userCount;
     
    @JsonProperty("verified_user_count")
    private Integer verifiedUserCount;
   
    @JsonProperty("dealership_enquiry_count")
    private Integer dealershipEnquiryCount;
    
    @JsonProperty("bookings_count")
    private Integer bookingsCount; 
    
    @JsonProperty("bulk_enquiry_count")
    private Integer bulkEnquiryCount;
    
    @JsonProperty("bulk_order_quantity")
    private Integer bulkOrderQuantity;
    
    @JsonProperty("cancelled_count")
    private Integer cancelledCount;

}

