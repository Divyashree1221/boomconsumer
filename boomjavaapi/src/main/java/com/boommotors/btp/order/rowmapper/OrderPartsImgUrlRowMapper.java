/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderPartsImgUrlDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class OrderPartsImgUrlRowMapper implements RowMapper<OrderPartsImgUrlDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OrderPartsImgUrlDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OrderPartsImgUrlDTO data = new OrderPartsImgUrlDTO();

        data.setVariantPartsColourId(ed.encrypt(String.valueOf(rs.getLong("order_parts_colour_id"))));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setBaseImage(rs.getString("base_image"));
        data.setVariantPartsColourId(ed.encrypt(String.valueOf(rs.getLong("variant_parts_colour_id"))));
        data.setImageUrl(rs.getString("image_url"));
        
        return data;
    }
}