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
public class VariantCustomFieldsRowmapper implements RowMapper<VariantCustomFieldsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantCustomFieldsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantCustomFieldsDTO data = new VariantCustomFieldsDTO();
        data.setVariantCustomFieldsId(ed.encrypt(String.valueOf(rs.getLong("variant_customFields_id"))));
        data.setCustomFields(rs.getString("customfields"));
        data.setVariantId(rs.getInt("variant_id"));
//        data.setCreatedBy(rs.getInt("created_by"));
//        data.setCreatedDate(rs.getTimestamp("created_date"));
//        data.setUpdatedBy(rs.getInt("updated_by"));
//        data.setUpdatedDate(rs.getTimestamp("updated_date"));
       
        return data;
    }

}
