/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author divyashree
 */
@Getter
@Setter
@NoArgsConstructor
public class ConsumerVehicleMappingRequest {

    @JsonProperty("consumer_vehicle_mapping_id")
    private String consumerVehicleMappingId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("dealer_id")
    private String dealerId;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("colour_name")
    private String colourName;

    @JsonProperty("imei_number")
    private String imeiNumber;

    @JsonProperty("otp")
    private Integer otp;

    @JsonProperty("otp_verified")
    private Boolean otpVerified;

    @JsonProperty("booking_date")
    private Timestamp bookingDate;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("pincode")
    private String pincode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("action")
    private String action;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    /////////////user data
    @JsonProperty("firstname")
    private String firstname;
    
     @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("email_id")
    private String emailId;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("password")
    private String password;

    /////////////order summary data
    @JsonProperty("delivery_type")
    private String deliveryType;

    @JsonProperty("is_out_of_coverage")
    private Boolean isOutOfCoverage;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("rfp_sent")
    private Boolean rfpSent;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("amount_paid")
    private Double amountPaid;

    @JsonProperty("model_id")
    private String modelId;

    @JsonProperty("tenure")
    private Integer tenure;

    @JsonProperty("exchange")
    private String exchange;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("cluster_id")
    private String clusterId;

    @JsonProperty("chassis_number")
    private String chassisNumber;
    
    @JsonProperty("registration_number")
    private String registrationNumber;
    
    @JsonProperty("final_save")
    private Boolean finalSave;

}
