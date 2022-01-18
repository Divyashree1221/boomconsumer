/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.rowmapper;

import com.boommotors.btp.user.dto.RoleAccessDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class RoleAccessRowMapper implements RowMapper<RoleAccessDTO> {

    @Override
    public RoleAccessDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        RoleAccessDTO data = new RoleAccessDTO();
        data.setId(rs.getInt("id"));
        data.setRoleId(rs.getInt("role_id"));
        data.setSource(rs.getInt("source"));

        return data;
    }

}
