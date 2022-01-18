/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderFinanceStatusDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class OrderFinanceStatusRowmapper implements RowMapper<OrderFinanceStatusDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OrderFinanceStatusDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderFinanceStatusDTO data = new OrderFinanceStatusDTO();
        
        data.setFinanceStatusId(ed.encrypt(String.valueOf(rs.getLong("finance_status_id"))));
        data.setFinanceStatus(rs.getString("finance_status"));
        data.setOrderFinanceStatusId(ed.encrypt(String.valueOf(rs.getLong("order_finance_status_id"))));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
                  data.setVariantName(rs.getString("variant_name"));
                   data.setOrder_financeStatusId(rs.getInt("order_financeStatusId"));

        return data;
    }

}
