/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.TypeAttGrpAttributeGroupsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class TypeAttGrpAttributeGroupsRowmapper implements RowMapper<TypeAttGrpAttributeGroupsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public TypeAttGrpAttributeGroupsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TypeAttGrpAttributeGroupsDTO data = new TypeAttGrpAttributeGroupsDTO();
        data.setTypeAttgrpAttributesId(ed.encrypt(String.valueOf(rs.getLong("type_attgrp_attributes_id"))));
        data.setAttributes(rs.getString("attributes"));
       
        return data;
    }

}
