/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.controller;

import com.boommotors.btp.dealer.dto.HeardFromWhereDTO;
import com.boommotors.btp.dealer.dto.StateDTO;
import com.boommotors.btp.dealer.payload.DealershipEnquiryRequest;
import com.boommotors.btp.dealer.service.DealerService;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.payload.ApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author divyashree
 */
@RestController
@RequestMapping("/api/dealership")
public class DealershipController {

    @Autowired
    DealerService dealerService;

    @GetMapping("/state/all")
    public ResponseEntity<?> retrieveAllState() {
        try {
            List<StateDTO> result = dealerService.retrieveAllState();

            if (result.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "No records found!", null),
                        HttpStatus.OK);
            }
            return new ResponseEntity(new ApiResponse(true, "Records found.", result),
                    HttpStatus.OK);
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/heard-from-where")
    public ResponseEntity<?> retrieveHeardFromWhere() {
        try {
            List<HeardFromWhereDTO> result = dealerService.retrieveHeardFromWhere();

            if (result.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "No records found!", null),
                        HttpStatus.OK);
            }
            return new ResponseEntity(new ApiResponse(true, "Records found.", result),
                    HttpStatus.OK);
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/enquiry/create")
    public ResponseEntity<?> createDealerMaster(@RequestBody DealershipEnquiryRequest enquiryRequest) {
        try {
            if ((!"".equals(enquiryRequest.getName()) && enquiryRequest.getName() != null)
                    && (!"".equals(enquiryRequest.getEmail()) && enquiryRequest.getEmail() != null)
                    && (!"".equals(enquiryRequest.getContactNo()) && enquiryRequest.getContactNo() != null)
                    && (!"".equals(enquiryRequest.getIsRegistered()) && enquiryRequest.getIsRegistered() != null)
                    && (!"".equals(enquiryRequest.getCompanyName()) && enquiryRequest.getCompanyName() != null)
                    && (!"".equals(enquiryRequest.getCompanyAddress()) && enquiryRequest.getCompanyAddress() != null)
                    && (!"".equals(enquiryRequest.getDesignation()) && enquiryRequest.getDesignation() != null)
                    && (!"".equals(enquiryRequest.getPreferredState1()) && enquiryRequest.getPreferredState1() != null)
                    && (!"".equals(enquiryRequest.getDistrictCity()) && enquiryRequest.getDistrictCity() != null)
                    && (!"".equals(enquiryRequest.getIsExperienced()) && enquiryRequest.getIsExperienced() != null)
                    && (!"".equals(enquiryRequest.getHeardFromWhere()) && enquiryRequest.getHeardFromWhere() != null)) {

                String encryptedEnquiryId = dealerService.createDealerEnquiry(enquiryRequest);

                if (!"".equals(encryptedEnquiryId) && encryptedEnquiryId != null) {
                    return new ResponseEntity(new ApiResponse(true, "Dealer enquiry created successfully!!", encryptedEnquiryId),
                            HttpStatus.OK);
                }
                return new ResponseEntity(new ApiResponse(false, "Problem occured while creating dealer enquiry!", null),
                        HttpStatus.OK);

            }
            return new ResponseEntity(new ApiResponse(false, "Please fill mandatory fields!.", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }
}
