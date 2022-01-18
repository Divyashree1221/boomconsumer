/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.AttributeDTO;
import com.boommotors.btp.product.dto.CategoriesDTO;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.OptionsDTO;
import com.boommotors.btp.product.dto.VariantBadgesDTO;
import com.boommotors.btp.product.dto.VariantCustomFieldsDTO;
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
public class VariantBadgesRowmapper implements RowMapper<VariantBadgesDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantBadgesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantBadgesDTO data = new VariantBadgesDTO();
//        data.setVariantBadgesId(ed.encrypt(String.valueOf(rs.getLong("variant_badges_id"))));
        data.setBadges(rs.getString("badges"));
//        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
//        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getLong("created_by"))));
//        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getLong("updated_by"))));
//        data.setCreatedDate(rs.getTimestamp("created_date"));
//        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        
       
        return data;
    }

}
