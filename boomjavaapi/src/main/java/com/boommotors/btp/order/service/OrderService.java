/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.service;

import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.EVStatusDTO;
import com.boommotors.btp.order.dto.FinanceStatusDTO;
import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.order.dto.OrderSummaryAndRazorpayDTO;
import com.boommotors.btp.order.dto.TransactionStatusDTO;
import com.boommotors.btp.payload.OrderRequest;
import java.util.List;

/**
 *
 * @author NandiniC
 */
public interface OrderService {

    String createOrderSummary(OrderRequest orderRequest) throws EtAuthException;

    List<OrderSummaryAndRazorpayDTO> retrieveOrderSummaryByUserId(String userId);

    List<EVStatusDTO> retrieveOrderSummaryEVStatusByUserIdForStatus(String userId);

    List<FinanceStatusDTO> retrieveOrderSummaryFinanceStatusByUserIdForStatus(String userId);

    List<TransactionStatusDTO> retrieveOrderSummaryTransactionStatusByUserIdForStatus(String userId);

    void createOrderStatus(String encryptedOrderSummaryId, String encryptedUserId) throws EtAuthException;
    
    void createOrderTransactionStatus(String encryptedOrderSummaryId, String encryptedUserId) throws EtAuthException;
    
    Boolean updateOrderSummary(OrderRequest orderRequest);
    
    String orderCancellation(OrderRequest orderRequest);
    
    void autoUpdateOrderTransactionTable();

}
