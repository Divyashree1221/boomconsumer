/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.rowmapper;

import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class UsersRowMapper implements RowMapper<UsersDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public UsersDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsersDTO data = new UsersDTO();
        data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
        data.setFirstName(rs.getString("firstname"));
        data.setLastName(rs.getString("lastname"));
        data.setUserRoleId(rs.getLong("user_role_id"));
        data.setEmailId(rs.getString("email_id"));
        data.setMobileNumber(rs.getString("mobile_number"));
        data.setSocialId(rs.getString("social_id"));
        data.setProvider(rs.getString("provider"));
        data.setLoginOtp(rs.getInt("loginotp"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setPassword(rs.getString("password"));
        data.setEmailVerified(rs.getBoolean("email_verified"));
        data.setMobile_verified( rs.getBoolean("mobile_verified"));
        data.setEmailOtp(rs.getInt("email_otp"));
        data.setMobileOtpIssueTime(rs.getTimestamp("mobile_otp_issue_time"));
        data.setEmailOtpIssueTime(rs.getTimestamp("email_otp_issue_time"));
        data.setAccountLocked(rs.getBoolean("account_locked"));
        data.setFailedAttempt(rs.getInt("failed_attempt"));
        data.setLockTime(rs.getTimestamp("lock_time"));

        return data;
    }

}
