/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.util;

import com.boommotors.btp.product.rowmapper.*;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.user.rowmapper.*;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class DateRowMapper implements RowMapper<DateDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public DateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DateDTO data = new DateDTO();
        data.setTimezone(rs.getTimestamp("timezone"));
                
        return data;
}

    
}
