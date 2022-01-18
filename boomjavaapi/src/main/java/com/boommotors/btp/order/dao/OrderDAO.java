/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dao;

import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.OrderAccessoriesDTO;
import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.order.dto.OrderEVStatusDTO;
import com.boommotors.btp.order.dto.OrderFinanceStatusDTO;
import com.boommotors.btp.order.dto.OrderPartsColourDTO;
import com.boommotors.btp.order.dto.OrderPartsImgUrlDTO;
import com.boommotors.btp.order.dto.OrderSummaryAndRazorpayDTO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.order.dto.OrderTransactionStatusDTO;
import com.boommotors.btp.order.dto.TransactionDetailsDTO;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author NandiniC
 */
public interface OrderDAO {

    Long createOrderSummary(OrderSummaryDTO orderSummary) throws EtAuthException;

    Long createOrderPartColour(OrderPartsColourDTO orderPartsColour) throws EtAuthException;

    Long createOrderAccessories(OrderAccessoriesDTO orderAccessories) throws EtAuthException;

    List<OrderSummaryAndRazorpayDTO> fetchOrderSummaryByUserId(Integer userId);

    List<OrderSummaryDTO> fetchOrderSummaryByUserIdForStatus(Integer userId);

    List<OrderEVStatusDTO> fetchOrderEVStatus(Integer orderSummaryId);

    List<OrderFinanceStatusDTO> fetchOrderFinanceStatus(Integer orderSummaryId);

    List<OrderTransactionStatusDTO> fetchOrderTransactionStatus(Integer orderSummaryId);

    Long createOrderEvStatus(OrderEVStatusDTO orderEVStatusDTO) throws EtAuthException;

    Long createOrderFinanceStatus(OrderFinanceStatusDTO  orderFinanceStatusDTO) throws EtAuthException;

    Long createOrderTransactionStatus(OrderTransactionStatusDTO orderTransactionStatusDTO) throws EtAuthException;
    
    OrderSummaryDTO fetchOrderDetailsBySummaryId(Integer orderSummaryId);
        
    List<OrderPartsImgUrlDTO> getOrderPartsImageUrls(Integer summaryId);
    
    List<TransactionDetailsDTO> fetchPaymentDetails(Integer summaryId);
    
    Boolean updateOrderSummary(OrderSummaryDTO data);
    
    OrderTransactionDTO fetchOrderTransaction(Integer orderSummaryId);
    
    Boolean updateOrderTransaction(OrderTransactionDTO data);
    
    Boolean updateConsumerOrderDetails(OrderSummaryDTO data);
    
    List<OrderDetailDTO> fetchFailedOrderDetails(Timestamp fromDate, Timestamp toDate);

}
