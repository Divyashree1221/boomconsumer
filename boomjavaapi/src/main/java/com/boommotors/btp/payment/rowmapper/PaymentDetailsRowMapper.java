/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.rowmapper;

import com.boommotors.btp.payment.dto.PaymentDetailsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class PaymentDetailsRowMapper implements RowMapper<PaymentDetailsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public PaymentDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PaymentDetailsDTO data = new PaymentDetailsDTO();

        data.setRazorpayPaymentId(rs.getString("razorpay_payment_id"));
        data.setRazorpayOrderId(rs.getString("razorpay_order_id"));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setVariantName(rs.getString("variant_name"));
        data.setModelName(rs.getString("model_name"));
        data.setAdvanceAmount(rs.getDouble("advance_amount"));
        data.setAmountPaid(rs.getDouble("amount_paid"));
        
        return data;
    }
}