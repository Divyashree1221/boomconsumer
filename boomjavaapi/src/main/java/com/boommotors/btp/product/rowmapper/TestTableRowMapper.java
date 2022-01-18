/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.TestTableDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class TestTableRowMapper implements RowMapper<TestTableDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public TestTableDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TestTableDTO data = new TestTableDTO();
        data.setId(ed.encrypt(String.valueOf(rs.getLong("id"))));
        data.setTestField(rs.getString("testfield"));
        
        data.setTest2(rs.getString("test2"));
        
       
        
        return data;
}

    
}
