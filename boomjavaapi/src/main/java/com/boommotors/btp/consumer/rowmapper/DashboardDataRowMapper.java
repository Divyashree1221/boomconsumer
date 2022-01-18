/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.rowmapper;

import com.boommotors.btp.consumer.dto.ConsumerDetailsDTO;
import com.boommotors.btp.consumer.dto.DashboardDataDTO;
import com.boommotors.btp.util.DatabaseUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class DashboardDataRowMapper implements RowMapper<DashboardDataDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();
    DatabaseUtil du = new DatabaseUtil();

    @Override
    public DashboardDataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DashboardDataDTO data = new DashboardDataDTO();

        data.setIm(rs.getLong("im"));
        data.setPs(rs.getString("ps"));
        data.setGfx(rs.getInt("g_fx"));
        data.setDt(rs.getInt("dt"));
        data.setTm(rs.getInt("tm"));
        data.setLt(rs.getDouble("lt"));
        data.setLtd(rs.getString("lt_d"));
        data.setLn(rs.getDouble("ln"));
        data.setLnd(rs.getString("ln_d"));
        data.setGkm(rs.getDouble("gkm"));
        data.setHd(rs.getDouble("hd"));
        data.setNsat(rs.getInt("nsat"));
        data.setAlt(rs.getDouble("alt"));
        data.setNws(rs.getInt("nws"));
        data.setLac(rs.getInt("lac"));
        data.setCid(rs.getString("cid"));
        data.setD1(rs.getDouble("d1"));
        data.setD2(rs.getDouble("d2"));
        data.setD3(rs.getDouble("d3"));
        data.setR1(rs.getInt("r1"));
        data.setR2(rs.getInt("r2"));
        data.setA1(rs.getInt("a1"));
        data.setMuv(rs.getDouble("mu_v"));
        data.setMut(rs.getDouble("mu_t"));
        data.setVkm(rs.getInt("v_km"));
        data.setMa(rs.getInt("m_a"));
        data.setCv(rs.getInt("c_v"));
        data.setTs(rs.getInt("t_s"));
        data.setCt(rs.getInt("c_t"));
        data.setMt(rs.getInt("m_t"));
        data.setSoc(rs.getInt("soc"));
        data.setBa(rs.getInt("b_a"));
        data.setBv(rs.getInt("b_v"));
        data.setBt(rs.getInt("b_t"));
        data.setX(rs.getDouble("x"));
        data.setY(rs.getDouble("y"));
        data.setZ(rs.getDouble("z"));
        data.setRi(rs.getInt("ri"));
        data.setFsn(rs.getInt("fsn"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setOdo(rs.getInt("odo"));
        
        return data;
    }

}
