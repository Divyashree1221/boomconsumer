package com.boommotors.btp.dashboard.controller;

import com.boommotors.btp.dashboard.dto.UserCountDTO;
import com.boommotors.btp.dashboard.service.DashboardService;
import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author divyashree
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;
    

    @Autowired
    DateUtil dateUtil;

    /**
     * Get all models.
     *
     * @return RESULT
     */
    @GetMapping("/userscount")
    public ResponseEntity<?> retrieveCounts() {
        
        UserCountDTO result = dashboardService.retrieveCounts();
        if (result == null) {
            return new ResponseEntity(new ApiResponse(false, "Data not retrieved successfully!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Data retrieved successfully", result),
                HttpStatus.OK);
    }

 
    
}
