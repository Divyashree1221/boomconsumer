/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderAccessoriesDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author NandiniC
 */
public class OrderAccessoriesRowMapper implements RowMapper<OrderAccessoriesDTO> {
    
      EncryptDecrypt ed = new EncryptDecrypt();
      
        @Override
    public OrderAccessoriesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderAccessoriesDTO data = new OrderAccessoriesDTO();
        data.setOrderAccessoriesId(ed.encrypt(String.valueOf(rs.getLong("order_accessories_id"))));
        data.setOrderSummaryId(rs.getString("order_summary_id"));
        data.setModelAccessoriesId(rs.getString("model_accessories_id"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }   
    
    
}
