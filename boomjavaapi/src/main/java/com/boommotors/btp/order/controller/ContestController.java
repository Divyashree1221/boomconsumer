/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.controller;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.order.payload.MyTemplateLikesRequest;
import com.boommotors.btp.order.dao.ContestDAO;
import com.boommotors.btp.order.dto.ContestTemplatesDTO;
import com.boommotors.btp.order.dto.MytemplateLikesDTO;
import com.boommotors.btp.order.service.ContestService;

import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.payload.OrderRequest;
import com.boommotors.btp.payload.PagedResponse;
import com.boommotors.btp.util.EncryptDecrypt;
import java.util.List;
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
 * @author NandiniC
 */
@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    ContestService contestService;

    @Autowired
    ContestDAO contestRepository;

    @Autowired
    EncryptDecrypt edUtil;

    @GetMapping("/template/{user_id}")
    public ResponseEntity<?> getContestByUser(@PathVariable(value = "user_id") String encryptedUserID) {
        List<ContestTemplatesDTO> templeteData = contestService.retreiveTempleteForUser(encryptedUserID);

        if (templeteData == null) {
            return new ResponseEntity(new ApiResponse(false, "Template not found!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Template found!", templeteData),
                HttpStatus.OK);
    }

    @PostMapping("/create/my-template-likes")
    public ResponseEntity<?> createMyTemplateLikes(@RequestBody MyTemplateLikesRequest myTemplateLikesRequest) {
        try {

            String myTemplateLikesId = contestService.createmyTemplateLikes(myTemplateLikesRequest);

            if (!myTemplateLikesId.equals("")) {
                ContestTemplatesDTO contestTempletesData = contestService.retrieveContestTemplatesById(myTemplateLikesRequest.getContestTemplatesId());
                return new ResponseEntity(new ApiResponse(true, "Like added successfully", contestTempletesData),
                        HttpStatus.OK);
            } else if (myTemplateLikesId.equals("")) {
                return new ResponseEntity(new ApiResponse(false, "you have already Liked the Template!", null),
                        HttpStatus.OK);
            }

//            if (myTemplateLikesId != null || !myTemplateLikesId.equals("")) {
//                return new ResponseEntity(new ApiResponse(true, "Like added successfully", myTemplateLikesId),
//                        HttpStatus.OK);
//            }
            return new ResponseEntity(new ApiResponse(false, "Problem occured while creating the order!", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }

    }

    @GetMapping("/selectAll/ContestTemplete/{page}/{user_id}")
    public ResponseEntity<?> retrieveAllContestTemplete(@PathVariable(value = "page") int page, @PathVariable(value = "user_id") String user_id) {

        // Some response bean to standardise all restful responses.
        PagedResponse<ContestTemplatesDTO> result = contestService.fetchAllContestTemplete(page, user_id);

        if (result == null) {
            return new ResponseEntity(new ApiResponse(false, "No Records found!", null),
                    HttpStatus.OK);
        } else if (result.getContent().isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, "No Records found!", null),
                    HttpStatus.OK);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/selectAll/OtherContestTemplete/{user_id}")
    public ResponseEntity<?> retrieveOtherTempleteByUserId(@PathVariable(value = "user_id") String user_id) {

        // Some response bean to standardise all restful responses.
        List<MytemplateLikesDTO> result = contestService.retrieveOtherTempleteByUserId(user_id);

        if (result == null) {
            return new ResponseEntity(new ApiResponse(false, "No Records found!", null),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponse(true, "Records found!", result),
                    HttpStatus.OK);
        }
    }

    /**
     * create contest template.
     *
     * @param orderRequest
     * @return templateId
     */
    @PostMapping("/template/create")
    public ResponseEntity<?> createContestTemplate(@RequestBody OrderRequest orderRequest) {
        try {
            if (orderRequest.getOrderSummaryId().equals("") || orderRequest.getTemplateName().equals("") || orderRequest.getUserNickname().equals("")) {
                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);
            }

            //checking whether the template has already created for the given summary_id
            Integer summaryId = Integer.parseInt(edUtil.decrypt(orderRequest.getOrderSummaryId()));
            Boolean recordExist = contestRepository.existsByOrderSummaryId(summaryId);
            System.out.println("recordExist : " + recordExist);
            if (recordExist) {
                return new ResponseEntity(new ApiResponse(true, "Contest template already exist with given summary_id!", null),
                        HttpStatus.OK);
            }

            //service call to insert contest template
            String templateId = contestService.createContestTemplate(orderRequest);

            if (!templateId.isEmpty()) {
                return new ResponseEntity(new ApiResponse(true, "Contest template created successfully!!", templateId),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(false, "Problem occured while creating the contest template!", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }

}
