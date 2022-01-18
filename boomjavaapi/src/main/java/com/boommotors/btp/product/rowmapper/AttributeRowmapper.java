/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.AttributeDTO;
import com.boommotors.btp.product.dto.AttributesValuesDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class AttributeRowmapper implements RowMapper<AttributeDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public AttributeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AttributeDTO data = new AttributeDTO();
        data.setAttributesId(ed.encrypt(String .valueOf(rs.getLong("attributes_id"))));
        data.setName(rs.getString("name"));
        data.setSlug(rs.getString("slug"));
        data.setFeatured(rs.getBoolean("featured"));
        
        AttributesValuesDTO attributesValuesData = new AttributesValuesDTO();
//        String attribuesValueName = rs.getString("attribues_value_name");
//        String attribuesValueSlug = rs.getString("attribues_value_slug");
//        if (!rs.wasNull()) {
//            attributesValuesData.setName(attribuesValueName);
//            attributesValuesData.setSlug(attribuesValueSlug);
//        } else {
            attributesValuesData.setName("");
            attributesValuesData.setSlug("");
        //}

        data.setValues(attributesValuesData);
        
//        data.setVariantId(rs.getInt("variant_id"));
//        data.setTypeAttributeGroupsId(rs.getString("type_attributeGroups_id"));
//        data.setValuesName(rs.getString("values_name"));
//        data.setValuesSlug(rs.getString("values_slug"));
//        data.setCreatedBy(rs.getInt("created_by"));
//        data.setCreatedDate(rs.getTimestamp("created_date"));
//        data.setUpdatedBy(rs.getInt("updated_by"));
//        data.setUpdatedDate(rs.getTimestamp("updated_date"));
                return data;
                
    }

}
