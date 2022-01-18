/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.ModelAccessoriesDetailsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class ModelAccessoriesDetailsRowMapper implements RowMapper<ModelAccessoriesDetailsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public ModelAccessoriesDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        ModelAccessoriesDetailsDTO data = new ModelAccessoriesDetailsDTO();

        data.setModelAccessoriesId(ed.encrypt(String.valueOf(rs.getLong("model_accessories_id"))));
        data.setModelAccessoriesDetails(rs.getString("model_accessories_details"));
        data.setPrice(rs.getDouble("price"));

        return data;
    }
}
