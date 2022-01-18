/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.MainAddOnsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class MainAddOnsRowMapper implements RowMapper<MainAddOnsDTO>{
    
     EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public MainAddOnsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MainAddOnsDTO data = new MainAddOnsDTO();
        data.setMainAddOnsId(ed.encrypt(String.valueOf(rs.getInt("main_add_ons_id"))));    
        data.setMainAddOnsName(rs.getString("main_add_ons_name"));
        data.setModelId(ed.encrypt(String.valueOf(rs.getInt("model_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));   
                
        return data;
    }
    
}
