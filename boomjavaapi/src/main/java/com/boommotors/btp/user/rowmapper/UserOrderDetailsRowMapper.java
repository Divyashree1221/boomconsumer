/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.rowmapper;

import com.boommotors.btp.user.dto.UserOrderDetailsDTO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class UserOrderDetailsRowMapper implements RowMapper<UserOrderDetailsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public UserOrderDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserOrderDetailsDTO data = new UserOrderDetailsDTO();
        data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
        data.setFirstname(rs.getString("firstname"));
        data.setLastname(rs.getString("lastname"));
        data.setEmailId(rs.getString("email_id"));
        data.setMobileNumber(rs.getString("mobile_number"));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setVariantName(rs.getString("variant_name"));
        data.setColourName(rs.getString("colour_name"));
        data.setQuantity(rs.getInt("quantity"));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setOrderStatus(rs.getString("order_status"));
        data.setPaymentStatus(rs.getString("payment_status"));
        data.setRazorpayPaymentId(rs.getString("razorpay_payment_id"));
        data.setRazorpayOrderId(rs.getString("razorpay_order_id"));

        return data;
    }

}
