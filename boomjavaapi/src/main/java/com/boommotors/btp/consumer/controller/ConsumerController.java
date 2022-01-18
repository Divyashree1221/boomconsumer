package com.boommotors.btp.consumer.controller;

import com.boommotors.btp.consumer.dao.ConsumerDAO;
import com.boommotors.btp.consumer.dto.ConsumerDetailsDTO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleDetailsDTO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleMappingDTO;
import com.boommotors.btp.consumer.dto.MappedVehiclesDTO;
import com.boommotors.btp.consumer.dto.DashboardDataDTO;
import com.boommotors.btp.consumer.dto.PasskeyDTO;
import com.boommotors.btp.consumer.payload.ConsumerProfileUpdateRequest;
import com.boommotors.btp.consumer.payload.ConsumerchangePasswordRequest;
import com.boommotors.btp.consumer.service.ConsumerService;
import com.boommotors.btp.consumer.payload.ConsumerVehicleMappingRequest;
import com.boommotors.btp.dto.User;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.security.JwtTokenProvider;
import com.boommotors.btp.user.dao.UserDAO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.service.UserService;
import com.boommotors.btp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.boommotors.btp.util.EncryptDecrypt;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author divyashree
 */
@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userRepository;

    @Autowired
    ConsumerDAO consumerRepository;

    @Autowired
    ConsumerService consumerService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    EncryptDecrypt edUtil;

    @Value("${sms.auth.key}")
    private String smsAuthKey;

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ConsumerchangePasswordRequest changePasswordRequest) {
        try {
            if (changePasswordRequest.getUserId().equals("") || changePasswordRequest.getCurrentPassword().equals("")
                    || changePasswordRequest.getNewPassword().equals("") || changePasswordRequest.getUserId() == null
                    || changePasswordRequest.getCurrentPassword() == null
                    || changePasswordRequest.getCurrentPassword() == null) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }

            User res = userService.findById(changePasswordRequest.getUserId());
            if (res != null) {
                String currentPassword = passwordEncoder.encode(changePasswordRequest.getCurrentPassword());

                Boolean checkCurrentPW = passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), res.getPassword());
                System.out.println("Check :  " + checkCurrentPW);

                System.out.println("Current Password : " + changePasswordRequest.getCurrentPassword());
                System.out.println("Current Password Input: " + currentPassword);
                System.out.println("Current Password Table : " + res.getPassword());
                System.out.println("12345678 : " + passwordEncoder.encode("12345678"));

                if (!checkCurrentPW) {
                    return new ResponseEntity(new ApiResponse(false, "Your Current Password doesn't match!", null),
                            HttpStatus.OK);
                }
            }

            boolean result = consumerService.updatePassword(changePasswordRequest);
            if (!result) {
                return new ResponseEntity(new ApiResponse(false, "Password not changed!", null),
                        HttpStatus.OK);
            }
            return new ResponseEntity(new ApiResponse(true, "Password changed successfully.", result),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            //e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/details/{user_id}")
    public ResponseEntity<?> getConsumerDetailsByUserId(@PathVariable(value = "user_id") String userId) {
        List<ConsumerDetailsDTO> res = consumerService.retrieveConsumerDetailsByUserId(userId);
        try {
            if (res.isEmpty() || res == null) {
                return new ResponseEntity(new ApiResponse(false, "No data Found!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Consumers data retrieved successfully", res),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException | NullPointerException e) {
            //e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/vehicle-mapped-data/{order_summary_id}")
    public ResponseEntity<?> getMappedDataByOrderSummaryId(@PathVariable(value = "order_summary_id") String orderSummaryId) {
        ConsumerVehicleMappingDTO res = consumerService.retrieveMappedDataByOrderSummaryId(orderSummaryId);
        try {
            if (res == null) {
                return new ResponseEntity(new ApiResponse(false, "No data Found!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Consumer vehilce mapped data retrieved successfully", res),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            //e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/vehicle-mapping/create")
    public ResponseEntity<?> createConsumerVehicleMapping(@RequestBody ConsumerVehicleMappingRequest request) {
        try {
            if ((!"".equals(request.getDealerId()) && request.getDealerId() != null)
                    && (!"".equals(request.getVariantId()) && request.getVariantId() != null)
                    && (!"".equals(request.getClusterId()) && request.getClusterId() != null)) {

                ConsumerVehicleMappingDTO clusterIdExists = consumerService.clusterIdExists(request);

                if (clusterIdExists != null) {
                    return new ResponseEntity(new ApiResponse(false, "Cluster Id already in use!", null),
                            HttpStatus.OK);
                }

                if (request.getChassisNumber() != null && !"".equals(request.getChassisNumber())) {
                    ConsumerVehicleMappingDTO chassisExists = consumerService.chassisNumberExists(request);
                    if (chassisExists != null) {
                        return new ResponseEntity(new ApiResponse(false, "Chassis number already in use!", null),
                                HttpStatus.OK);
                    }
                }
                if (request.getRegistrationNumber() != null && !"".equals(request.getRegistrationNumber())) {
                    ConsumerVehicleMappingDTO registrationNoExists = consumerService.registrationNumberExists(request);
                    if (registrationNoExists != null) {
                        return new ResponseEntity(new ApiResponse(false, "Registration number already in use!", null),
                                HttpStatus.OK);
                    }
                }

                if (request.getImeiNumber() != null && !"".equals(request.getImeiNumber())) {
                    ConsumerVehicleMappingDTO imeiNoExists = consumerService.imeiNumberExists(request);
                    if (imeiNoExists != null) {
                        return new ResponseEntity(new ApiResponse(false, "IMEI number already in use!", null),
                                HttpStatus.OK);
                    }
                }

                String encryptedEnquiryId = consumerService.createConsumerVehicleMapping(request);

                if (!"".equals(encryptedEnquiryId) && encryptedEnquiryId != null) {
                    if (encryptedEnquiryId.equals("Email Address/Mobile Number already in use!")) {
                        return new ResponseEntity(new ApiResponse(false, encryptedEnquiryId, encryptedEnquiryId),
                                HttpStatus.OK);
                    }
                    if (encryptedEnquiryId.equals("Error occured while updating IMEI NO!")) {
                        return new ResponseEntity(new ApiResponse(false, encryptedEnquiryId, encryptedEnquiryId),
                                HttpStatus.OK);
                    }
                    if (encryptedEnquiryId.equals("Problem occured while creating user!")) {
                        return new ResponseEntity(new ApiResponse(false, encryptedEnquiryId, encryptedEnquiryId),
                                HttpStatus.OK);
                    }
                    if (encryptedEnquiryId.equals("Problem occured while mapping vehicle to user!")) {
                        return new ResponseEntity(new ApiResponse(false, encryptedEnquiryId, encryptedEnquiryId),
                                HttpStatus.OK);
                    }
                    if (encryptedEnquiryId.equals("Problem occured while creating order!")) {
                        return new ResponseEntity(new ApiResponse(false, encryptedEnquiryId, encryptedEnquiryId),
                                HttpStatus.OK);
                    }
                    return new ResponseEntity(new ApiResponse(true, "Consumer vehicle mapped successfully!", encryptedEnquiryId),
                            HttpStatus.OK);
                }
                return new ResponseEntity(new ApiResponse(false, "Problem occured while mapping vehicle to consumer! Try again.", null),
                        HttpStatus.OK);

            }
            return new ResponseEntity(new ApiResponse(false, "Required fields are empty!", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardData() {
        ConsumerVehicleDetailsDTO res = consumerService.retrieveVehicleDetails();
        try {
            if (res == null) {
                return new ResponseEntity(new ApiResponse(false, "No data Found!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Vehilce details data retrieved successfully", res),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            //e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/dashboard/{imei}")
    public ResponseEntity<?> getConsumerMqttLive(@PathVariable(value = "imei") String imei) {
        Long im = Long.parseLong(imei);
        System.out.println("im : " + im);
        DashboardDataDTO res = consumerService.retrieveMqttLiveData(im);
        System.out.println("here 6");
        try {
            System.out.println("here 7");
            if (res == null) {
                System.out.println("here 8");
                return new ResponseEntity(new ApiResponse(false, "No data Found!", null),
                        HttpStatus.OK);
            }
            System.out.println("here 9");
            return new ResponseEntity(new ApiResponse(true, "Dashboard data retrieved successfully", res),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException | NullPointerException e) {
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/passkey/{user_id}/{imei}")
    public ResponseEntity<?> getPasskey(@PathVariable(value = "user_id") String userID,
            @PathVariable(value = "imei") String imei) {
        //Long im = Long.parseLong(imei);
        //MqttLiveDTO res = consumerService.retrieveMqttLiveData(im);
        Long im = Long.parseLong("352913090328285");
        PasskeyDTO res = new PasskeyDTO();
        res.setUserid("P41Zp6WRmdmzlYzf21P1Uw");
        res.setIm(im);
        res.setMac("DB:36:81:88:A6:4E");
        res.setPasskey("147246");

        try {
//            if (res == null) {
//                return new ResponseEntity(new ApiResponse(false, "No data Found!", null),
//                        HttpStatus.OK);
//            }
            return new ResponseEntity(new ApiResponse(true, "Passkey  retrieved successfully", res),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException | NullPointerException e) {
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/mapped-vehilces/{user_id}")
    public ResponseEntity<?> getMappedVehciles(@PathVariable(value = "user_id") String userId) {
        List<MappedVehiclesDTO> res = consumerService.retrieveAllMappedVehiclesByUserId(userId);
        try {
            if (res == null) {
                return new ResponseEntity(new ApiResponse(false, "No data Found!", null),
                        HttpStatus.OK);
            }
            return new ResponseEntity(new ApiResponse(true, "Mapped Vehicles data retrieved successfully", res),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException | NullPointerException e) {
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/profile-update")
    public ResponseEntity<?> updateConsumerUserDetails(@RequestBody ConsumerProfileUpdateRequest consumerProfileUpdateRequest) {
        try {
            if (consumerProfileUpdateRequest.getUserId().equals("") || consumerProfileUpdateRequest.getFirstName().equals("")
                    || consumerProfileUpdateRequest.getLastName().equals("") || consumerProfileUpdateRequest.getUserId() == null
                    || consumerProfileUpdateRequest.getFirstName() == null
                    || consumerProfileUpdateRequest.getLastName() == null) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }

            User res = userService.findById(consumerProfileUpdateRequest.getUserId());
            if (res != null) {
                boolean result = consumerService.updateProfile(consumerProfileUpdateRequest);
                if (!result) {
                    return new ResponseEntity(new ApiResponse(false, "Profile not updated!", null), HttpStatus.OK);
                }
                UsersDTO profileData = userService.retrieveUserById(consumerProfileUpdateRequest.getUserId());
                return new ResponseEntity(new ApiResponse(true, "Profile updated successfully.", profileData),
                        HttpStatus.OK);

            }

            return new ResponseEntity(new ApiResponse(false, "User not found!", null), HttpStatus.OK);
        } catch (ResourceNotFoundException | AppException e) {
            //e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

}
