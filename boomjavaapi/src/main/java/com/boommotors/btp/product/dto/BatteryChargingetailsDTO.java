/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.dto;

import com.boommotors.btp.user.dto.*;
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
public class BatteryChargingetailsDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("battery_type")
    private String batteryType;
    
    @JsonProperty("capacity")
    private String capacity;
    
    @JsonProperty("nominal_voltage")
    private String nominalVoltage;

    @JsonProperty("water_dust_resistance")
    private String waterDustResistance;
    
    @JsonProperty("regular_charging_time")
    private String regularChargingTime;
    
    @JsonProperty("avg_swapping_time")
    private String avgSwappingTime;
    
    @JsonProperty("battery_image")
    private String batteryImage;

}

