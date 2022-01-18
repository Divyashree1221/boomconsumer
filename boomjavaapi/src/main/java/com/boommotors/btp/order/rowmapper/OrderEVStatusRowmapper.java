/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderEVStatusDTO;
import com.boommotors.btp.util.DatabaseUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class OrderEVStatusRowmapper implements RowMapper<OrderEVStatusDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();
    DatabaseUtil du = new DatabaseUtil();

    @Override
    public OrderEVStatusDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderEVStatusDTO data = new OrderEVStatusDTO();

        data.setEvStatusId(ed.encrypt(String.valueOf(rs.getLong("ev_status_id"))));
      data.setEvStatus(rs.getString("ev_status"));
        data.setOrderEvStatusId(ed.encrypt(String.valueOf(rs.getLong("order_ev_status_id"))));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setVariantName(rs.getString("variant_name"));
        data.setOrder_EVStatusId(rs.getInt("order_EVStatusId"));

//        boolean doesEvStatusExists = du.isThere(rs, "ev_status");
//        if (doesEvStatusExists) {
//            if (!rs.wasNull()) {
//                data.setEvStatus(rs.getString("ev_status"));
//            } else {
//                data.setEvStatus("");
//            }
//        } else {
//            data.setEvStatus("");
//        }
//
//        boolean doesVariantExists = du.isThere(rs, "variant_name");
//        if (doesVariantExists) {
//            if (!rs.wasNull()) {
//                data.setVariantName(rs.getString("variant_name"));
//            } else {
//                data.setVariantName("");
//            }
//        } else {
//            data.setVariantName("");
//        }
//
//        boolean doesOrderEVStatusIdExists = du.isThere(rs, "order_EVStatusId");
//        if (doesOrderEVStatusIdExists) {
//            if (!rs.wasNull()) {
//                data.setOrder_EVStatusId(rs.getInt("order_EVStatusId"));
//            } else {
//                data.setOrder_EVStatusId(0);
//            }
//        } else {
//            data.setOrder_EVStatusId(0);
//        }

        return data;
    }

}
