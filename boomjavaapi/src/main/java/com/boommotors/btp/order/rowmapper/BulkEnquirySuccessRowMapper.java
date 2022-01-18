/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.BulkEnquirySuccessDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author NandiniC
 */
public class BulkEnquirySuccessRowMapper implements RowMapper<BulkEnquirySuccessDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public BulkEnquirySuccessDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        BulkEnquirySuccessDTO data = new BulkEnquirySuccessDTO();

        data.setFirstname(rs.getString("firstname"));
        data.setLastname(rs.getString("lastname"));
        data.setQuantity(rs.getInt("quantity"));
        data.setVariantName(rs.getString("variant_name"));
        data.setModelName(rs.getString("model_name"));
        return data;
    }

}
