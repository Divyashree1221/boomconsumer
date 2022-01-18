/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.VariantsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class VariantsRowMapper implements RowMapper<VariantsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantsDTO data = new VariantsDTO();
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setVariantName(rs.getString("variant_name"));
        data.setModelId(ed.encrypt(String.valueOf(rs.getLong("model_id"))));
//        data.setModelName(rs.getString("model_name"));
        data.setMotorNominalPower(rs.getString("motor_nominal_power"));
        data.setMotorPeakPower(rs.getString("motor_peak_power"));
        data.setTopSpeed(rs.getString("top_speed"));
        data.setGradeability(rs.getString("gradeability"));
        data.setMotorType(rs.getString("motor_type"));
        data.setWdrMotor(rs.getString("wdr_motor"));
        data.setWdrController(rs.getString("wdr_controller"));
        data.setTrEcoMode(rs.getString("tr_eco_mode"));
        data.setTrPowerMode(rs.getString("tr_power_mode"));
        data.setBatteryType(rs.getString("battery_type"));
        data.setCapacity(rs.getString("capacity"));
        data.setNominalVoltage(rs.getString("nominal_voltage"));
        data.setWaterDustResistance(rs.getString("water_dust_resistance"));
        data.setRegularChargingTime(rs.getString("regular_charging_time"));
        data.setAvgSwappingTime(rs.getString("avg_swapping_time"));
        data.setWheelSize(rs.getString("wheel_size"));
        data.setWheelType(rs.getString("wheel_type"));
        data.setFrontTyreSize(rs.getString("front_tyre_size"));
        data.setRearTyreSize(rs.getString("rear_tyre_size"));
        data.setBrakingSystem(rs.getString("braking_system"));
        data.setBrakeType(rs.getString("brake_type"));
        data.setFrontSuspension(rs.getString("front_suspension"));
        data.setRearSuspension(rs.getString("rear_suspension"));
        data.setHead_tailLightIndicators(rs.getString("head_tail_light_indicators"));
        data.setUsbCharger(rs.getString("usb_charger"));
        data.setCellPhoneHolder(rs.getString("cell_phone_holder"));
        data.setIot(rs.getString("iot"));
        data.setWheelBase(rs.getString("wheel_base"));
        data.setGroundClearance(rs.getString("ground_clearance"));
        data.setWidth(rs.getString("width"));
        data.setKerbWeight(rs.getString("kerb_weight"));
        data.setUnderseatStorage(rs.getString("underseat_storage"));
        data.setPrice(rs.getDouble("price"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getLong("created_by"))));
        data.setCreated_date(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getLong("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setImage(rs.getString("image"));
        data.setRegBraking(rs.getString("reg_braking"));
        data.setIotKeyFeature(rs.getString("iot_key_feature"));
        data.setSpeedometer(rs.getString("speedometer"));
        data.setLoadingCapacity(rs.getString("loading_capacity"));
        data.setWarranty(rs.getString("warranty"));
        data.setBaseImage(rs.getString("base_image"));
        data.setBatteryImage(rs.getString("battery_image"));
        data.setCaption(rs.getString("caption"));
        data.setImage2(rs.getString("image2"));
        data.setImage3(rs.getString("image3"));
        data.setImage4(rs.getString("image4"));

        return data;
    }

}
