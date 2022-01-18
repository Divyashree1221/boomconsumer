/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.ColourDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class ColourRowMapper implements RowMapper<ColourDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public ColourDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ColourDTO data = new ColourDTO();
        data.setColourId(ed.encrypt(String.valueOf(rs.getLong("colour_id"))));
        data.setColourCode(rs.getString("colour_code"));
        data.setColourName(rs.getString("colour_name"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setVariantId(ed.encrypt(String.valueOf(rs.getInt("variant_id"))));   
        data.setImageUrl(rs.getString("image_url"));
        data.setColourCode1(rs.getString("colour_code1"));
        data.setColourCode2(rs.getString("colour_code2"));
                
        return data;
}

    
}
