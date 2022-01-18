/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.BulkEnquiryDetailsDTO;
import com.boommotors.btp.order.dto.ModelAccessoriesDetailsDTO;
import com.boommotors.btp.order.dto.VariantPartsColourDetailsDTO;
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
public class BulkEnquiryDetailsRowmapper implements RowMapper<BulkEnquiryDetailsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public BulkEnquiryDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BulkEnquiryDetailsDTO data = new BulkEnquiryDetailsDTO();

        data.setBulkEnquiryId(rs.getLong("bulk_enquiry_id"));
        data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
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
        data.setRfpMailSent(rs.getBoolean("rfp_mail_sent"));
        data.setRfpSmsSent(rs.getBoolean("rfp_sms_sent"));
        data.setMailToSalesSent(rs.getBoolean("mail_to_sales_sent"));
        data.setTotalAmount(rs.getDouble("total_amount"));
        data.setAdvanceAmount(rs.getDouble("advance_amount"));
        data.setAmountPaid(rs.getDouble("amount_paid"));
        data.setPackageName(rs.getString("package_name"));
        data.setColourAmount(rs.getDouble("colour_amount"));
        data.setExShowroom(rs.getDouble("ex_showroom"));
        data.setPackageAmt(rs.getDouble("package_amt"));
        data.setSubTotal(rs.getDouble("sub_total"));
        data.setGstAmt(rs.getDouble("gst_amt"));
        data.setGrossTotal(rs.getDouble("gross_total"));
        data.setSubsidyAmt(rs.getDouble("subsidy_amt"));
        data.setBatteryType(rs.getString("battery_type"));
        data.setTenure(rs.getInt("tenure"));
        data.setExchange(rs.getString("exchange"));

//        data.setVpcColourName(rs.getString("vpc_color_name"));
//        data.setVariantPartName(rs.getString("variant_part_name"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        List<ModelAccessoriesDetailsDTO> accessoriesData = new ArrayList<>();
        if (!rs.wasNull()) {
            data.setModelAccessories(accessoriesData);

        } else {
            data.setModelAccessories(accessoriesData);

        }
        
        List<VariantPartsColourDetailsDTO> partsrData = new ArrayList<>();
        if (!rs.wasNull()) {
            data.setPartsColourDetails(partsrData);

        } else {
            data.setPartsColourDetails(partsrData);

        }

        return data;
    }

}
