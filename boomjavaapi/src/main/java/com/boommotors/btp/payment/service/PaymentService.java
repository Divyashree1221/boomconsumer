/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.service;

import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.payment.dto.OrderDTO;
import com.boommotors.btp.payment.dto.PaymentDetailsDTO;
import com.boommotors.btp.payment.payload.RazorpayOnlinePaymentOrderTransactionRequest;
import com.boommotors.btp.payment.payload.RazorpayOrderRequest;
import com.boommotors.btp.payment.payload.RazorpayOrderTransactionRequest;
import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import io.imagekit.sdk.models.results.Result;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author Ramya
 */
public interface PaymentService {

    OrderSummaryDTO retrieveOrderSummary(String orderSummaryId);

    OrderDTO createRazorpayOrder(RazorpayOrderRequest razorpayOrderRequest, OrderSummaryDTO orderSummaryData) throws RazorpayException;

    String saveOrderdetail(OrderDetailDTO orderDetail);

    String saveOrderTransaction(RazorpayOrderTransactionRequest razorpayOrderTransactionRequest, Payment payment);

    String saveOnlinePaymentOrderTransaction(RazorpayOnlinePaymentOrderTransactionRequest razorpayOnlinePaymentOrderTransactionRequest, Payment payment);

    OrderDetailDTO retrieveOrderDetailByRazorPayOrderId(String razorpayOrderId);

    PaymentDetailsDTO retrievePaymentDetails(String paymentId);

    OrderTransactionDTO retrieveOrderTransaction(String razorpayPaymentId);

    OrderDTO createOnlinePaymentRazorpayOrder(RazorpayOrderRequest razorpayOrderRequest) throws RazorpayException;

    public Result getPDF(String razorpayPaymentId, String firstName) throws IOException, ParseException;

    String refundFullAmount(OrderTransactionDTO transRes);

}
