/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dashboard.service;

import com.boommotors.btp.dashboard.dto.UserCountDTO;

/**
 *
 * @author Ramya
 */
public interface DashboardService {
    
    UserCountDTO retrieveCounts();
    
}
