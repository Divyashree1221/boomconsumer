/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.rowmapper;

import com.boommotors.btp.dealer.dto.HeardFromWhereDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class HeardFromWhereRowMapper implements RowMapper<HeardFromWhereDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public HeardFromWhereDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        HeardFromWhereDTO data = new HeardFromWhereDTO();

        data.setId(ed.encrypt(String.valueOf(rs.getLong("id"))));
        data.setHeardFromWhereName(rs.getString("heard_from_where_name"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }
}
