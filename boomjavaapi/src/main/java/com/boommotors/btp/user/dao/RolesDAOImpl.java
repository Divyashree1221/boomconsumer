/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.dao;

import com.boommotors.btp.user.dto.RoleAccessDTO;
import com.boommotors.btp.user.rowmapper.RoleAccessRowMapper;
import java.util.ArrayList;
import java.util.List;
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
 * @author Ramya
 */
@Repository
public class RolesDAOImpl extends JdbcDaoSupport implements RolesDAO {

    private static final Logger logger = LoggerFactory.getLogger(RolesDAOImpl.class);

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

   

    @Override
    public List<RoleAccessDTO> findAllRolesBySource(Integer source) {
        List<RoleAccessDTO> resultData = new ArrayList<>();
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT \"id\", role_id, source ");
            sbQry.append("FROM \"booma\".\"role_access\" ");
            sbQry.append("WHERE ");
            sbQry.append("\"source\" = ? ");
            resultData = getJdbcTemplate().query(sbQry.toString(), new Object[]{source}, new RoleAccessRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Responding with unauthorized error. Message - {}", ex.getMessage());
        }
        return resultData;
    }
}
