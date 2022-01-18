/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.rowmapper;

import com.boommotors.btp.dealer.dto.DealerReferencesDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class DealerReferencesRowMapper implements RowMapper<DealerReferencesDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public DealerReferencesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DealerReferencesDTO data = new DealerReferencesDTO();

        data.setDealerReferencesId(ed.encrypt(String.valueOf(rs.getLong("dealer_references_id"))));
        data.setDealerId(ed.encrypt(String.valueOf(rs.getLong("dealer_id"))));
        data.setNameaddress(rs.getString("nameaddress"));
        data.setOccupation(rs.getString("occupation"));
        data.setYearsKown(rs.getString("years_known"));
        data.setContactPerson(rs.getString("contact_person"));
        data.setPhoneNumber(rs.getString("phone_number"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}
