/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.controller;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.invoice.util.IndexHelper;
import com.boommotors.btp.invoice.util.Invoice;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.order.service.ContestService;
import com.boommotors.btp.order.service.OrderService;
import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.payload.OrderRequest;
import com.boommotors.btp.payment.dto.OrderDTO;
import com.boommotors.btp.payment.dto.PaymentDetailsDTO;
import com.boommotors.btp.payment.payload.RazorpayOnlinePaymentOrderTransactionRequest;
import com.boommotors.btp.payment.payload.RazorpayOrderRequest;
import com.boommotors.btp.payment.payload.RazorpayOrderTransactionRequest;
import com.boommotors.btp.payment.service.PaymentService;
import com.boommotors.btp.util.EncryptDecrypt;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

/**
 *
 * @author NandiniC
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    OrderService orderService;

    @Autowired
    ContestService contestService;

    @Autowired
    ServletContext context;

    private final TemplateEngine templateEngine;

    public PaymentController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Autowired
    ServletContext servletContext;

    @Value("${razorpay.keyId}")
    private String razorpayKeyId;

    @Value("${razorpay.keySecret}")
    private String razorpayKeySecret;
    
    /**
     *
     * @param razorpayOrderRequest
     * @return
     * @throws RazorpayException
     */
    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody RazorpayOrderRequest razorpayOrderRequest) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        if (razorpayOrderRequest.getPaymentType().equals("Booking advance")) {
            OrderSummaryDTO orderSummaryData = paymentService.retrieveOrderSummary(razorpayOrderRequest.getOrderSummaryId());

            System.out.println("Data  : " + orderSummaryData);
            if (orderSummaryData != null) {
                OrderDTO res = paymentService.createRazorpayOrder(razorpayOrderRequest, orderSummaryData);

                return new ResponseEntity(new ApiResponse(true, "Order created.", res),
                        HttpStatus.OK);
            }

        } else {
            OrderDTO res = paymentService.createOnlinePaymentRazorpayOrder(razorpayOrderRequest);

            return new ResponseEntity(new ApiResponse(true, "Order created.", res),
                    HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Something went wrong.", null),
                HttpStatus.OK);
    }

    /**
     *
     * @param razorpayOrderTransactionRequest
     * @return
     * @throws RazorpayException
     */
    @PostMapping("/order/transaction")
    public ResponseEntity<?> saveOrderTransaction(@RequestBody RazorpayOrderTransactionRequest razorpayOrderTransactionRequest) throws RazorpayException {

//        OrderDetailDTO orderDetailData = paymentService.retrieveOrderDetailByRazorPayOrderId(razorpayOrderTransactionRequest.getRazorpayOrderId());
//        System.out.println("ID : " + razorpayOrderTransactionRequest.getRazorpayOrderId());
        int orderSummaryIdInt = Integer.parseInt(edUtil.decrypt(razorpayOrderTransactionRequest.getOrderSummaryId()));
        String payload = razorpayOrderTransactionRequest.getRazorpayOrderId() + '|' + razorpayOrderTransactionRequest.getRazorpayPaymentId();
        String actualSignature = getHash(payload, razorpayKeySecret);

        System.out.println("Server Signature : " + actualSignature);
        System.out.println("Incoming Signature : " + razorpayOrderTransactionRequest.getSignature());
        System.out.println("Summary ID : " + orderSummaryIdInt);

        if (actualSignature.equals(razorpayOrderTransactionRequest.getSignature())) {
            System.out.println("here...");
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            Payment payment = razorpay.Payments.fetch(razorpayOrderTransactionRequest.getRazorpayPaymentId());
            System.out.println("Payment details : " + payment);

            if (payment != null) {

                String orderTransactionIdEncrypted = paymentService.saveOrderTransaction(razorpayOrderTransactionRequest, payment);

                // insert into 3 OrderStatus  Tables
                orderService.createOrderStatus(razorpayOrderTransactionRequest.getOrderSummaryId(), razorpayOrderTransactionRequest.getUserId());

                //insert into Contest Template tables 
                if (!"".equals(razorpayOrderTransactionRequest.getOrderSummaryId()) && "".equals(razorpayOrderTransactionRequest.getColourName()) && razorpayOrderTransactionRequest.getParticipateInContest() == true) {

                    // assign 
                    OrderRequest orderRequest = new OrderRequest();

                    orderRequest.setUserId(razorpayOrderTransactionRequest.getUserId());
                    orderRequest.setVariantId(razorpayOrderTransactionRequest.getVariantId());
                    orderRequest.setTemplateName(razorpayOrderTransactionRequest.getTemplateName());
                    orderRequest.setUserNickname(razorpayOrderTransactionRequest.getUserNickname());
                    orderRequest.setBaseImage(razorpayOrderTransactionRequest.getBaseImage());
                    orderRequest.setBodyMudGuardColourID(razorpayOrderTransactionRequest.getBodyMudGuardColourID());
                    orderRequest.setBodyImage(razorpayOrderTransactionRequest.getBodyImage());
                    orderRequest.setChasisColourID(razorpayOrderTransactionRequest.getChasisColourID());
                    orderRequest.setFrameImage(razorpayOrderTransactionRequest.getFrameImage());
                    orderRequest.setMaskHandleBarColourID(razorpayOrderTransactionRequest.getMaskHandleBarColourID());
                    orderRequest.setMaskImage(razorpayOrderTransactionRequest.getMaskImage());
                    orderRequest.setWheelRimsColourID(razorpayOrderTransactionRequest.getWheelRimsColourID());
                    orderRequest.setRimsImage(razorpayOrderTransactionRequest.getRimsImage());
                    orderRequest.setShocksColourID(razorpayOrderTransactionRequest.getShocksColourID());
                    orderRequest.setShockImage(razorpayOrderTransactionRequest.getShockImage());
                    orderRequest.setOrderSummaryId(razorpayOrderTransactionRequest.getOrderSummaryId());

                    String newContestTempleteID = contestService.createContestTemplate(orderRequest);

                    if (newContestTempleteID != null) {

                        System.out.println("orderTransactionIdEncrypted : " + orderTransactionIdEncrypted);
                        return new ResponseEntity(new ApiResponse(true, "Payment Successfull!! And your template has been submitted for the contest. Please check your dashboard. ", orderTransactionIdEncrypted),
                                HttpStatus.OK);
                    } else {
                        System.out.println("orderTransactionIdEncrypted : " + orderTransactionIdEncrypted);
                        return new ResponseEntity(new ApiResponse(true, "Payment Successfull!! Problem occured while submiting your Template for the contest. ", orderTransactionIdEncrypted),
                                HttpStatus.OK);
                    }

                } else {

                    System.out.println("orderTransactionIdEncrypted : " + orderTransactionIdEncrypted);
                    return new ResponseEntity(new ApiResponse(true, "Payment Successfull.", orderTransactionIdEncrypted),
                            HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity(new ApiResponse(false, "Something went wrong.", null),
                HttpStatus.OK);
    }

    /**
     *
     * @param razorpayOrderTransactionRequest
     * @return
     * @throws RazorpayException
     */
    @PostMapping("/order/online-payment/transaction")
    public ResponseEntity<?> saveOnlinePaymentOrderTransaction(@RequestBody RazorpayOnlinePaymentOrderTransactionRequest razorpayOnlinePaymentOrderTransactionRequest) throws RazorpayException {

        int orderSummaryIdInt = Integer.parseInt(edUtil.decrypt(razorpayOnlinePaymentOrderTransactionRequest.getOrderSummaryId()));
        String payload = razorpayOnlinePaymentOrderTransactionRequest.getRazorpayOrderId() + '|' + razorpayOnlinePaymentOrderTransactionRequest.getRazorpayPaymentId();
        String actualSignature = getHash(payload, razorpayKeySecret);

        System.out.println("Server Signature : " + actualSignature);
        System.out.println("Incoming Signature : " + razorpayOnlinePaymentOrderTransactionRequest.getSignature());
        System.out.println("Summary ID : " + orderSummaryIdInt);

        if (actualSignature.equals(razorpayOnlinePaymentOrderTransactionRequest.getSignature())) {
            System.out.println("here...");
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            Payment payment = razorpay.Payments.fetch(razorpayOnlinePaymentOrderTransactionRequest.getRazorpayPaymentId());
            System.out.println("Payment details : " + payment);

            if (payment != null) {

                String orderTransactionIdEncrypted = paymentService.saveOnlinePaymentOrderTransaction(razorpayOnlinePaymentOrderTransactionRequest, payment);

                // insert into OrderTransactionStatus  Table when the payment type is "Full Amount Payment"
                if (razorpayOnlinePaymentOrderTransactionRequest.getPaymentType().equals("Full Payment")) {
                    orderService.createOrderTransactionStatus(razorpayOnlinePaymentOrderTransactionRequest.getOrderSummaryId(), razorpayOnlinePaymentOrderTransactionRequest.getUserId());

                    System.out.println("orderTransactionIdEncrypted : " + orderTransactionIdEncrypted);
                    return new ResponseEntity(new ApiResponse(true, "Payment Successfull.", orderTransactionIdEncrypted),
                            HttpStatus.OK);
                }

                System.out.println("orderTransactionIdEncrypted : " + orderTransactionIdEncrypted);
                return new ResponseEntity(new ApiResponse(true, "Payment Successfull.", orderTransactionIdEncrypted),
                        HttpStatus.OK);
            }
        }

        return new ResponseEntity(new ApiResponse(false, "Something went wrong.", null),
                HttpStatus.OK);
    }

    /**
     *
     * @param payload
     * @param secret
     * @return
     * @throws RazorpayException
     */
    public static String getHash(String payload, String secret) throws RazorpayException {
        Mac sha256_HMAC;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
            return new String(Hex.encodeHex(hash));
        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }

    /**
     * Fetch payment details using paymentId on successful payment.
     *
     * @param paymentId
     * @return RESULT
     */
    @GetMapping("/details/{payment_id}")
    public ResponseEntity<?> retrievePaymentDetails(@PathVariable(value = "payment_id") String paymentId) {
        try {
            PaymentDetailsDTO result = paymentService.retrievePaymentDetails(paymentId);
            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "Payment details not retrieved successfully!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Payment details retrieved successfully", result),
                    HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping(path = "/invoice/{razorpay_payment_id}")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "razorpay_payment_id") String razorpayPaymentId) throws IOException, ParseException {

        OrderTransactionDTO result = paymentService.retrieveOrderTransaction(razorpayPaymentId);
        System.out.println("ORDER ID : " + result.getRazorpayOrderId());

        System.out.println("Transaction Data : " + result.getEmail());

        /* Do Business Logic*/
        Invoice invoice = IndexHelper.getInvoice(result);

        System.out.println("Invoice Data : " + invoice.getEmail());

        /* Create HTML using Thymeleaf template Engine */
        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("orderEntry", invoice);
        String orderHtml = templateEngine.process("index", context);

        /* Setup Source and target I/O streams */
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        //converterProperties.setBaseUri("http://localhost:8080");
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();
        
        /* Send the response as downloadable PDF */
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

//        return new ResponseEntity(new ApiResponse(true, "Data", result),
//                HttpStatus.OK);
//
    }

}
