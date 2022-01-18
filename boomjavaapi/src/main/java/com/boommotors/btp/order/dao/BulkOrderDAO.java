/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dao;

import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.BulkEnquiryAccessoriesDTO;
import com.boommotors.btp.order.dto.BulkEnquiryDTO;
import com.boommotors.btp.order.dto.BulkEnquiryDetailsDTO;
import com.boommotors.btp.order.dto.BulkEnquiryPartsColourDTO;
import com.boommotors.btp.order.dto.BulkEnquirySuccessDTO;
import com.boommotors.btp.order.dto.ModelAccessoriesDetailsDTO;
import com.boommotors.btp.order.dto.VariantPartsColourDetailsDTO;
import java.util.List;

/**
 *
 * @author divyashree
 */
public interface BulkOrderDAO {

    Long createBulkEnquiry(BulkEnquiryDTO bulkEnquiry) throws EtAuthException;

    Long createBulkEnquiryPartColour(BulkEnquiryPartsColourDTO enquiryPartsColour) throws EtAuthException;

    Long createBulkEnquiryAccessories(BulkEnquiryAccessoriesDTO enquiryAccessories) throws EtAuthException;
    
    boolean updateBulkEnquiry(BulkEnquiryDTO bulkEnquiry);
    
    BulkEnquirySuccessDTO FetchBulkEnquiryByID(String encryptedBulkEnquiryID) throws EtAuthException;
    
    List<BulkEnquiryDetailsDTO> fetchBulkEnquiryByUserID(int userId) throws EtAuthException;
    
    List<ModelAccessoriesDetailsDTO> fetchModelAccessories(int bulkEnquiryId);
    
    List<VariantPartsColourDetailsDTO> fetchPartsColourDetails(int bulkEnquiryId);
}
