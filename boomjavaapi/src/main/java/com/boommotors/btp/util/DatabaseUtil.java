/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rjanumpally
 */
@Component
public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);
    public void forcelyCloseConnection(JdbcTemplate jdbcTemplate) {

        try {
            jdbcTemplate.getDataSource().getConnection().close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isThere(ResultSet rs, String column) {
        try {
            rs.findColumn(column);
            return true;
        } catch (SQLException sqlex) {
            logger.debug("column doesn't exist {}", column);
        }
        return false;
    }
    
    
    
}
