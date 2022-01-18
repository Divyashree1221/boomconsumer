/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.controller;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.order.dto.BulkEnquiryDTO;
import com.boommotors.btp.order.dto.BulkEnquiryDetailsDTO;
import com.boommotors.btp.order.service.BulkOrderService;
import com.boommotors.btp.order.service.ContestService;
import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.payload.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.boommotors.btp.order.dto.BulkEnquirySuccessDTO;
import java.util.List;

/**
 *
 * @author divyashree
 */
@RestController
@RequestMapping("/api/bulk-order")
public class BulkOrderController {

    @Autowired
    BulkOrderService bulkOrderService;

    @Autowired
    ContestService contestService;

    /**
     * Capture bulk enquiry details.
     *
     * @param orderRequest
     * @return bulkEnquiryID
     */
    @PostMapping("/enquiry/create")
    public ResponseEntity<?> createBulkOrderEnquiry(@RequestBody OrderRequest orderRequest) {
        try {
            if (orderRequest.getUserId().equals("") || orderRequest.getVariantId().equals("")) {
                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);
            }
            //call the service call for inserting bulk enquiry
            String bulkEnquiryID = bulkOrderService.createBulkEnquiry(orderRequest);

            if (!bulkEnquiryID.isEmpty()) {
                return new ResponseEntity(new ApiResponse(true, "Bulk enquiry created successfully!!", bulkEnquiryID),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(false, "Problem occured while creating the Bulk order enquiry!", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }

    /**
     * Fetch payment details using paymentId on successful payment.
     *
     * @param bulk_enquiry_id
     * @return RESULT
     */
    @GetMapping("/details/{bulk_enquiry_id}")
    public ResponseEntity<?> retrievePaymentDetails(@PathVariable(value = "bulk_enquiry_id") String bulk_enquiry_id) {
        try {
            BulkEnquirySuccessDTO result = bulkOrderService.FetchBulkEnquiryByID(bulk_enquiry_id);
            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "Bulk Enquiry details not retrieved successfully!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Bulk Enquiry details retrieved successfully", result),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/enquiry/details/{user_id}")

    public ResponseEntity<?> retrieveBulkEnquiryDetails(@PathVariable(value = "user_id") String userId) {

        try {
            List<BulkEnquiryDetailsDTO> result = bulkOrderService.retrieveBulkEnquiryUserID(userId);

            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "No Bulk Enquiry for this User!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Bulk Enquiry found!", result),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }
}
