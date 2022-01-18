/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dashboard.rowmapper;

import com.boommotors.btp.dashboard.dto.CountDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya Softcons
 */
public class CountsRowMapper implements RowMapper<CountDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public CountDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountDTO data = new CountDTO();
        data.setCount(rs.getInt("count"));
      
                
        return data;
}
}
