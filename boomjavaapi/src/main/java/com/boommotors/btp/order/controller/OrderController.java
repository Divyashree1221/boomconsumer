/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.controller;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.order.dto.EVStatusDTO;
import com.boommotors.btp.order.dto.FinanceStatusDTO;
import com.boommotors.btp.order.dto.OrderSummaryAndRazorpayDTO;
import com.boommotors.btp.order.dto.TransactionStatusDTO;
import com.boommotors.btp.order.service.ContestService;
import com.boommotors.btp.order.service.OrderService;
import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.payload.OrderRequest;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ContestService contestService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            if (orderRequest.getUserId().equals("") || orderRequest.getVariantId().equals("")) {
                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);
            }
            //call the service call for inserting order summary
            String newOrderSummaryID = orderService.createOrderSummary(orderRequest);

            if (!"".equals(newOrderSummaryID)) {

//                if (orderRequest.getParticipateInContest()) {
//                    //check if the user already has created the templete
//                     List<ContestTempletesDTO> templeteData = contestService.retreiveTempleteForUser(orderRequest.getUserId());
//
//                    if (templeteData == null) {
//
//                        //check if the table already has templete with same colour combination
//                        ContestTempletesDTO templeteColourCombination = contestService.retreiveTempleteForTheColourCombination(orderRequest);
//                        if (templeteColourCombination == null) {
//
//                            String newContestTempleteID = contestService.createContestTemplete(orderRequest);
//                            return new ResponseEntity(new ApiResponse(true, "Order created successfully!! And your template has been submitted for the contest. Please check your dashboard. ", newOrderSummaryID),
//                                    HttpStatus.OK);
//                        } else {
//
//                            return new ResponseEntity(new ApiResponse(false, "Order created successfully!! A template with the same colour combination is already registered for the contest. If you want to submit another template for the competition, please check your dashboard.", newOrderSummaryID),
//                                    HttpStatus.OK);
//                        }
//
//                    } else {
//                        return new ResponseEntity(new ApiResponse(false, "Order created successfully!! You have already submitted a template for the contest. Please check your dashboard.", newOrderSummaryID),
//                                HttpStatus.OK);
//                    }
//
//                } else {
                return new ResponseEntity(new ApiResponse(true, "Order created successfully!!", newOrderSummaryID),
                        HttpStatus.OK);
                // }
            }
            return new ResponseEntity(new ApiResponse(false, "Problem occured while creating the order!", null),
                    HttpStatus.BAD_REQUEST);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }

    }

    @GetMapping("/order/{orderSummary_id}")

    public ResponseEntity<?> retreiveOrderSummary(@PathVariable(value = "orderSummary_id") String orderSummaryId) {

        return null;

    }

    @GetMapping("/order-summary-ev-status/{user_id}")

    public ResponseEntity<?> retreiveOrderSummaryEVDataByUserId(@PathVariable(value = "user_id") String userId) {

        List<EVStatusDTO> result = orderService.retrieveOrderSummaryEVStatusByUserIdForStatus(userId);
        //System.out.println("EVStatus ID  : " + result.getOrderEvStatus().get(0).getEvStatusId());
        System.out.println("result" + result);
        if (result.equals("") || result == null) {
            return new ResponseEntity(new ApiResponse(false, "No Status for this User!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Status found!", result),
                HttpStatus.OK);
    }

    @GetMapping("/order-summary-finance-status/{user_id}")

    public ResponseEntity<?> retreiveOrderSummaryFinanceDataByUserId(@PathVariable(value = "user_id") String userId) {

        List<FinanceStatusDTO> result = orderService.retrieveOrderSummaryFinanceStatusByUserIdForStatus(userId);
        //System.out.println("EVStatus ID  : " + result.getOrderEvStatus().get(0).getEvStatusId());
        System.out.println("result" + result);
        if (result.equals("") || result == null) {
            return new ResponseEntity(new ApiResponse(false, "No Status for this User!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Status found!", result),
                HttpStatus.OK);
    }

    @GetMapping("/order-summary-transaction-status/{user_id}")
    public ResponseEntity<?> retreiveOrderSummaryTransactionDataByUserId(@PathVariable(value = "user_id") String userId) {

        List<TransactionStatusDTO> result = orderService.retrieveOrderSummaryTransactionStatusByUserIdForStatus(userId);
        //System.out.println("EVStatus ID  : " + result.getOrderEvStatus().get(0).getEvStatusId());
        System.out.println("result" + result);
        if (result.equals("") || result == null) {
            return new ResponseEntity(new ApiResponse(false, "No Status for this User!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Status found!", result),
                HttpStatus.OK);
    }

    @GetMapping("/order-summary/{user_id}")
    public ResponseEntity<?> retreiveOrderStatus(@PathVariable(value = "user_id") String userId) {
        List<OrderSummaryAndRazorpayDTO> result = orderService.retrieveOrderSummaryByUserId(userId);
        if (result == null || result.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, "No OrderSummary for this User!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "OrderSummary found!", result),
                HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateOrderSummary(@RequestBody OrderRequest orderRequest) {
        try {
            if (orderRequest.getUserId().equals("") || orderRequest.getVariantId().equals("")
                    || orderRequest.getVariantName().equals("") || orderRequest.getColourName().equals("")) {
                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);
            }
            Boolean res = orderService.updateOrderSummary(orderRequest);
            if (res) {
                return new ResponseEntity(new ApiResponse(true, "Order updated successfully.", res),
                        HttpStatus.OK);
            } else if (res == null) {
                return new ResponseEntity(new ApiResponse(false, "Problem occured while updating the order details!", null),
                        HttpStatus.OK);
            }
            return new ResponseEntity(new ApiResponse(false, "Problem occured while updating the order details!", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody OrderRequest orderRequest) {
        try {
            String res = orderService.orderCancellation(orderRequest);
            if ("processed".equals(res)) {
                return new ResponseEntity(new ApiResponse(true, "Order cancelled and refunded successfully.", res),
                        HttpStatus.OK);
            } else if ("Issue occured while updating changes to tables.".equals(res)) {
                return new ResponseEntity(new ApiResponse(false, "Amount refunded but issue occured while updating changes to tables.", null),
                        HttpStatus.OK);

            } else {
                return new ResponseEntity(new ApiResponse(false, res, res),
                        HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @Scheduled(cron = "0 0 06 * * *", zone = "IST")
    public void autoUpdateOrderTransactionTable() {
        System.out.println("Hello cron Scheduler...................................  :" + new Date());
        orderService.autoUpdateOrderTransactionTable();
    }

}
