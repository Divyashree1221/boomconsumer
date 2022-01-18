/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.dao;

import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.payment.dto.PaymentDetailsDTO;

/**
 *
 * @author Ramya
 */
public interface PaymentDAO {

    OrderSummaryDTO fetchOrderSummary(Integer orderSummaryId);

    Long saveOrderDtail(OrderDetailDTO data);

    Long saveOrderTransaction(OrderTransactionDTO data);

    OrderDetailDTO fetchOrderDetailByRazorPayOrderId(String razorpayOrderId);

    boolean updateOrderDetailIsPaid(OrderDetailDTO data);

    PaymentDetailsDTO fetchPaymentDetails(String paymentId);

    OrderTransactionDTO fetchOrderTransaction(String razorpayPaymentId);
    
    boolean updateOrderSummaryAmount(OrderSummaryDTO data);
    

}
