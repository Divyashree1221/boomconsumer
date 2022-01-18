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
public class ConsumerVehicleDetailsDTO {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("battery_left")
    private String batteryLeft;
    
    @JsonProperty("range_left")
    private String rangeLeft;
    
    @JsonProperty("variant_name")
    private String variantName;
    
}
