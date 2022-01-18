/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author NandiniC
 */
public class OrderDetailRowMapper implements RowMapper<OrderDetailDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OrderDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDetailDTO data = new OrderDetailDTO();
        
        data.setOrderDetailId(ed.encrypt(String.valueOf(rs.getLong("order_detail_id"))));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setRazorpayOrderId(rs.getString("razorpay_order_id"));
        data.setPaymentType(rs.getString("payment_type"));
        data.setAmount(rs.getDouble("amount"));
        data.setIsPaid(rs.getBoolean("is_paid"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}
