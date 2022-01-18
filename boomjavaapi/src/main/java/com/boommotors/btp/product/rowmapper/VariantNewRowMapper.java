/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.ModelDTO;
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
public class VariantNewRowMapper implements RowMapper<VariantNewDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantNewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantNewDTO data = new VariantNewDTO();
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setName(rs.getString("name"));
        data.setModelId(ed.encrypt(String.valueOf(rs.getInt("model_id"))));
        data.setExcerpt(rs.getString("excerpt"));
        data.setDescription(rs.getString("description"));
        data.setSlug(rs.getString("slug"));
        data.setSku(rs.getString("sku"));
        data.setPartNumber(rs.getString("stock"));
        data.setStock(rs.getString("stock"));
        data.setPrice(rs.getDouble("price"));
        data.setCompareAtPrice(rs.getDouble("compareatprice"));
        data.setRating(rs.getDouble("rating"));
        data.setReviews(rs.getDouble("reviews"));
        data.setAvailability(rs.getString("availability"));
        data.setBrandId(ed.encrypt(String.valueOf(rs.getInt("brand_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        
        return data;
    }

}
