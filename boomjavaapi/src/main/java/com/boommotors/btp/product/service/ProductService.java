/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.service;

import com.boommotors.btp.product.dto.BatteryChargingetailsDTO;
import com.boommotors.btp.product.dto.ColorsForEachpartDTO;
import com.boommotors.btp.product.dto.ColourDTO;
import com.boommotors.btp.product.dto.MainAddOnsDTO;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.ModelPackageDTO;
import com.boommotors.btp.product.dto.ModelAccessoriesDTO;
import com.boommotors.btp.product.dto.ProductDTO;
import com.boommotors.btp.product.dto.VariantNewDTO;
import com.boommotors.btp.product.dto.VariantStatePriceDTO;
import com.boommotors.btp.product.dto.VariantsDTO;
import java.util.List;

/**
 *
 * @author Ramya
 */
public interface ProductService {
    
    List<ModelDTO> retrieveModels();
    
    List<VariantsDTO> retrieveVariants(String encryptedModelId);
    
    List<ColourDTO> retrieveColours(String encryptedVariantId);

    VariantsDTO retrieveVariantDetails(String encryptedVariantId);
    
    List<MainAddOnsDTO> retrieveAddOnsByModelId(String encryptedModelId);
    
    List<MainAddOnsDTO> retrieveAllAddOns();
    
    List<VariantsDTO> retrieveAllVariants();
    
    List<VariantsDTO> retrieveAllVariant(String encryptedModelId);
    
    ProductDTO retrieveProductDetailsByVariantId(String encryptedVariantId);
    
    List<ModelPackageDTO> retrieveModelPackageDetails(String encryptedModelId);
    
    List<ModelAccessoriesDTO> retrieveModelAccessoies(String encryptedModelId);
    
    List<ColorsForEachpartDTO> retrieveColoursForEachPart(String encryptedVariantId);
    
    List<BatteryChargingetailsDTO> retrieveBatteryChargingDetails(String encryptedVariantId);
    
    List<VariantStatePriceDTO> retrieveVariantStatePrice(String encryptedVariantId, String stateName);
}
