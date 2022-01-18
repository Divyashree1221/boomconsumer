/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.service;

import com.boommotors.btp.dealer.dto.DealerMasterDTO;
import com.boommotors.btp.dealer.dto.HeardFromWhereDTO;
import com.boommotors.btp.dealer.dto.StateDTO;
import com.boommotors.btp.consumer.payload.ConsumerVehicleMappingRequest;
import com.boommotors.btp.dealer.payload.DealerMasterRequest;
import com.boommotors.btp.dealer.payload.DealershipEnquiryRequest;
import com.boommotors.btp.dealer.payload.NewDealershipEnquiryRequest;
import java.util.List;

/**
 *
 * @author Ramya
 */
public interface DealerService {

    String createDealerMaster(DealerMasterRequest dealerRequest);
    
    DealerMasterDTO retrieveDealerDetails(String encryptedUserId);
    
    Boolean update(DealerMasterRequest dealerRequest);
    
    DealerMasterDTO retrieveByDealershipNo(String dealershipNumber);
    
    List<DealerMasterDTO> retrieveDealersData(String encryptedUserId);
    
    Boolean updateDealerMaster(DealerMasterRequest dealerRequest);
    
    List<StateDTO> retrieveAllState();
    
    List<HeardFromWhereDTO> retrieveHeardFromWhere();
    
    String createDealerEnquiry(DealershipEnquiryRequest enquiryRequest);
    
    String createDealershipEnquiry(NewDealershipEnquiryRequest enquiryRequest);
    
 }
