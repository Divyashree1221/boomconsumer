/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.VariantCompatibilityDTO;
import com.boommotors.btp.product.dto.VariantNewDTO;
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
public class VariantCompatibilityRowMapper implements RowMapper<VariantCompatibilityDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantCompatibilityDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantCompatibilityDTO data = new VariantCompatibilityDTO();
//        data.setVariantCompatibilityId(ed.encrypt(String.valueOf(rs.getLong("variant_compatibility_id"))));
        data.setCompatibility(rs.getInt("compatibility"));
       
//        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
//        data.setCreatedDate(rs.getTimestamp("created_date"));
//        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
//        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        
        return data;
    }

}
