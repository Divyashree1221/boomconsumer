/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.service;

import com.boommotors.btp.consumer.dto.ConsumerDetailsDTO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleDetailsDTO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleMappingDTO;
import com.boommotors.btp.consumer.dto.MappedVehiclesDTO;
import com.boommotors.btp.consumer.dto.DashboardDataDTO;
import com.boommotors.btp.consumer.payload.ConsumerProfileUpdateRequest;
import com.boommotors.btp.consumer.payload.ConsumerchangePasswordRequest;
import com.boommotors.btp.consumer.payload.ConsumerVehicleMappingRequest;
import java.util.List;

/**
 *
 * @author Ramya
 */
public interface ConsumerService {

    Boolean verifyOTP(String encryptedUserId, Integer code);

    List<ConsumerVehicleMappingDTO> retrieveConsumerVehicleMappingData(String encryptedUserId);

    Boolean updateOtpVerified(List<ConsumerVehicleMappingDTO> data);

    Boolean updatePassword(ConsumerchangePasswordRequest consumerchangePasswordRequest);

    List<ConsumerDetailsDTO> retrieveConsumerDetailsByUserId(String userId);

    ConsumerVehicleMappingDTO retrieveMappedDataByOrderSummaryId(String orderSummaryId);

    String createConsumerVehicleMapping(ConsumerVehicleMappingRequest request);

    ConsumerVehicleDetailsDTO retrieveVehicleDetails();

    DashboardDataDTO retrieveMqttLiveData(Long imei);

    ConsumerVehicleMappingDTO clusterIdExists(ConsumerVehicleMappingRequest req);

    ConsumerVehicleMappingDTO chassisNumberExists(ConsumerVehicleMappingRequest req);

    ConsumerVehicleMappingDTO imeiNumberExists(ConsumerVehicleMappingRequest req);

    ConsumerVehicleMappingDTO registrationNumberExists(ConsumerVehicleMappingRequest req);
    
    List<MappedVehiclesDTO> retrieveAllMappedVehiclesByUserId(String encruptedUserId);
    
    Boolean updateProfile(ConsumerProfileUpdateRequest consumerProfileUpdateRequest);

}
