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
import com.boommotors.btp.product.dto.VariantImageDTO;
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
public class VariantImageRowmapper implements RowMapper<VariantImageDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantImageDTO data = new VariantImageDTO();
//        data.setVariantImageId(ed.encrypt(String.valueOf(rs.getLong("variant_images_id"))));
        data.setImages(rs.getString("images"));
//        data.setVariantId(rs.getInt("variant_id"));
//        data.setCreatedBy(rs.getInt("created_by"));
//        data.setCreatedDate(rs.getTimestamp("created_date"));
//        data.setUpdatedBy(rs.getInt("updated_by"));
//        data.setUpdatedDate(rs.getTimestamp("updated_date"));
       
        return data;
    }

}
