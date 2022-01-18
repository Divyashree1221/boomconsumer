/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.service;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.product.dao.ProductDAO;
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
import com.boommotors.btp.product.dto.ProductDTO;
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
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ramya
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    ProductDAO productRepository;

    @Override
    public List<ModelDTO> retrieveModels() {
        try {
            return productRepository.fetchModels();

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<VariantsDTO> retrieveVariants(String encryptedModelId) {

        int modelId = Integer.parseInt(edUtil.decrypt(encryptedModelId));
        try {
            return productRepository.fetchVariants(modelId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<ColourDTO> retrieveColours(String encryptedVariantId) {
        int variantId = Integer.parseInt(edUtil.decrypt(encryptedVariantId));
        try {
            return productRepository.fetchColours(variantId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public VariantsDTO retrieveVariantDetails(String encryptedVariantId) {

        int variantId = Integer.parseInt(edUtil.decrypt(encryptedVariantId));
        try {
            return productRepository.fetchVariantDetails(variantId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<MainAddOnsDTO> retrieveAddOnsByModelId(String encryptedModelId) {

        int modelId = Integer.parseInt(edUtil.decrypt(encryptedModelId));
        try {
            return productRepository.fetchAddOnsByModelId(modelId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<MainAddOnsDTO> retrieveAllAddOns() {
        try {
            return productRepository.fetchAllAddOns();

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<VariantsDTO> retrieveAllVariants() {

        try {
            return productRepository.fetchAllVariants();

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<VariantsDTO> retrieveAllVariant(String encryptedModelId) {
        int modelId = Integer.parseInt(edUtil.decrypt(encryptedModelId));
        try {
            return productRepository.fetchAllVariant(modelId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public ProductDTO retrieveProductDetailsByVariantId(String encryptedVariantId) {

        int variantId = Integer.parseInt(edUtil.decrypt(encryptedVariantId));

        System.out.println("variant ID : " + variantId);
        try {

//            String testID = edUtil.encrypt(String.valueOf(3));
//            System.out.println("testID : " + testID);
//            System.out.println("decrypted ID : " + edUtil.decrypt(testID));
            ProductDTO finalResult = new ProductDTO();
            VariantNewDTO variantResult = productRepository.fetchProductDetailsByVariantId(variantId);
            if (variantResult != null) {
                System.out.println("variantResult : " + variantResult.getVariantId());

                finalResult.setId(variantResult.getVariantId());

                finalResult.setName(variantResult.getName());
                finalResult.setExcerpt(variantResult.getExcerpt());
                finalResult.setDescription(variantResult.getDescription());
                finalResult.setSlug(variantResult.getSlug());
                finalResult.setSku(variantResult.getSku());
                finalResult.setPartNumber(variantResult.getPartNumber());
                finalResult.setStock(variantResult.getStock());
                finalResult.setPrice(variantResult.getPrice());
                finalResult.setCompareAtPrice(variantResult.getCompareAtPrice());
                finalResult.setRating(variantResult.getRating());
                finalResult.setReviews(variantResult.getReviews());
                finalResult.setAvailability(variantResult.getAvailability());


                /*Attribute*/
                List<AttributeDTO> attributeResult = productRepository.fetchAttributesData(variantId);
                for (int i = 0; i < attributeResult.size(); i++) {
                    System.out.println("ID : " + attributeResult.get(i).getAttributesId());

                    int attributeId = Integer.parseInt(edUtil.decrypt(attributeResult.get(i).getAttributesId()));
                    System.out.println("Attr ID : " + attributeId);
                    AttributesValuesDTO attrributeValuesData = productRepository.fetchAttributeValuesData(attributeId);
                    attributeResult.get(i).setValues(attrributeValuesData);
                }
                // List<AttributesValuesDTO> attrributeValuesData = productRepository.fetchAttributeValuesData(attributeResult)
                System.out.println("attributeResult : " + attributeResult.toString());

                finalResult.setAttributes(attributeResult);

                /*Brand*/
                int brandId = Integer.parseInt(edUtil.decrypt(variantResult.getBrandId()));
                BrandDTO brandResult = productRepository.fetchBrandData(brandId);
                finalResult.setBrand(brandResult);

                /*Variant Badges*/
                List<VariantBadgesDTO> variantBadgesData = productRepository.fetchVariantBadgesData(variantId);

                StringBuilder variantBadgesSB = new StringBuilder();
                if (variantBadgesData.size() > 0) {

                    for (int i = 0; i < variantBadgesData.size(); i++) {
                        variantBadgesSB.append(variantBadgesData.get(i).getBadges()).append(",");
                    }

                    System.out.println("sb : " + variantBadgesSB);
                    String[] badgeArray = null;
                    for (int i = 0; i < variantBadgesData.size(); i++) {
                        String badges = variantBadgesSB.toString();
                        badgeArray = badges.split(",");
                        finalResult.setBadges(badgeArray);
                    }
                }

                /*  Compatibility  */
                List<VariantCompatibilityDTO> variantCompatibiltyData = productRepository.fetchCompatibilityBadgesData(variantId);
                StringBuilder variantCompatibilitySB = new StringBuilder();
                if (variantCompatibiltyData.size() > 0) {

                    for (int i = 0; i < variantCompatibiltyData.size(); i++) {
                        variantCompatibilitySB.append(variantCompatibiltyData.get(i).getCompatibility()).append(",");
                    }

                    System.out.println("sb : " + variantCompatibilitySB);
                    String[] compatibilityStringArray = null;

                    for (int i = 0; i < variantCompatibiltyData.size(); i++) {
                        String badges = variantCompatibilitySB.toString();
                        compatibilityStringArray = badges.split(",");
//                        
//                        finalResult.setCompatibility(compatibilityArray);
                    }
                    Integer[] compatibilityIntArray = new Integer[compatibilityStringArray.length];
                    for (int i = 0; i < compatibilityStringArray.length; i++) {
                        compatibilityIntArray[i] = Integer.parseInt(compatibilityStringArray[i]);
                        finalResult.setCompatibility(compatibilityIntArray);
                    }

                }

                /*  tags  */
                List<VariantTagDTO> variantTagData = productRepository.fetchTagData(variantId);
                StringBuilder variantTagSB = new StringBuilder();

                if (variantTagData.size() > 0) {

                    for (int i = 0; i < variantTagData.size(); i++) {
                        variantTagSB.append(variantTagData.get(i).getTags()).append(",");
                    }

                    System.out.println("sb : " + variantTagSB);
                    String[] tagsArray = null;
                    for (int i = 0; i < variantTagData.size(); i++) {
                        String tags = variantTagSB.toString();
                        tagsArray = tags.split(",");
                        finalResult.setTags(tagsArray);
                    }
                }

                /*  images  */
                List<VariantImageDTO> variantImageData = productRepository.fetchImageData(variantId);
                StringBuilder variantImageSB = new StringBuilder();

                if (variantImageData.size() > 0) {

                    for (int i = 0; i < variantImageData.size(); i++) {
                        variantImageSB.append(variantImageData.get(i).getImages()).append(",");
                    }

                    System.out.println("sb : " + variantImageSB);
                    String[] imageArray = null;
                    for (int i = 0; i < variantImageData.size(); i++) {
                        String images = variantImageSB.toString();
                        imageArray = images.split(",");
                        finalResult.setImages(imageArray);
                    }
                }

                
                  /*Type*/
                TypeDTO typeResult = productRepository.fetchTypeData(variantId);
                    System.out.println("ID : " + typeResult.getTypeId());
                    
                    int typeId = Integer.parseInt(edUtil.decrypt(typeResult.getTypeId()));
                    System.out.println("Attr ID : " + typeId);
                    List<TypeAttributeGroupsDTO> attrributeValuesDataResult = productRepository.fetchAttributeGroupsData(typeId);
                    typeResult.setAttributeGroups(attrributeValuesDataResult);
                    
                    for (int j = 0; j < attrributeValuesDataResult.size(); j++) {
                        System.out.println("J : " + j);
                        int typeAttributegroupsId = Integer.parseInt(edUtil.decrypt(attrributeValuesDataResult.get(j).getTypeAttributegroupsId()));
                        List<TypeAttGrpAttributeGroupsDTO> typeAttGrpAttributeGroupsData = productRepository.fetchTypeAttGrpAttributeGroupsData(typeAttributegroupsId);
                        System.out.println("typeAttGrpAttributeGroupsData 0 : " + typeAttGrpAttributeGroupsData.get(0).getAttributes());
                        System.out.println("typeAttGrpAttributeGroupsData 1 : " + typeAttGrpAttributeGroupsData.get(1).getAttributes());
                        StringBuilder typeAttGrpAttributeGroupsSB = new StringBuilder();
                
                        String[] typeAttGrpAttributeGroupsArray = null;
                        if (typeAttGrpAttributeGroupsData.size() > 0) {
                
                            for (int k = 0; k < typeAttGrpAttributeGroupsData.size(); k++) {
                                typeAttGrpAttributeGroupsSB.append(typeAttGrpAttributeGroupsData.get(k).getAttributes()).append(",");
                            }
                    
                            System.out.println("sb : " + typeAttGrpAttributeGroupsSB);
                            
                            for (int l = 0; l < typeAttGrpAttributeGroupsData.size(); l++) {
                                String typeAttGrpAttributeGroups = typeAttGrpAttributeGroupsSB.toString();
                                typeAttGrpAttributeGroupsArray = typeAttGrpAttributeGroups.split(",");
                                
                            }
                            
                        }
                        attrributeValuesDataResult.get(j).setAttributes(typeAttGrpAttributeGroupsArray);
                    }      
                    
                
                finalResult.setType(typeResult);
                
                
                
                
                
                /*Options*/
                List<OptionsDTO> optionsResult = productRepository.fetchOptionsData(variantId);
                System.out.println("Option Id : " + optionsResult.get(0).getOptionsId());
                System.out.println("OptionResult size : " + optionsResult.size());
                for (int i = 0; i < optionsResult.size(); i++) {
                    System.out.println("ID : " + optionsResult.get(i).getOptionsId());

                    int optionsId = Integer.parseInt(edUtil.decrypt(optionsResult.get(i).getOptionsId()));
                    System.out.println("Attr ID : " + optionsId);
                    List<OptionsValuesDTO> optionsValuesData = productRepository.fetchOptionsValuesData(optionsId);
                    optionsResult.get(i).setValues(optionsValuesData);
                }
                // List<AttributesValuesDTO> attrributeValuesData = productRepository.fetchAttributeValuesData(attributeResult)
                System.out.println("attributeResult : " + optionsResult.toString());

                finalResult.setOptions(optionsResult);
                
                
                
                
                
                
                  /*Categories*/
                List<CategoriesDTO> categoriesResult = productRepository.fetchCategoriesData(variantId);
                for (int i = 0; i < categoriesResult.size(); i++) {
                    System.out.println("ID : " + categoriesResult.get(i).getCategoriesId());

                    int categoriesId = Integer.parseInt(edUtil.decrypt(categoriesResult.get(i).getCategoriesId()));
                    System.out.println("Category ID : " + categoriesId);
                    CategoriesCustomfieldsDTO categoriesCustomFieldsValuesData = productRepository.fetchCategoriesCustomfieldsData(categoriesId);
                    categoriesResult.get(i).setCustomFields(categoriesCustomFieldsValuesData);
                }
                // List<AttributesValuesDTO> attrributeValuesData = productRepository.fetchAttributeValuesData(attributeResult)
                System.out.println("categoriesResult : " + categoriesResult.toString());

                finalResult.setCategories(categoriesResult);
                
                
                 /*CustomFields*/ 
                 VariantCustomFieldsDTO cutomFieldsData = productRepository.fetchVariantCustomFieldsData(variantId);
                 System.out.println("cutomFieldsData : " + cutomFieldsData);
                 finalResult.setCustomFields(cutomFieldsData);
                                
//               if (!attributeResult.isEmpty()) {
//                   finalResult.setAttributes(attributeResult);
//                   for (int i = 0; i < attributeResult.size(); i++) {
//                       AttributesValuesDTO 
//                   }
//                  
//               }
            }

            return finalResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        } finally {

        }
    }

    @Override
    public List<ModelPackageDTO> retrieveModelPackageDetails(String encryptedModelId) {
        int modelId = Integer.parseInt(edUtil.decrypt(encryptedModelId));
        try {
            return productRepository.fetchModelPackageDetails(modelId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<ModelAccessoriesDTO> retrieveModelAccessoies(String encryptedModelId) {
        int modelId = Integer.parseInt(edUtil.decrypt(encryptedModelId));
        try {
            return productRepository.fetchModelAccessoies(modelId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<ColorsForEachpartDTO> retrieveColoursForEachPart(String encryptedVariantId) {
        int variantId = Integer.parseInt(edUtil.decrypt(encryptedVariantId));
        System.out.println("Mdel ID 14 : " + edUtil.encrypt(String.valueOf(1)));
        try {
            return productRepository.fetchColoursForEachPart(variantId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<BatteryChargingetailsDTO> retrieveBatteryChargingDetails(String encryptedVariantId) {
        int variantId = Integer.parseInt(edUtil.decrypt(encryptedVariantId));
        try {
            return productRepository.fetchBatteryChargingDetails(variantId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }



    @Override
    public List<VariantStatePriceDTO> retrieveVariantStatePrice(String encryptedVariantId, String stateName) {
        int variantId = Integer.parseInt(edUtil.decrypt(encryptedVariantId));
        try {
            return productRepository.fetchVariantStatePrice(variantId, stateName);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }
}
