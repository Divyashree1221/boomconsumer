/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.BatteryChargingetailsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class BatteryChargingDetailsRowMapper implements RowMapper<BatteryChargingetailsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public BatteryChargingetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BatteryChargingetailsDTO data = new BatteryChargingetailsDTO();
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));       
        data.setBatteryType(rs.getString("battery_type"));
        data.setCapacity(rs.getString("capacity"));
        data.setNominalVoltage(rs.getString("nominal_voltage"));
        data.setWaterDustResistance(rs.getString("water_dust_resistance"));
        data.setRegularChargingTime(rs.getString("regular_charging_time"));
        data.setAvgSwappingTime(rs.getString("avg_swapping_time"));
        data.setBatteryImage(rs.getString("battery_image"));

        return data;
    }

}
