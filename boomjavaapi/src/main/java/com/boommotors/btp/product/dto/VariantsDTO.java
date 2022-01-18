/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.dto;

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
public class VariantsDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("model_id")
    private String modelId;
    
//    @JsonProperty("model_name")
//    private String modelName;

    @JsonProperty("motor_nominal_power")
    private String motorNominalPower;

    @JsonProperty("motor_peak_power")
    private String motorPeakPower;

    @JsonProperty("top_speed")
    private String topSpeed;

    @JsonProperty("gradeability")
    private String gradeability;

    @JsonProperty("motor_type")
    private String motorType;

    @JsonProperty("wdr_motor")
    private String wdrMotor;

    @JsonProperty("wdr_controller")
    private String wdrController;

    @JsonProperty("tr_eco_mode")
    private String trEcoMode;

    @JsonProperty("tr_power_mode")
    private String trPowerMode;

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

    @JsonProperty("wheel_type")
    private String wheelType;

    @JsonProperty("wheel_size")
    private String wheelSize;

    @JsonProperty("front_tyre_size")
    private String frontTyreSize;

    @JsonProperty("rear_tyre_size")
    private String rearTyreSize;

    @JsonProperty("braking_system")
    private String brakingSystem;

    @JsonProperty("brake_type")
    private String brakeType;

    @JsonProperty("front_suspension")
    private String frontSuspension;

    @JsonProperty("rear_suspension")
    private String rearSuspension;

    @JsonProperty("head_tail_light_indicators")
    private String head_tailLightIndicators;

    @JsonProperty("usb_charger")
    private String usbCharger;

    @JsonProperty("cell_phone_holder")
    private String cellPhoneHolder;

    @JsonProperty("iot")
    private String iot;

    @JsonProperty("wheel_base")
    private String wheelBase;

    @JsonProperty("ground_clearance")
    private String groundClearance;

    @JsonProperty("width")
    private String width;

    @JsonProperty("kerb_weight")
    private String kerbWeight;

    @JsonProperty("underseat_storage")
    private String underseatStorage;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp created_date;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

    @JsonProperty("image")
    private String image;

    @JsonProperty("reg_braking")
    private String regBraking;

    @JsonProperty("iot_key_feature")
    private String iotKeyFeature;

    @JsonProperty("speedometer")
    private String speedometer;

    @JsonProperty("loading_capacity")
    private String loadingCapacity;

    @JsonProperty("warranty")
    private String warranty;
    
    @JsonProperty("base_image")
    private String baseImage;

    @JsonProperty("battery_image")
    private String batteryImage;
    
    @JsonProperty("caption")
    private String caption;
    
    @JsonProperty("image2")
    private String image2;
    
    @JsonProperty("image3")
    private String image3;
    
    @JsonProperty("image4")
    private String image4;
}
