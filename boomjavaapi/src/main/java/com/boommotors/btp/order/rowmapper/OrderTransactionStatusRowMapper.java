/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderTransactionStatusDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author om
 */
public class OrderTransactionStatusRowMapper implements RowMapper<OrderTransactionStatusDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OrderTransactionStatusDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderTransactionStatusDTO data = new OrderTransactionStatusDTO();

        data.setTransactionStatusId(ed.encrypt(String.valueOf(rs.getLong("transaction_status_id"))));
        data.setTransactionStatus(rs.getString("transaction_status"));
        data.setOrderTransactionStatusId(ed.encrypt(String.valueOf(rs.getLong("order_transaction_status_id"))));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setVariantName(rs.getString("variant_name"));
        data.setOrder_transactionStatusId(rs.getInt("order_transactionStatusId"));

        return data;
    }

}
