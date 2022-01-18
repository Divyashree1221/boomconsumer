/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.VariantTagDTO;
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
public class VariantTagRowMapper implements RowMapper<VariantTagDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantTagDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantTagDTO data = new VariantTagDTO();
//        data.setVariantTagsId(ed.encrypt(String.valueOf(rs.getLong("variant_tags_id"))));
        data.setTags(rs.getString("tags"));
//        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
//       
//        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
//        data.setCreatedDate(rs.getTimestamp("created_date"));
//        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
//        data.setUpdatedDate(rs.getTimestamp("updated_date"));
//        data.setRealRange(rs.getString("real_range"));
//        data.setCustomizations(rs.getString("customizations"));

        return data;
    }

}
