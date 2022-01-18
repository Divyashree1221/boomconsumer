/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.dao;

import com.boommotors.btp.product.dto.AttributeDTO;
import com.boommotors.btp.product.dto.AttributesValuesDTO;
import com.boommotors.btp.product.dto.BatteryChargingetailsDTO;
import com.boommotors.btp.product.dto.BrandDTO;
import com.boommotors.btp.product.dto.CategoriesCustomfieldsDTO;
import com.boommotors.btp.product.dto.CategoriesDTO;
import com.boommotors.btp.product.dto.ColorsForEachpartDTO;
import com.boommotors.btp.product.dto.ColourDTO;
import com.boommotors.btp.product.dto.MainAddOnsDTO;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.ModelPackageDTO;
import com.boommotors.btp.product.dto.ModelAccessoriesDTO;
import com.boommotors.btp.product.dto.OptionsDTO;
import com.boommotors.btp.product.dto.OptionsValuesDTO;
import com.boommotors.btp.product.dto.TestTableDTO;
import com.boommotors.btp.product.dto.TypeAttGrpAttributeGroupsDTO;
import com.boommotors.btp.product.dto.TypeAttributeGroupsDTO;
import com.boommotors.btp.product.dto.TypeDTO;
import com.boommotors.btp.product.dto.VariantBadgesDTO;
import com.boommotors.btp.product.dto.VariantCompatibilityDTO;
import com.boommotors.btp.product.dto.VariantCustomFieldsDTO;
import com.boommotors.btp.product.dto.VariantImageDTO;
import com.boommotors.btp.product.dto.VariantNewDTO;
import com.boommotors.btp.product.dto.VariantStatePriceDTO;
import com.boommotors.btp.product.dto.VariantTagDTO;
import com.boommotors.btp.product.dto.VariantsDTO;
import com.boommotors.btp.product.rowmapper.AttributeRowmapper;
import com.boommotors.btp.product.rowmapper.AttributesValuesRowMapper;
import com.boommotors.btp.product.rowmapper.BatteryChargingDetailsRowMapper;
import com.boommotors.btp.product.rowmapper.BrandRowMapper;
import com.boommotors.btp.product.rowmapper.CategoriesCustomfieldsRowMapper;
import com.boommotors.btp.product.rowmapper.CateoriesRowmapper;
import com.boommotors.btp.product.rowmapper.ColourForEachPartRowMapper;
import com.boommotors.btp.product.rowmapper.ColourRowMapper;
import com.boommotors.btp.product.rowmapper.MainAddOnsRowMapper;
import com.boommotors.btp.product.rowmapper.ModelPackageRowMapper;
import com.boommotors.btp.product.rowmapper.ModelRowMapper;
import com.boommotors.btp.product.rowmapper.ModelAccessoriesRowMapper;
import com.boommotors.btp.product.rowmapper.OptionsRowmapper;
import com.boommotors.btp.product.rowmapper.OptionsValuesRowMapper;
import com.boommotors.btp.product.rowmapper.TestTableRowMapper;
import com.boommotors.btp.product.rowmapper.TypeAttGrpAttributeGroupsRowmapper;
import com.boommotors.btp.product.rowmapper.TypeAttributeGroupsRowmapper;
import com.boommotors.btp.product.rowmapper.TypeRowmapper;
import com.boommotors.btp.product.rowmapper.VariantBadgesRowmapper;
import com.boommotors.btp.product.rowmapper.VariantCompatibilityRowMapper;
import com.boommotors.btp.product.rowmapper.VariantCustomFieldsRowmapper;
import com.boommotors.btp.product.rowmapper.VariantImageRowmapper;
import com.boommotors.btp.product.rowmapper.VariantNewRowMapper;
import com.boommotors.btp.product.rowmapper.VariantStatePriceRowMapper;
import com.boommotors.btp.product.rowmapper.VariantTagRowMapper;
import com.boommotors.btp.product.rowmapper.VariantsRowMapper;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ramya
 */
@Repository
public class ProductDAOImpl extends JdbcDaoSupport implements ProductDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);

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
    public List<ModelDTO> fetchModels() {

        try {
            List<ModelDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".model ");
            sbQry.append("ORDER BY model_id ASC ");
            result = getJdbcTemplate().query(sbQry.toString(), new ModelRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantsDTO> fetchVariants(Integer modelId) {

        try {
            List<VariantsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".variant ");
            sbQry.append("WHERE model_id = ? ");
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{modelId}, new VariantsRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ColourDTO> fetchColours(Integer variantId) {

        try {
            List<ColourDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".colour ");
            sbQry.append("WHERE variant_id = ? ");
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new ColourRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public VariantsDTO fetchVariantDetails(Integer variantId) {

        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".variant ");
            sbQry.append("WHERE variant_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{variantId}, new VariantsRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<MainAddOnsDTO> fetchAddOnsByModelId(Integer modelId) {

        try {
            List<MainAddOnsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".main_add_ons ");
            sbQry.append("WHERE model_id = ? ");
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{modelId}, new MainAddOnsRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<MainAddOnsDTO> fetchAllAddOns() {

        try {
            List<MainAddOnsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".main_add_ons ");
            result = getJdbcTemplate().query(sbQry.toString(), new MainAddOnsRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantsDTO> fetchAllVariants() {

        try {
            List<VariantsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".variant ");
            result = getJdbcTemplate().query(sbQry.toString(), new VariantsRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantsDTO> fetchAllVariant(Integer modelId) {

        try {
            List<VariantsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            
            sbQry.append("select V.variant_id, V.variant_name, V.model_id, M.model_name, V.motor_nominal_power, ");
            sbQry.append("V.motor_peak_power, V.top_speed, V.gradeability, V.motor_type, V.wdr_motor, V.wdr_controller,");
            sbQry.append("V.tr_eco_mode, V.tr_power_mode, V.battery_type, V.capacity, V.nominal_voltage, ");
            sbQry.append("V.water_dust_resistance, V.regular_charging_time, V.avg_swapping_time, ");
            sbQry.append("V.wheel_type, V.wheel_size, V.front_tyre_size, V.rear_tyre_size, V.braking_system, ");
            sbQry.append("V.brake_type, V.front_suspension, V.rear_suspension, V.head_tail_light_indicators, ");
            sbQry.append("V.usb_charger, V.cell_phone_holder, V.iot, V.wheel_base, V.ground_clearance, ");
            sbQry.append("V.width, V.kerb_weight, V.underseat_storage, V.price, V.created_by, V.created_date, ");
            sbQry.append("V.updated_by, V.updated_date, V.image, V.reg_braking, V.iot_key_feature, ");
            sbQry.append("V.speedometer, V.loading_capacity, V.warranty, V.base_image, ");
            sbQry.append("V.battery_image, V.caption, V.image2, V.image3, V.image4  ");           
            sbQry.append("FROM \"booma\".variant V ");
            sbQry.append("JOIN \"booma\".model M ON V.model_id = M.model_id ");
            sbQry.append("WHERE V.model_id = ?");
            sbQry.append("ORDER BY V.variant_id ASC ");
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{modelId}, new VariantsRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<TestTableDTO> fetchTestTable() {
        try {
            List<TestTableDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".testtable ");
            result = getJdbcTemplate().query(sbQry.toString(), new TestTableRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public VariantNewDTO fetchProductDetailsByVariantId(Integer variantId) {

        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".variant_new ");
            sbQry.append("WHERE variant_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{variantId}, new VariantNewRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantCompatibilityDTO> fetchCompatibilityData(Integer variantId) {
        try {
            List<VariantCompatibilityDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".variant_compatibility WHERE variant_id = ? ");
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new VariantCompatibilityRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<AttributeDTO> fetchAttributesData(Integer variantId) {

        try {
            List<AttributeDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT attributes_id, name, slug, featured ");
            sbQry.append("FROM \"booma\".attributes WHERE variant_id = ? ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new AttributeRowmapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public AttributesValuesDTO fetchAttributeValuesData(Integer attributeId) {

        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT attributes_values_id, name, slug, attributes_id ");
            sbQry.append("FROM \"booma\".attributes_values WHERE attributes_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{attributeId}, new AttributesValuesRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public BrandDTO fetchBrandData(Integer brandId) {

        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT brand_id, slug, name, image, country ");
            sbQry.append("FROM \"booma\".brand WHERE brand_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{brandId}, new BrandRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantBadgesDTO> fetchVariantBadgesData(Integer variantId) {

        try {
            List<VariantBadgesDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT badges ");
            sbQry.append("FROM \"booma\".variant_badges WHERE variant_id = ? ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new VariantBadgesRowmapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantCompatibilityDTO> fetchCompatibilityBadgesData(Integer variantId) {

        try {
            List<VariantCompatibilityDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT compatibility ");
            sbQry.append("FROM \"booma\".variant_compatibility WHERE variant_id = ? ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new VariantCompatibilityRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantTagDTO> fetchTagData(Integer variantId) {

        try {
            List<VariantTagDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT tags ");
            sbQry.append("FROM \"booma\".variant_tags WHERE variant_id = ? ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new VariantTagRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantImageDTO> fetchImageData(Integer variantId) {

        try {
            List<VariantImageDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT images ");
            sbQry.append("FROM \"booma\".variant_images WHERE variant_id = ? ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new VariantImageRowmapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ModelPackageDTO> fetchModelPackageDetails(Integer modelId) {

        try {
            List<ModelPackageDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT MA.model_accessories_id, MA.model_accessories_details, MA.price, ");
            sbQry.append("PA.package_accessories_id, PA.package_name ");
            sbQry.append("FROM \"booma\".model_accessories MA ");
            sbQry.append("JOIN booma.package_accessories PA ON ");
            sbQry.append("PA.model_accessories_id = MA.model_accessories_id  where MA.model_id = ? ");
            sbQry.append("ORDER BY PA.package_name DESC, MA.model_accessories_id ");
              
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{modelId}, new ModelPackageRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ModelAccessoriesDTO> fetchModelAccessoies(Integer modelId) {

        try {
            List<ModelAccessoriesDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT * FROM \"booma\".model_accessories ");
            sbQry.append("WHERE model_id = ? ");
            sbQry.append("ORDER BY model_accessories_id");
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{modelId}, new ModelAccessoriesRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ColorsForEachpartDTO> fetchColoursForEachPart(Integer variantId) {

        try {
            List<ColorsForEachpartDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT vpc.*, v.base_image ");
            sbQry.append("FROM booma.variant_parts_colour vpc ");
            sbQry.append("LEFT JOIN booma.variant v ON v.variant_id = vpc.variant_id ");
            sbQry.append("WHERE vpc.variant_id = ?");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new ColourForEachPartRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BatteryChargingetailsDTO> fetchBatteryChargingDetails(Integer variantId) {

        try {
            List<BatteryChargingetailsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT ");
            sbQry.append("variant_id, battery_type, capacity, nominal_voltage, water_dust_resistance, ");
            sbQry.append("regular_charging_time, avg_swapping_time, battery_image ");
            sbQry.append("FROM booma.variant WHERE variant_id = ?");
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new BatteryChargingDetailsRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
     
    @Override
    public TypeDTO fetchTypeData(Integer variantId) {
        
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT type_id, slug, name ");
            sbQry.append("FROM \"booma\".type WHERE variant_id = ? ");
            
            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{variantId}, new TypeRowmapper());


        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<TypeAttributeGroupsDTO> fetchAttributeGroupsData(Integer typeId) {
        
        try {
            List<TypeAttributeGroupsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT type_attributegroups_id, name, slug ");
            sbQry.append("FROM \"booma\".type_attributegroups WHERE type_id = ? ");
            
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{typeId}, new TypeAttributeGroupsRowmapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<TypeAttGrpAttributeGroupsDTO> fetchTypeAttGrpAttributeGroupsData(Integer typeAttributegroupsId) {
        
        try {
            List<TypeAttGrpAttributeGroupsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT type_attgrp_attributes_id, attributes ");
            sbQry.append("FROM \"booma\".type_attgrp_attributes WHERE type_attributegroups_id = ? ");
            
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{typeAttributegroupsId}, new TypeAttGrpAttributeGroupsRowmapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VariantStatePriceDTO> fetchVariantStatePrice(Integer variantId, String stateName) {
        try {
            List<VariantStatePriceDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT * ");
            sbQry.append("FROM booma.variant_state_price WHERE variant_id = ? AND LOWER(state_name) = LOWER(?) ");
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId, stateName}, new VariantStatePriceRowMapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }
    
    @Override
    public List<OptionsDTO> fetchOptionsData(Integer variantId) {

        try {
            List<OptionsDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT options_id, type, slug, name ");
            sbQry.append("FROM \"booma\".options WHERE variant_id = ? ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new OptionsRowmapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<OptionsValuesDTO> fetchOptionsValuesData(Integer optionsId) {

        try {
            List<OptionsValuesDTO> result = new ArrayList<>();
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT slug, name, colour, options_values_id FROM booma.options_values ");
            sbQry.append("WHERE options_id = ?");

            result =  getJdbcTemplate().query(sbQry.toString(), new Object[]{optionsId}, new OptionsValuesRowMapper());
            
            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<CategoriesDTO> fetchCategoriesData(Integer variantId) {

        try {
            List<CategoriesDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT categories_id, type, name, slug, image, items, parent, layout ");
            sbQry.append("FROM \"booma\".categories WHERE variant_id = ? ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{variantId}, new CateoriesRowmapper());

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public CategoriesCustomfieldsDTO fetchCategoriesCustomfieldsData(Integer categoriesId) {

        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * FROM booma.categories_customfields ");
            sbQry.append("WHERE categories_id = ?");

            return  getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{categoriesId}, new CategoriesCustomfieldsRowMapper());
            

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public VariantCustomFieldsDTO fetchVariantCustomFieldsData(Integer variantId) {

        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT variant_customfields_id, customfields ");
            sbQry.append("FROM \"booma\".variant_customfields WHERE variant_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{variantId}, new VariantCustomFieldsRowmapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public ModelDTO fetchModelById(Integer modelId) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".model ");
            sbQry.append("WHERE model_id = ?");
            
            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{modelId}, new ModelRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public ColourDTO fetchImageUrl(Integer variantId, String colourName) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".colour ");
            sbQry.append("WHERE variant_id = ? AND colour_name = ?");
            
            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{variantId, colourName}, new ColourRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
