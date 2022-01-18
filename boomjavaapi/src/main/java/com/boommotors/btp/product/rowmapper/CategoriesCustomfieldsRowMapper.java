/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.CategoriesCustomfieldsDTO;
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
public class CategoriesCustomfieldsRowMapper implements RowMapper<CategoriesCustomfieldsDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public CategoriesCustomfieldsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoriesCustomfieldsDTO data = new CategoriesCustomfieldsDTO();
        data.setCategoriesCustomfieldsId(ed.encrypt(String.valueOf(rs.getInt("categories_customfields_id"))));  
        data.setCategoriesId(ed.encrypt(String.valueOf(rs.getInt("categories_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
                
        return data;
}
}

