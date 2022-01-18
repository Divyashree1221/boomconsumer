/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.service;

import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.BulkEnquiryDTO;
import com.boommotors.btp.order.dto.BulkEnquiryDetailsDTO;
import com.boommotors.btp.order.dto.BulkEnquirySuccessDTO;
import com.boommotors.btp.payload.OrderRequest;
import java.util.List;

/**
 *
 * @author divyashree
 */
public interface BulkOrderService {

    String createBulkEnquiry(OrderRequest orderRequest) throws EtAuthException;

    BulkEnquirySuccessDTO FetchBulkEnquiryByID(String encryptedBulkEnquiryID) throws EtAuthException;
    
    List<BulkEnquiryDetailsDTO> retrieveBulkEnquiryUserID(String encryptedUserID);

}
