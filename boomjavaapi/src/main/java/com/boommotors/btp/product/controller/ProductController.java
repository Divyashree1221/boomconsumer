package com.boommotors.btp.product.controller;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.payload.ApiResponseForProducts;
import com.boommotors.btp.product.dao.ProductDAO;
import com.boommotors.btp.product.dto.BatteryChargingetailsDTO;
import com.boommotors.btp.product.dto.ColorsForEachpartDTO;
import com.boommotors.btp.product.dto.ColourDTO;
import com.boommotors.btp.product.dto.MainAddOnsDTO;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.ModelPackageDTO;
import com.boommotors.btp.product.dto.ModelAccessoriesDTO;
import com.boommotors.btp.product.dto.ProductDTO;
import com.boommotors.btp.product.dto.TestTableDTO;
import com.boommotors.btp.product.dto.VariantStatePriceDTO;
import com.boommotors.btp.product.dto.VariantsDTO;
import com.boommotors.btp.product.service.ProductService;
import com.boommotors.btp.util.DateUtil;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author divyashree
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;
    
    @Autowired
    ProductDAO productRepository;

    @Autowired
    DateUtil dateUtil;

    /**
     * Get all models.
     *
     * @return RESULT
     */
    @GetMapping("/model")
    public ResponseEntity<?> retrieveModels() {
        List<ModelDTO> result = productService.retrieveModels();
        if (result == null) {
            return new ResponseEntity(new ApiResponse(false, "Data not retrieved successfully!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", result),
                HttpStatus.OK);
    }

    
    /**
     * Get list of colours based on variant_id.
     *
     * @param encryptedVariantId
     * @return RESULT
     */
    @GetMapping("/colour/{variant_id}")
    public ResponseEntity<?> retrieveColours(@PathVariable(value = "variant_id") String encryptedVariantId) {
        try {
            List<ColourDTO> result = productService.retrieveColours(encryptedVariantId);
            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "Data not retrieved successfully!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", result),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }

   
    /**
     * Get list of add-ons based on model_id.
     *
     * @param encryptedModelId
     * @return RESULT
     */
    @GetMapping("/add-ons/{model_id}")
    public ResponseEntity<?> retrieveAddOns(@PathVariable(value = "model_id") String encryptedModelId) {
        try {
            List<MainAddOnsDTO> result = productService.retrieveAddOnsByModelId(encryptedModelId);
            if (result.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "Add-Ons not found!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Add-Ons found", result),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }

   
    @GetMapping("/details/{variant_id}")
    public ResponseEntity<?> retrieveProductDetails(@PathVariable(value = "variant_id") String encryptedVariantId) {
        try {
            ProductDTO result = productService.retrieveProductDetailsByVariantId(encryptedVariantId);
            if (result != null) {
                
                
                
                return new ResponseEntity(new ApiResponse(true, "Product found!", result),
                        HttpStatus.OK);
            } 

            return new ResponseEntity(new ApiResponse(false, "Product not found!", null),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }
    
    
    @GetMapping("/test")
    public ResponseEntity<?> retrieveTestTable() {
        try {
            List<TestTableDTO> result = productRepository.fetchTestTable();
            List<TestTableDTO> result1 = new ArrayList<>();
            
            System.out.println("result : " + result.toString());
            
            //TestTableArrayDTO testTableArrayDTO = new TestTableArrayDTO();
            String[] test2Array = null;
            System.out.println("Size : " + result.size());
            
            for(int i = 0; i < result.size(); i++) {
                String test2 = result.get(i).getTest2();
                System.out.println("Test2 Stirng  :  " + test2);
                test2Array= test2.split(",");
                
                TestTableDTO data = new TestTableDTO();
                //data = result.get(i);
                data.setId(result.get(i).getId());
                data.setTest2(result.get(i).getTest2());
                data.setTestField(result.get(i).getTestField());
                data.setTest2Array(test2Array);
                //result.clear();
                
                result1.add(i, data);
            }
            
//            for(int i = 0; i < result.size(); i++) {
//                TestTableDTO data = new TestTableDTO();
//                //data = result.get(i);
//                data.setId(result.get(i).getId());
//                data.setTest2Array(test2Array);
//                //result.clear();
//                result1.add(i, data);
//            }
                 
            
//            for(int i = 0; i < result.size(); i++) {
//                String test2 = result.get(i).getTest2();
//                System.out.println("Test2 Stirng  :  " + test2);
//                test2Array= test2.split(",");
//                
//                for(int j = 0; j < test2Array.length; j++) { 
//                System.out.println("Test2 Array  " + j + " : " +  test2Array[j]);
////                result.set(i, result.get(0).getTest2());
////                result.set(i, result.get(0).getTest2())setTest2Array(test2Array);
//                testTableArrayDTO.setTest2Array(test2Array);
//            }
//                
//            }

            if (result.isEmpty()) {
                return new ResponseEntity(new ApiResponseForProducts(false, "Data not retrieved successfully!", null, null, null),
                        HttpStatus.OK);
            }
           
            return new ResponseEntity(new ApiResponse(true, "Data retrieved", result1),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }
    
    @GetMapping("/variants/{model_id}")
    public ResponseEntity<?> retrieveAllVarinats(@PathVariable(value = "model_id") String encryptedModelId) {
        try {
           
            List<VariantsDTO> resultVariant = productService.retrieveAllVariant(encryptedModelId);

            if (resultVariant.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "Data not retrieved successfully!", null),
                        HttpStatus.OK);
            }
           
            return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", resultVariant),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }
    
    @GetMapping("/model-package-details/{model_id}")
    public ResponseEntity<?> retrieveModelPackageDetails(@PathVariable(value = "model_id") String encryptedModelId) {
        try {
           
            List<ModelPackageDTO> resultVariant = productService.retrieveModelPackageDetails(encryptedModelId);

            if (resultVariant.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "Data not retrieved successfully!", null),
                        HttpStatus.OK);
            }
           
            return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", resultVariant),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }
    
    @GetMapping("/model-accessories/{model_id}")
    public ResponseEntity<?> retrieveModelAccessoies(@PathVariable(value = "model_id") String encryptedModelId) {
        try {
           
            List<ModelAccessoriesDTO> resultVariant = productService.retrieveModelAccessoies(encryptedModelId);

            if (resultVariant.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "Data not retrieved successfully!", null),
                        HttpStatus.OK);
            }
           
            return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", resultVariant),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }
    
    /**
     * Get all colours for each part.
     *
     * @param encryptedVariantId
     * @return RESULT
     */
    @GetMapping("/colours-for-each-part/{variant_id}")
    public ResponseEntity<?> retrieveColoursForEachPart(@PathVariable(value = "variant_id") String encryptedVariantId) {
        try {
            List<ColorsForEachpartDTO> result = productService.retrieveColoursForEachPart(encryptedVariantId);
            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "Data not retrieved successfully!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", result),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }
    
    /**
     * Get  all colours for each part.
     *
     * @param encryptedVariantId
     * @return RESULT
     */
    @GetMapping("/battery-charging-details/{variant_id}")
    public ResponseEntity<?> retrieveBatteryChargingDetails(@PathVariable(value = "variant_id") String encryptedVariantId) {
        try {
            List<BatteryChargingetailsDTO> result = productService.retrieveBatteryChargingDetails(encryptedVariantId);
            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "Data not retrieved successfully!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", result),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }
    
    /**
     * Get variant state price
     *
     * @param encryptedVariantId
     * @param stateName
     * @return RESULT
     */
    @GetMapping("/variant-state-price/{variant_id}/{state_name}")
    public ResponseEntity<?> retrieveVariantStatePrice(@PathVariable(value = "variant_id") String encryptedVariantId,
            @PathVariable(value = "state_name") String stateName) {
        try {
            List<VariantStatePriceDTO> result = productService.retrieveVariantStatePrice(encryptedVariantId, stateName);
            if (result.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "Out of coverage area!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", result),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }
}
