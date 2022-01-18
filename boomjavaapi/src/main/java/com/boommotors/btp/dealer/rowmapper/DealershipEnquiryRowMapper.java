/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.rowmapper;

import com.boommotors.btp.dealer.dto.DealershipEnquiryDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class DealershipEnquiryRowMapper implements RowMapper<DealershipEnquiryDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public DealershipEnquiryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DealershipEnquiryDTO data = new DealershipEnquiryDTO();

        data.setId(ed.encrypt(String.valueOf(rs.getLong("id"))));
        data.setName(rs.getString("name"));
        data.setEmail(rs.getString("email"));
        data.setContactNo(rs.getString("contact_no"));
        data.setIsRegistered(rs.getBoolean("is_registered"));
        data.setCompanyName(rs.getString("company_name"));
        data.setCompanyAddress(rs.getString("company_address"));
        data.setDesignation(ed.encrypt(String.valueOf(rs.getLong("designation"))));
        data.setPreferredState1(ed.encrypt(String.valueOf(rs.getLong("preferred_state1"))));
        data.setPreferredState2(rs.getString("preferred_state2"));
        data.setDistrictCity(rs.getString("district_city"));
        data.setIsOwnedLeased(rs.getBoolean("is_owned_leased"));
        data.setAmount(rs.getString("amount"));
        data.setIsExperienced(rs.getBoolean("is_experienced"));
        data.setHeardFromWhere(rs.getString("heard_from_where"));
        data.setComments(rs.getString("comments"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}
