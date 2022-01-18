/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.VariantNewDTO;
import com.boommotors.btp.product.dto.VariantProductDTO;
import com.boommotors.btp.user.rowmapper.*;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ramya
 */
public class VariantProductRowMapper implements RowMapper<VariantProductDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public VariantProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantProductDTO data = new VariantProductDTO();
        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
        data.setVarintName(rs.getString("varint_name"));
        data.setExcerpt(rs.getString("excerpt"));
        data.setDescription(rs.getString("description"));
        data.setVariantSlug(rs.getString("variant_slug"));
        data.setVariantSku(rs.getString("variant_sku"));
        data.setPartNumber(rs.getString("part_number"));
        data.setStock(rs.getString("stock"));
        data.setPrice(rs.getDouble("price"));
        data.setCompareAtPrice(rs.getDouble("compareatprice"));
        data.setRating(rs.getDouble("rating"));
        data.setReviews(rs.getDouble("reviews"));
        data.setAvailability(rs.getString("availability"));
        data.setVariantImages(rs.getString("variant_images"));
        data.setBadges(rs.getString("badges"));
        data.setCompatibility(rs.getInt("compatibility"));
        data.setBrandSlug(rs.getString("brand_slug"));
        data.setBrandName(rs.getString("brand_name"));
        data.setBrandImage(rs.getString("brand_image"));
        data.setCountry(rs.getString("country"));
        data.setTypeSlug(rs.getString("type_slug"));
        data.setTypeName(rs.getString("type_name"));
        data.setTypeAttributegroupsName(rs.getString("type_attributegroups_name"));
        data.setTypeAttributegroupsSlug(rs.getString("type_attributegroups_slug"));
        data.setTypeAttributegroupsAttributes(rs.getString("type_attributegroups_attributes"));
        data.setAttributesName(rs.getString("attributes_name"));
        data.setAttributesSlug(rs.getString("attributes_slug"));
        data.setAttributesFeatured(rs.getString("attributes_featured"));
        data.setAttributesValuesName(rs.getString("attributes_values_name"));
        data.setAttributesSlug(rs.getString("attributes_values_slug"));
        data.setOptionsType(rs.getString("options_type"));
        data.setOptionsSlug(rs.getString("options_slug"));
        data.setOptionsName(rs.getString("options_name"));
        data.setOptionsValuesSlug(rs.getString("options_values_slug"));
        data.setOptionsValuesName(rs.getString("options_values_name"));
        data.setOptionsValuesColour(rs.getString("options_values_colour"));
        data.setTags(rs.getString("tags"));
        data.setCategoriesId(rs.getInt("categories_id"));
        data.setCategoriesType(rs.getString("categories_type"));
        data.setCategoriesName(rs.getString("categories_name"));
        data.setCategoriesSlug(rs.getString("categories_slug"));
        data.setCategoriesImage(rs.getString("categories_image"));
        data.setCategoriesItems(rs.getInt("categories_items"));
        data.setCategoriesParent(rs.getString("categories_parent"));
        data.setCategoriesLayout(rs.getString("categories_layout"));
        data.setCustomfields(rs.getString("customfields"));
        data.setCcCustomfileds(rs.getString("cc_customfileds"));
        data.setVcfCustomfield(rs.getString("vcf_customfield"));
        
        return data;
    }

}
