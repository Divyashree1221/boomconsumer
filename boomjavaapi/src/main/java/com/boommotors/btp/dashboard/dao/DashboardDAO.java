/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dashboard.dao;

import com.boommotors.btp.dashboard.dto.CountDTO;

/**
 *
 * @author Ramya
 */
public interface DashboardDAO {
    
    CountDTO fetchUserCount();
    
    CountDTO fetchVerifiedUserCount();
    
    CountDTO fetchDealershipEnquiryCount();
    
    CountDTO fetchBookingsCount();
    
    CountDTO fetchBulkEnquiryCount();
    
    CountDTO fetchQtyBulkEnquiryCount();
    
    CountDTO fetchOrderCancelledCount();
    
    
}
