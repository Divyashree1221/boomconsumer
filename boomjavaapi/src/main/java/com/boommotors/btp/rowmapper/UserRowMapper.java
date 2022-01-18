/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.rowmapper;

import com.boommotors.btp.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("firstname"));
        user.setLastName(rs.getString("lastname"));
        user.setEmail(rs.getString("email_id"));
        user.setMobileNumber(rs.getString("mobile_number"));
        user.setProvider(rs.getString("provider"));
        user.setSocialId(rs.getString("social_id"));
        user.setUserRoleId(rs.getInt("user_role_id"));
        user.setPassword(rs.getString("password"));
        return user;

    }
}
