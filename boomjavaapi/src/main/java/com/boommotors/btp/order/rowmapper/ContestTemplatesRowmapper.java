/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.rowmapper;

import com.boommotors.btp.order.dto.ContestTemplatesDTO;
import com.boommotors.btp.util.DatabaseUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author NandiniC
 */
public class ContestTemplatesRowmapper implements RowMapper<ContestTemplatesDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();
     DatabaseUtil du = new DatabaseUtil();

    @Override
    public ContestTemplatesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        ContestTemplatesDTO data = new ContestTemplatesDTO();
        
        data.setContestTemplatesId(ed.encrypt(String.valueOf(rs.getLong("contest_templates_id"))));
        data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
        data.setTemplateName(rs.getString("template_name"));
        data.setUserNickname(rs.getString("user_nickname"));
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setBaseImage(rs.getString("base_image"));
        data.setBodyColorId(ed.encrypt(String.valueOf(rs.getLong("body_color_id"))));
        data.setBodyImage(rs.getString("body_image"));
        data.setFrameColorId(ed.encrypt(String.valueOf(rs.getLong("frame_color_id"))));
        data.setFrameImage(rs.getString("frame_image"));
        data.setMaskColorId(ed.encrypt(String.valueOf(rs.getLong("mask_color_id"))));
        data.setMaskImage(rs.getString("mask_image"));
        data.setRimsColorId(ed.encrypt(String.valueOf(rs.getLong("rims_color_id"))));
        data.setRimsImage(rs.getString("rims_image"));
        data.setShockColorId(ed.encrypt(String.valueOf(rs.getLong("shock_color_id"))));
        data.setShockImage(rs.getString("shock_image"));
        data.setLikesCount(rs.getInt("likes_count"));
        data.setTemplateImage(rs.getString("template_image"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
          boolean doesTotalRecordsExists = du.isThere(rs, "total_records");
        if (doesTotalRecordsExists) {
            if (!rs.wasNull()) {
                data.setTotalRecords(rs.getInt("total_records"));
            } else {
                data.setTotalRecords(0);
            }
        }
          boolean doesUserLikeExists = du.isThere(rs, "userLikecliked");
        if (doesUserLikeExists) {
            if (!rs.wasNull()) {
                data.setUserLikecliked(rs.getInt("userLikecliked"));
            } else {
                data.setUserLikecliked(0);
            }
        }
        
        
 
        

        return data;
    }

}
