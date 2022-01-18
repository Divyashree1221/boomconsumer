/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.util;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author divyashree
 */
@Repository
public class DateDAOImpl extends JdbcDaoSupport implements DateDAO {

    private static final Logger logger = LoggerFactory.getLogger(DateDAOImpl.class);

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public DateDTO retrieveISTTimeFromDB() {
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Kolkata'");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new DateRowMapper());

        } catch (DataAccessException e) {
            return null;
        }
    }

}
