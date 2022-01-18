/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.rowmapper;

import com.boommotors.btp.dealer.dto.DealerKeyPersonnelDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class DealerKeyPersonnelRowMapper implements RowMapper<DealerKeyPersonnelDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public DealerKeyPersonnelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DealerKeyPersonnelDTO data = new DealerKeyPersonnelDTO();

        data.setPersonnelId(ed.encrypt(String.valueOf(rs.getLong("personnel_id"))));
        data.setDealerId(ed.encrypt(String.valueOf(rs.getLong("dealer_id"))));
        data.setDesignation(rs.getString("designation"));
        data.setQualification(rs.getString("qualification"));
        data.setPersonnelName(rs.getString("personnel_name"));
        data.setCurrentlyWorking(rs.getString("currently_working"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}
