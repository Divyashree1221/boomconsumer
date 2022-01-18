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
import java.util.List;

/**
 *
 * @author Ramya
 */
public interface ProductDAO {
    
    List<ModelDTO> fetchModels();
    
    List<VariantsDTO> fetchVariants(Integer modelId);
    
    List<ColourDTO> fetchColours(Integer variantId);
    
    VariantsDTO fetchVariantDetails(Integer variantId);
    
    List<MainAddOnsDTO> fetchAddOnsByModelId(Integer modelId);
    
    List<MainAddOnsDTO> fetchAllAddOns();
    
    List<VariantsDTO> fetchAllVariants();
    
    List<VariantsDTO> fetchAllVariant(Integer modelId);
    
    List<TestTableDTO> fetchTestTable();
    
    VariantNewDTO fetchProductDetailsByVariantId(Integer variantId);
    
    List<VariantCompatibilityDTO> fetchCompatibilityData(Integer variantId);
    
    List<AttributeDTO> fetchAttributesData(Integer variantId);
    
    AttributesValuesDTO fetchAttributeValuesData(Integer attributeId);
    
    BrandDTO fetchBrandData(Integer brandId);
    
    List<VariantBadgesDTO> fetchVariantBadgesData(Integer variantId);
    
    List<VariantCompatibilityDTO> fetchCompatibilityBadgesData(Integer variantId);
    
    List<VariantTagDTO> fetchTagData(Integer variantId);
    
    List<VariantImageDTO> fetchImageData(Integer variantId);
    
    List<ModelPackageDTO> fetchModelPackageDetails(Integer modelId);
    
    List<ModelAccessoriesDTO> fetchModelAccessoies(Integer dModelId);
    
    List<ColorsForEachpartDTO> fetchColoursForEachPart(Integer variantId);
    
    List<BatteryChargingetailsDTO> fetchBatteryChargingDetails(Integer variantId);
    
    List<VariantStatePriceDTO>fetchVariantStatePrice(Integer variantId, String stateName);

    TypeDTO fetchTypeData(Integer variantId);
    
    List<TypeAttributeGroupsDTO> fetchAttributeGroupsData(Integer typeId);
    
    List<TypeAttGrpAttributeGroupsDTO> fetchTypeAttGrpAttributeGroupsData(Integer typeAttributegroupsId);
    
    List<OptionsDTO> fetchOptionsData(Integer variantId);
    
    List<OptionsValuesDTO> fetchOptionsValuesData(Integer optionsId);
    
    List<CategoriesDTO> fetchCategoriesData(Integer variantId);
    
    CategoriesCustomfieldsDTO fetchCategoriesCustomfieldsData(Integer categoriesId);
    
    VariantCustomFieldsDTO fetchVariantCustomFieldsData(Integer variantId);
    
    ModelDTO fetchModelById(Integer modelId);
    
    ColourDTO fetchImageUrl(Integer variantId, String colourName);
    
}
