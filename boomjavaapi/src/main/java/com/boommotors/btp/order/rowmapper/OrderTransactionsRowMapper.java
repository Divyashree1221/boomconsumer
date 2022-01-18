/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class OrderTransactionsRowMapper implements RowMapper<OrderTransactionDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OrderTransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderTransactionDTO data = new OrderTransactionDTO();

        data.setOrderTransactionsId(ed.encrypt(String.valueOf(rs.getLong("order_transactions_id"))));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setRazorpayPaymentId(rs.getString("razorpay_payment_id"));
        data.setRazorpayOrderId(rs.getString("razorpay_order_id"));
        data.setPaymentSuccess(rs.getInt("payment_success"));
        data.setStatus(rs.getString("status"));
        data.setPaymentMethod(rs.getString("payment_method"));
        data.setCardId(rs.getString("card_id"));
        data.setCaptured(rs.getBoolean("captured"));
        data.setEntity(rs.getString("entity"));
        data.setCurrency(rs.getString("currency"));
        data.setDescription(rs.getString("description"));
        data.setBank(rs.getString("bank"));
        data.setWallet(rs.getString("wallet"));
        data.setVpa(rs.getString("vpa"));
        data.setFee(rs.getDouble("fee"));
        data.setRefundStatus(rs.getString("refund_status"));
        data.setAuthCode(rs.getString("auth_code"));
        data.setAmountRefunded(rs.getDouble("amount_refunded"));
        data.setErrorReason(rs.getString("error_reason"));
        data.setErrorDescription(rs.getString("error_description"));
        data.setContact(rs.getString("contact"));
        data.setInvoiceId(rs.getString("invoice_id"));
        data.setInternational(rs.getBoolean("international"));
        data.setAmount(rs.getDouble("amount"));
        data.setErrorSource(rs.getString("error_source"));
        data.setErrorStep(rs.getString("error_step"));
        data.setTax(rs.getDouble("tax"));
        data.setErrorCode(rs.getString("error_code"));
        data.setRazorpayTransactionId(rs.getString("razorpay_transaction_id"));
        data.setQuantity(rs.getInt("quantity"));
        data.setEmail(rs.getString("email_id"));

        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}
