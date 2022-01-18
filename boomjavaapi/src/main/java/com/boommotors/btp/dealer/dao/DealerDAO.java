/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.dao;

import com.boommotors.btp.dealer.dto.DealerBankerDTO;
import com.boommotors.btp.dealer.dto.DealerKeyPersonnelDTO;
import com.boommotors.btp.dealer.dto.DealerMasterDTO;
import com.boommotors.btp.dealer.dto.DealerReferencesDTO;
import com.boommotors.btp.dealer.dto.DealershipEnquiryDTO;
import com.boommotors.btp.dealer.dto.HeardFromWhereDTO;
import com.boommotors.btp.dealer.dto.NewDealershipEnquiryDTO;
import com.boommotors.btp.dealer.dto.StateDTO;
import java.util.List;

/**
 *
 * @author Ramya
 */
public interface DealerDAO {

    Long createDealerMaster(DealerMasterDTO data);

    Long createDealerBanker(DealerBankerDTO data);

    Long createDealerKeyPersonnel(DealerKeyPersonnelDTO data);

    Long createDealerReferences(DealerReferencesDTO data);

    DealerMasterDTO fetchDealerDetailsByUserId(Integer userId);

    List<DealerBankerDTO> fetchDealerBankerDetails(Integer dealerId);

    List<DealerKeyPersonnelDTO> fetchDealerPersonnelDetails(Integer dealerId);

    List<DealerReferencesDTO> fetchDealerReferences(Integer dealerId);
    
    Boolean update(DealerMasterDTO data);
    
    Boolean updateDealerBanker(DealerBankerDTO data);
    
    Boolean updateDealerKeyPersonnel(DealerKeyPersonnelDTO data);
    
    Boolean updateDealerReferences(DealerReferencesDTO data);
    
    DealerMasterDTO fetchByDealershipNo(String dealershipNumber);
    
    List<DealerMasterDTO> fetchDealersData(Integer distributorId);
    
    Integer fetchDistributorId(Integer userId);
    
    Integer fetchDistributorIdByState(String stateName);
    
    Boolean updateDealerAdditionalDocs(DealerMasterDTO data);
    
    Boolean updateDealerStatus(DealerMasterDTO data);
    
    List<StateDTO> fetchAllState();
    
    List<HeardFromWhereDTO> fetchHeardFromWhere();
    
    Long createDealerEnquiry(DealershipEnquiryDTO data);
    
    Long createDealershipEnquiry(NewDealershipEnquiryDTO data);
    
}
