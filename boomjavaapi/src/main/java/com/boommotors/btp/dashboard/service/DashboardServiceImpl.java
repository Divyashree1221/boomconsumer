/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dashboard.service;

import com.boommotors.btp.dashboard.dao.DashboardDAO;
import com.boommotors.btp.dashboard.dto.CountDTO;
import com.boommotors.btp.dashboard.dto.UserCountDTO;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ramya
 */
@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    DashboardDAO dashboardRepository;

    @Override
    public UserCountDTO retrieveCounts() {
        try {
            CountDTO userCount = dashboardRepository.fetchUserCount();
            
            CountDTO verifiedUserCount = dashboardRepository.fetchVerifiedUserCount();
            
            CountDTO dealershipEnquiryCount = dashboardRepository.fetchDealershipEnquiryCount();
            
            CountDTO bookingsCount = dashboardRepository.fetchBookingsCount();
            
            CountDTO bulkEnquiryCount = dashboardRepository.fetchBulkEnquiryCount();
            
            CountDTO qtyBulkEnquiry = dashboardRepository.fetchQtyBulkEnquiryCount();
            
            CountDTO cancelledCount = dashboardRepository.fetchOrderCancelledCount();
                       
            UserCountDTO result = new UserCountDTO();
            result.setUserCount(userCount.getCount());
            result.setVerifiedUserCount(verifiedUserCount.getCount());
            result.setDealershipEnquiryCount(dealershipEnquiryCount.getCount());
            result.setBookingsCount(bookingsCount.getCount());
            result.setBulkEnquiryCount(bulkEnquiryCount.getCount());
            result.setBulkOrderQuantity(qtyBulkEnquiry.getCount());
            result.setCancelledCount(cancelledCount.getCount());
            
            
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

}
