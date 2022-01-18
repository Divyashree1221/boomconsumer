/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.rowmapper;

import com.boommotors.btp.consumer.dto.ConsumerVehicleMappingDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya Softcons
 */
public class ConsumerVehicleMappingRowMapper implements RowMapper<ConsumerVehicleMappingDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public ConsumerVehicleMappingDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConsumerVehicleMappingDTO data = new ConsumerVehicleMappingDTO();
        data.setConsumerVehicleMappingId(ed.encrypt(String.valueOf(rs.getLong("consumer_vehicle_mapping_id"))));
        data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
        data.setDealerId(ed.encrypt(String.valueOf(rs.getLong("dealer_id"))));
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setVariantName(rs.getString("variant_name"));
        data.setColourName(rs.getString("colour_name"));
        data.setImeiNumber(rs.getString("imei_number"));
        data.setOtp(rs.getInt("otp"));
        data.setOtpVerified(rs.getBoolean("otp_verified"));
        data.setBookingDate(rs.getTimestamp("booking_date"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setClusterId(rs.getString("cluster_id"));
        data.setChassisNumber(rs.getString("chassis_number"));
        data.setRegistrationNumber(rs.getString("registration_number"));
        data.setFinalSave(rs.getBoolean("final_save"));
     
        return data;
}
}
