package com.boommotors.btp.dealer.controller;

import com.boommotors.btp.dealer.dto.DealerMasterDTO;
import com.boommotors.btp.dealer.payload.DealerMasterRequest;
import com.boommotors.btp.dealer.payload.NewDealershipEnquiryRequest;
import com.boommotors.btp.dealer.service.DealerService;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.payload.ApiResponse;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ramya
 */
@RestController
@RequestMapping("/api/dealer")
public class DealerController {

    @Autowired
    DealerService dealerService;

    @PostMapping("/master/create")
    public ResponseEntity<?> createDealerMaster(@RequestBody DealerMasterRequest dealerRequest) {
        try {
            if (!"".equals(dealerRequest.getPin()) && dealerRequest.getPin() != null) {
                dealerRequest.setStatus("Draft");
                if (dealerRequest.getDraftFinalSave() == true) {
                    dealerRequest.setStatus("Submitted");
                    if (dealerRequest.getDealerBankerInfo().size() < 1 || dealerRequest.getDealerPersonnelInfo().size() < 1 || dealerRequest.getDealeReferenceInfo().size() != 3
                            || "".equals(dealerRequest.getUserId()) || dealerRequest.getUserId() == null) {
                        return new ResponseEntity(new ApiResponse(false, "Mandatory fileds are required!", null),
                                HttpStatus.OK);
                    }
                }

                if (dealerRequest.getDealerId() == null || "".equals(dealerRequest.getDealerId())) {
                    String encryptedDealerId = dealerService.createDealerMaster(dealerRequest);

                    if (!"".equals(encryptedDealerId) && encryptedDealerId != null) {
                        return new ResponseEntity(new ApiResponse(true, "Dealer master created successfully!!", encryptedDealerId),
                                HttpStatus.OK);
                    }
                    return new ResponseEntity(new ApiResponse(false, "Problem occured while creating the dealer master!", null),
                            HttpStatus.OK);

                } else {
                    Boolean result = dealerService.update(dealerRequest);
                    if (!result) {
                        return new ResponseEntity(new ApiResponse(false, "Dealer master data not updated successfully!", null),
                                HttpStatus.OK);
                    }
                    return new ResponseEntity(new ApiResponse(true, "Dealer master data updated successfully.", result),
                            HttpStatus.OK);
                }
            }
            return new ResponseEntity(new ApiResponse(false, "Please enter pincode.", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/master/byid/{user_id}")
    public ResponseEntity<?> retrieveDealerDetailsByUserId(@PathVariable(value = "user_id") String encryptedUserId) {

        try {
            DealerMasterDTO result = dealerService.retrieveDealerDetails(encryptedUserId);

            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "No record found for this user!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Record found!", result),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/master/bynumber/{dealership_number}")
    public ResponseEntity<?> retrieveDealerDetailsByDealershipNo(@PathVariable(value = "dealership_number") String dealershipNumber) {

        try {
            DealerMasterDTO result = dealerService.retrieveByDealershipNo(dealershipNumber);

            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "No record found!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Record found!", result),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/master/all/{user_id}")
    public ResponseEntity<?> retrieveAllDealers(@PathVariable(value = "user_id") String encryptedUserId) {

        try {
            List<DealerMasterDTO> result = dealerService.retrieveDealersData(encryptedUserId);

            if (result == null || result.isEmpty()) {
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

    @PostMapping("/master/update")
    public ResponseEntity<?> updateDealerMaster(@Valid @RequestBody DealerMasterRequest dealerRequest) {

        boolean result = dealerService.updateDealerMaster(dealerRequest);
        if (!result) {
            return new ResponseEntity(new ApiResponse(false, "Data not updated successfully!", null),
                    HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(true, "Data updated successfully.", result),
                HttpStatus.OK);
    }

    @PostMapping("/enquiry/create")
    public ResponseEntity<?> createDealershipEnquiry(@RequestBody NewDealershipEnquiryRequest enquiryRequest) {
        try {
            if ((!"".equals(enquiryRequest.getIsExperienced()) && enquiryRequest.getIsExperienced() != null)
                    && (!"".equals(enquiryRequest.getPincode1()) && enquiryRequest.getPincode1() != null)
                    && (!"".equals(enquiryRequest.getAmount()) && enquiryRequest.getAmount() != null && enquiryRequest.getAmount() != 0)
                    && (!"".equals(enquiryRequest.getComments()) && enquiryRequest.getComments() != null)) {

                String encryptedEnquiryId = dealerService.createDealershipEnquiry(enquiryRequest);

                if (!"".equals(encryptedEnquiryId) && encryptedEnquiryId != null) {
                    return new ResponseEntity(new ApiResponse(true, "Dealership enquiry created successfully!", encryptedEnquiryId),
                            HttpStatus.OK);
                }
                return new ResponseEntity(new ApiResponse(false, "Problem occured while creating Dealership enquiry! Try again.", null),
                        HttpStatus.OK);

            }
            return new ResponseEntity(new ApiResponse(false, "Fill mandatory fields.", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

}
