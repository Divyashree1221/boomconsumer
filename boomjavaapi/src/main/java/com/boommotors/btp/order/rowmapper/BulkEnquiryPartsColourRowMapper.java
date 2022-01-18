/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.BulkEnquiryPartsColourDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class BulkEnquiryPartsColourRowMapper implements RowMapper<BulkEnquiryPartsColourDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public BulkEnquiryPartsColourDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        BulkEnquiryPartsColourDTO data = new BulkEnquiryPartsColourDTO();

        data.setBulkEnquiryPartsColourId(ed.encrypt(String.valueOf(rs.getLong("bulk_enquiry_parts_colour_id"))));
        data.setBulkEnquiryId(ed.encrypt(String.valueOf(rs.getLong("bulk_enquiry_id"))));
        data.setVariantPartsColourId(ed.encrypt(String.valueOf(rs.getLong("variant_parts_colour_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }
}
