/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.rowmapper;

import com.boommotors.btp.user.dto.UserRoleDTO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class UserRoleRowMapper implements RowMapper<UserRoleDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public UserRoleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRoleDTO data = new UserRoleDTO();
        data.setUserRoleId(ed.encrypt(String.valueOf(rs.getLong("user_role_id"))));
        data.setUserRole(rs.getString("user_role"));
        data.setRoleType(rs.getString("role_type"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        
        return data;
    }

}
