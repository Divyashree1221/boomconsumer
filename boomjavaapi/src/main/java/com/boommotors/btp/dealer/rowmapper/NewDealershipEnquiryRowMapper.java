/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.rowmapper;

import com.boommotors.btp.dealer.dto.DealershipEnquiryDTO;
import com.boommotors.btp.dealer.dto.NewDealershipEnquiryDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class NewDealershipEnquiryRowMapper implements RowMapper<NewDealershipEnquiryDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public NewDealershipEnquiryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        NewDealershipEnquiryDTO data = new NewDealershipEnquiryDTO();

        data.setId(ed.encrypt(String.valueOf(rs.getLong("id"))));
        data.setIsExperienced(rs.getBoolean("is_experienced"));
        data.setBrand(rs.getString("brand"));
        data.setSinceWhen(rs.getString("since_when"));
        data.setPincode1(rs.getString("pincode1"));
        data.setPincode2(rs.getString("pincode2"));
        data.setPincode3(rs.getString("pincode3"));
        data.setAmount(rs.getDouble("amount"));
        data.setComments(rs.getString("comments"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}