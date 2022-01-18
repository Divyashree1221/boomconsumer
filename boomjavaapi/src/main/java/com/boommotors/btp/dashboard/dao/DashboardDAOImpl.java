/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dashboard.dao;

import com.boommotors.btp.dashboard.dto.CountDTO;
import com.boommotors.btp.dashboard.rowmapper.CountsRowMapper;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ramya
 */
@Repository
public class DashboardDAOImpl extends JdbcDaoSupport implements DashboardDAO {

    private static final Logger logger = LoggerFactory.getLogger(DashboardDAOImpl.class);

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public CountDTO fetchUserCount() {

        StringBuilder sbQry = new StringBuilder();
        sbQry.append("SELECT  ");
        sbQry.append("COUNT(*) ");
        sbQry.append("FROM \"booma\".user ");
        return getJdbcTemplate().queryForObject(sbQry.toString(), new CountsRowMapper());

    }

    @Override
    public CountDTO fetchVerifiedUserCount() {

        StringBuilder sbQry = new StringBuilder();
        sbQry.append("SELECT  ");
        sbQry.append("COUNT(*) ");
        sbQry.append("FROM \"booma\".user ");
        sbQry.append("WHERE mobile_verified = true ");
        return getJdbcTemplate().queryForObject(sbQry.toString(), new CountsRowMapper());

    }

    @Override
    public CountDTO fetchDealershipEnquiryCount() {

        StringBuilder sbQry = new StringBuilder();
        sbQry.append("SELECT  ");
        sbQry.append("COUNT(*) ");
        sbQry.append("FROM \"booma\".dealership_enquiry ");
        return getJdbcTemplate().queryForObject(sbQry.toString(), new CountsRowMapper());

    }

    @Override
    public CountDTO fetchBookingsCount() {
        String status = "captured";
        StringBuilder sbQry = new StringBuilder();
        sbQry.append("SELECT  ");
        sbQry.append("COUNT(*) ");
        sbQry.append("FROM \"booma\".order_transactions ");
        sbQry.append("WHERE status = ? ");
        return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{status}, new CountsRowMapper());

    }

    @Override
    public CountDTO fetchBulkEnquiryCount() {
        StringBuilder sbQry = new StringBuilder();
        sbQry.append("SELECT  ");
        sbQry.append("COUNT(*) ");
        sbQry.append("FROM \"booma\".bulk_enquiry ");
        return getJdbcTemplate().queryForObject(sbQry.toString(), new CountsRowMapper());

    }

    @Override
    public CountDTO fetchQtyBulkEnquiryCount() {
        StringBuilder sbQry = new StringBuilder();
        sbQry.append("SELECT SUM (quantity) AS count ");
        sbQry.append("FROM \"booma\".bulk_enquiry ");
        return getJdbcTemplate().queryForObject(sbQry.toString(), new CountsRowMapper());

    }

    @Override
    public CountDTO fetchOrderCancelledCount() {
        String status = "true";
        StringBuilder sbQry = new StringBuilder();
        sbQry.append("SELECT COUNT(*) ");
        sbQry.append("FROM \"booma\".order_transactions ");
        sbQry.append("WHERE refund_status = ? ");
        return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{status}, new CountsRowMapper());
    }

}
