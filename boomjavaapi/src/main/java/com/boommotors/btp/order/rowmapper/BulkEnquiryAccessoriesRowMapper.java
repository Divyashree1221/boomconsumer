/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.BulkEnquiryAccessoriesDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class BulkEnquiryAccessoriesRowMapper implements RowMapper<BulkEnquiryAccessoriesDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public BulkEnquiryAccessoriesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BulkEnquiryAccessoriesDTO data = new BulkEnquiryAccessoriesDTO();
        data.setBulkEnquiryAccessoriesId(ed.encrypt(String.valueOf(rs.getLong("bulk_enquiry_accessories_id"))));
        data.setBulkEnquiryId(rs.getString("bulk_enquiry_id"));
        data.setModelAccessoriesId(rs.getString("model_accessories_id"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}
