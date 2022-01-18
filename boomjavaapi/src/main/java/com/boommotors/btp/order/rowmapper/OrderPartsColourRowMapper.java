/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderPartsColourDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author NandiniC
 */
public class OrderPartsColourRowMapper implements RowMapper<OrderPartsColourDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OrderPartsColourDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OrderPartsColourDTO data = new OrderPartsColourDTO();

        data.setVariantPartsColourId(ed.encrypt(String.valueOf(rs.getLong("order_parts_colour_id"))));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setVariantPartsColourId(ed.encrypt(String.valueOf(rs.getLong("variant_parts_colour_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }
}
