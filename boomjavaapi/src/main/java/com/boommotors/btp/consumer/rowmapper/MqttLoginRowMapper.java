/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.rowmapper;

import com.boommotors.btp.consumer.dto.ConsumerDetailsDTO;
import com.boommotors.btp.consumer.dto.DashboardDataDTO;
import com.boommotors.btp.consumer.dto.MqttLoginDTO;
import com.boommotors.btp.util.DatabaseUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class MqttLoginRowMapper implements RowMapper<MqttLoginDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();
    DatabaseUtil du = new DatabaseUtil();

    @Override
    public MqttLoginDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MqttLoginDTO data = new MqttLoginDTO();

        data.setIm(rs.getLong("im"));
        data.setMac(rs.getString("mac"));
        data.setILt(rs.getDouble("l_lt"));
        data.setILtd(rs.getString("l_ltd"));
        data.setILn(rs.getDouble("l_ln"));
        data.setILnd(rs.getString("l_lnd"));
        data.setGfx(rs.getInt("g_fx"));
        data.setBs(rs.getInt("bs"));
        data.setClid(rs.getString("clid"));
        data.setOdo(rs.getInt("odo"));
        data.setDat(rs.getInt("dat"));
        data.setTm(rs.getInt("tm"));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        
        return data;
    }

}
