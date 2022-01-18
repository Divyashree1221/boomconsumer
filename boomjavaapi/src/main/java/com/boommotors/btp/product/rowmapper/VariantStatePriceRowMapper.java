/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.VariantStatePriceDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class VariantStatePriceRowMapper implements RowMapper<VariantStatePriceDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantStatePriceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantStatePriceDTO data = new VariantStatePriceDTO();
        data.setVariantStatePriceId(ed.encrypt(String.valueOf(rs.getInt("variant_state_price_id"))));
        data.setVariantId(ed.encrypt(String.valueOf(rs.getInt("variant_id"))));
        data.setStateName(rs.getString("state_name"));
        data.setBasePrice(rs.getDouble("base_price"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
       
        return data;
    }
    
}
