/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.AttributeDTO;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.OptionsDTO;
import com.boommotors.btp.product.dto.TypeAttributeGroupsDTO;
import com.boommotors.btp.product.dto.TypeDTO;
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
public class TypeAttributeGroupsRowmapper implements RowMapper<TypeAttributeGroupsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public TypeAttributeGroupsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TypeAttributeGroupsDTO data = new TypeAttributeGroupsDTO();
        data.setTypeAttributegroupsId(ed.encrypt(String.valueOf(rs.getLong("type_attributegroups_id"))));
        data.setSlug(rs.getString("slug"));
        data.setName(rs.getString("name"));
       
        return data;
    }

}
