/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.user.rowmapper.*;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class ModelRowMapper implements RowMapper<ModelDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public ModelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ModelDTO data = new ModelDTO();
        data.setModelId(ed.encrypt(String.valueOf(rs.getLong("model_id"))));
        data.setModelName(rs.getString("model_name"));       
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
                
        return data;
}

    
}
