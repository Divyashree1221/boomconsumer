/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.rowmapper;

import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.ModelPackageDTO;
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
public class ModelPackageRowMapper implements RowMapper<ModelPackageDTO> {
    
    EncryptDecrypt ed = new EncryptDecrypt();

    @Override
    public ModelPackageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ModelPackageDTO data = new ModelPackageDTO();
        data.setModelAccessoriesId(ed.encrypt(String.valueOf(rs.getLong("model_accessories_id"))));
        data.setModelAccessoriesDetails(rs.getString("model_accessories_details"));
        data.setPrice(rs.getDouble("price"));
        data.setPackageAccessoriesId(ed.encrypt(String.valueOf(rs.getLong("package_accessories_id"))));
        data.setPackageName(rs.getString("package_name"));
        
        return data;
}

    
}
