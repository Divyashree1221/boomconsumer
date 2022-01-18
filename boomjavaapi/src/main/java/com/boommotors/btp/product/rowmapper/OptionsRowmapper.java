/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.AttributeDTO;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.OptionsDTO;
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
public class OptionsRowmapper implements RowMapper<OptionsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OptionsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OptionsDTO data = new OptionsDTO();
        data.setOptionsId(ed.encrypt(String.valueOf(rs.getLong("options_id"))));
        data.setType(rs.getString("type"));
        data.setSlug(rs.getString("slug"));
        data.setName(rs.getString("name"));
    
        return data;
    }

}
