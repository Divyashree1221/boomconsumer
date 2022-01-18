/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.MytemplateLikesDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author NandiniC
 */
public class MytemplateLikesRowmapper implements RowMapper<MytemplateLikesDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public MytemplateLikesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MytemplateLikesDTO data = new MytemplateLikesDTO();

        data.setMyTemplateLlikesId(ed.encrypt(String.valueOf(rs.getLong("my_template_likes_id"))));
        data.setContestTemplatesId(ed.encrypt(String.valueOf(rs.getLong("contest_templates_id"))));
        data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));

        return data;
    }

}
