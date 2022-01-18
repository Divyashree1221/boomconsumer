/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.dao;

import com.boommotors.btp.consumer.dto.ConsumerDetailsDTO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleMappingDTO;
import com.boommotors.btp.consumer.dto.MappedVehiclesDTO;
import com.boommotors.btp.consumer.dto.DashboardDataDTO;
import com.boommotors.btp.consumer.dto.MqttLiveDTO;
import com.boommotors.btp.consumer.dto.MqttLoginDTO;
import com.boommotors.btp.consumer.payload.ConsumerProfileUpdateRequest;
import com.boommotors.btp.consumer.payload.ConsumerchangePasswordRequest;
import java.util.List;

/**
 *
 * @author Ramya
 */
public interface ConsumerDAO {

    List<ConsumerVehicleMappingDTO> fetchIsFirstTimeUser(Integer userId);

    String validateOTP(Integer userId, Integer otp);

    Boolean updateOtpVerified(ConsumerVehicleMappingDTO data);

    Boolean updatePassword(ConsumerchangePasswordRequest data);

    List<ConsumerDetailsDTO> fetchConsumerDetailsByUserId(Integer userId);

    Boolean existsByUserId(Integer userId);

    ConsumerVehicleMappingDTO fetchMappedDataByOrderSummaryId(Integer summaryId);

    Long createConsumerVehicleMapping(ConsumerVehicleMappingDTO data);

    ConsumerVehicleMappingDTO fetchMappedDataByUserId(Integer userId);

    Boolean updateConsumerVehicleMapping(ConsumerVehicleMappingDTO data);

    ConsumerVehicleMappingDTO clusterIdExistsForEdit(String clusterId, Integer summaryId);

    ConsumerVehicleMappingDTO clusterIdExistsForAdd(String clusterId);

    ConsumerVehicleMappingDTO chassisNumberExistsForEdit(String chassisNo, Integer summaryId);

    ConsumerVehicleMappingDTO chassisNumberExistsForAdd(String chassisNo);

    ConsumerVehicleMappingDTO imeiNumberExistsForEdit(String imeiNo, Integer summaryId);

    ConsumerVehicleMappingDTO imeiNumberExistsForAdd(String imeiNo);

    ConsumerVehicleMappingDTO registrationNumberExistsForAdd(String registrationNo);

    ConsumerVehicleMappingDTO registrationNumberExistsForEdit(String registrationNo, Integer summaryId);

    DashboardDataDTO fetchMqttLiveData(Long imei);
    
    List<MqttLoginDTO> fetchMqttLoginData(Long imei);
    
    List<MappedVehiclesDTO> fetchAllMappedVehiclesByUserId(Integer userId);
    
    MqttLiveDTO fetchSOC(Long imei);
    
    Boolean updateProfile(ConsumerProfileUpdateRequest data);

}
