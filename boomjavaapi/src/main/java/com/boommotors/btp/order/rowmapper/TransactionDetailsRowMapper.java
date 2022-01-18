/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.TransactionDetailsDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author srilatha
 */
public class TransactionDetailsRowMapper implements RowMapper<TransactionDetailsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public TransactionDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        TransactionDetailsDTO data = new TransactionDetailsDTO();

        data.setAmount(rs.getDouble("amount"));
        data.setParticulars(rs.getString("particulars"));
        data.setPayments(rs.getDouble("payments"));
        data.setSeq(rs.getLong("seq"));

        return data;
    }
}
