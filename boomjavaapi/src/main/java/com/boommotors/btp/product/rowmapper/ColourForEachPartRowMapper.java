/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.ColorsForEachpartDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class ColourForEachPartRowMapper implements RowMapper<ColorsForEachpartDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public ColorsForEachpartDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ColorsForEachpartDTO data = new ColorsForEachpartDTO();
        data.setVariantPartsColourId(ed.encrypt(String.valueOf(rs.getLong("variant_parts_colour_id"))));
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setColourCode(rs.getString("colour_code"));
        data.setColourName(rs.getString("colour_name"));
        data.setImageUrl(rs.getString("image_url"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getInt("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getInt("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setVariantPartName(rs.getString("variant_part_name"));
        data.setBaseImage(rs.getString("base_image"));
                
        return data;
}

    
}
