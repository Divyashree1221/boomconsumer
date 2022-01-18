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
public class MappedVehiclesDTO {

    private static final long serialVersionUID = 1L;
    
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

    @JsonProperty("booking_date")
    private Timestamp bookingDate;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;
    
    @JsonProperty("cluster_id")
    private String clusterId;
    
    @JsonProperty("chassis_number")
    private String chassisNumber;
    
    @JsonProperty("registration_number")
    private String registrationNumber;
    
    @JsonProperty("final_save")
    private Boolean finalSave;
    
    @JsonProperty("soc")
    private Integer soc;
    
    @JsonProperty("image_url")
    private String imageUrl;
   

}
