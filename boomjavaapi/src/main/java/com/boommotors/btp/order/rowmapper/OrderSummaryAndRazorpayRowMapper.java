/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.OrderSummaryAndRazorpayDTO;
import com.boommotors.btp.order.dto.TransactionDetailsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class OrderSummaryAndRazorpayRowMapper implements RowMapper<OrderSummaryAndRazorpayDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public OrderSummaryAndRazorpayDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderSummaryAndRazorpayDTO data = new OrderSummaryAndRazorpayDTO();

        data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setVariantName(rs.getString("variant_name"));
        data.setColourName(rs.getString("colour_name"));
        data.setFinanceEmi(rs.getDouble("finance_emi"));
        data.setInsuranceType(rs.getString("insurance_type"));
        data.setInsuranceAmount(rs.getDouble("insurance_amount"));
        data.setWarrantyAmount(rs.getDouble("warranty_amount"));
        data.setDelivery_type(rs.getString("delivery_type"));
        data.setPincode(rs.getString("pincode"));
        data.setCity(rs.getString("city"));
        data.setState(rs.getString("state"));
        data.setCountry(rs.getString("country"));
        data.setIsOutOfCoverage(rs.getBoolean("is_out_of_coverage"));
        data.setQuantity(rs.getInt("quantity"));
        data.setRfpSent(rs.getBoolean("rfp_sent"));
        data.setRfpSent(rs.getBoolean("rfp_sent"));
        data.setTotalAmount(rs.getDouble("total_amount"));
        data.setAdvanceAmount(rs.getDouble("advance_amount"));
        data.setAmountPaid(rs.getDouble("amount_paid"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setPackageName(rs.getString("package_name"));
        data.setColourAmount(rs.getDouble("colour_amount"));
        data.setExShowroom(rs.getDouble("ex_showroom"));
        data.setPackageAmt(rs.getDouble("package_amt"));
        data.setSubTotal(rs.getDouble("sub_total"));
        data.setGstAmt(rs.getDouble("gst_amt"));
        data.setGrossTotal(rs.getDouble("gross_total"));
        data.setSubsidyAmt(rs.getDouble("subsidy_amt"));
        data.setTenure(rs.getInt("tenure"));
        data.setExchange(rs.getString("exchange"));
        data.setRazorpayOrderId(rs.getString("razorpay_order_id"));
        data.setTotalPaid(rs.getDouble("total_paid"));

        List<TransactionDetailsDTO> transData = new ArrayList<>();
        if (!rs.wasNull()) {
            data.setTransactionDetails(transData);

        } else {
            data.setTransactionDetails(transData);

        }

        return data;
    }

}
