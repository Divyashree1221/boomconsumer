/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.OptionsValuesDTO;
import com.boommotors.btp.user.rowmapper.*;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class OptionsValuesRowMapper implements RowMapper<OptionsValuesDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OptionsValuesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OptionsValuesDTO data = new OptionsValuesDTO();
        data.setOptionsValuesId(ed.encrypt(String.valueOf(rs.getLong("options_values_id"))));
        data.setSlug(rs.getString("slug"));       
        data.setName(rs.getString("name"));
        data.setColour(rs.getString("colour"));
                
        return data;
}
}

