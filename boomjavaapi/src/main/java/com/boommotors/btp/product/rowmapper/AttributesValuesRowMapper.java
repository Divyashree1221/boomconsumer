/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.AttributesValuesDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class AttributesValuesRowMapper implements RowMapper<AttributesValuesDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public AttributesValuesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AttributesValuesDTO data = new AttributesValuesDTO();
        data.setAttributesValuesId(ed.encrypt(String.valueOf(rs.getInt("attributes_values_id"))));
        data.setName(rs.getString("name"));
        data.setSlug(rs.getString("slug"));       
        data.setAttributesId(ed.encrypt(String.valueOf(rs.getInt("attributes_id"))));
//        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
//        data.setCreatedDate(rs.getTimestamp("created_date"));
//        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
//        data.setUpdatedDate(rs.getTimestamp("updated_date"));
                
        return data;
}
}
