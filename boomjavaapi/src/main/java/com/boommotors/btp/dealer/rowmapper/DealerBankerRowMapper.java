/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.rowmapper;

import com.boommotors.btp.dealer.dto.DealerBankerDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class DealerBankerRowMapper implements RowMapper<DealerBankerDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public DealerBankerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DealerBankerDTO data = new DealerBankerDTO();

        data.setDealerBankerId(ed.encrypt(String.valueOf(rs.getLong("dealer_banker_id"))));
        data.setDealerId(ed.encrypt(String.valueOf(rs.getLong("dealer_id"))));
        data.setNameofbank(rs.getString("nameofbank"));
        data.setBranch(rs.getString("branch"));
        data.setDurationOfRelationship(rs.getString("duration_of_relationship"));
        data.setTypeoffacility(rs.getString("typeoffacility"));
        data.setCurrentLimit(rs.getString("current_limit"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}