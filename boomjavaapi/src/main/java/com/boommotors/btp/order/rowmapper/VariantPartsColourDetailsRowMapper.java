/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.VariantPartsColourDetailsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class VariantPartsColourDetailsRowMapper implements RowMapper<VariantPartsColourDetailsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantPartsColourDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        VariantPartsColourDetailsDTO data = new VariantPartsColourDetailsDTO();

        data.setVariantPartsColourId(ed.encrypt(String.valueOf(rs.getLong("variant_parts_colour_id"))));
        data.setVariantPartName(rs.getString("variant_part_name"));
        data.setColourName(rs.getString("colour_name"));

        return data;
    }
}
