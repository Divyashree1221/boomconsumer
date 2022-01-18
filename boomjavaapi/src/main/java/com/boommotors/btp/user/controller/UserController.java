/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.controller;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.payload.EditProfileReuest;
import com.boommotors.btp.user.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.boommotors.btp.payload.EmailOtpRequest;
import com.boommotors.btp.payload.SigninRequest;
import com.boommotors.btp.user.dto.UserOrderDetailsDTO;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

/**
 *
 * @author divyashree
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getUserDetails(@PathVariable(value = "email") String email) {
        UsersDTO user = userService.retrieveUserByEmail(email);

        if (user == null) {
            return new ResponseEntity(new ApiResponse(false, "Profile not found!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Profile found!", user),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<UsersDTO> user = userService.retrieveAllUsers();

        if (user.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, "Users not retrieved successfully!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Users retrieved successfully", user),
                HttpStatus.OK);
    }

    @GetMapping("/byid/{user_id}")
    public ResponseEntity<?> retrieveUserById(@PathVariable(value = "user_id") String userId) {
        UsersDTO user = userService.retrieveUserById(userId);

        if (user == null) {
            return new ResponseEntity(new ApiResponse(false, "Users not retrieved successfully!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Users retrieved successfully", user),
                HttpStatus.OK);
    }

    @GetMapping("/bymobile/{mobile_no}")
    public ResponseEntity<?> retrieveByMobNo(@PathVariable(value = "mobile_no") String mobno) {
        UsersDTO result = userService.retrieveUserByMobNo(mobno);
        if (result == null) {
            return new ResponseEntity(new ApiResponse(false, "No record found!", null),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new ApiResponse(true, "Record found", result),
                HttpStatus.OK);
    }

    @PostMapping("/update/profile")
    public ResponseEntity<?> updateProfile(@RequestBody EditProfileReuest editProfileReuest) {
        try {

            UsersDTO user = userService.retrieveUserById(editProfileReuest.getUserId());

            if (user == null) {
                return new ResponseEntity(new ApiResponse(false, "Profile not found!", null),
                        HttpStatus.OK);
            }

            Boolean updateProfile = userService.updateProfile(editProfileReuest);

            if (!updateProfile) {
                return new ResponseEntity(new ApiResponse(false, "Something went wrong!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Profile Updated Successfully", updateProfile),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }

    }

    @PostMapping("/sendEmailOtp")
    public ResponseEntity<?> sendEmailOtp(@RequestBody EmailOtpRequest emailOtpRequest) {
        try {

            if (emailOtpRequest.getUserId().equals("") || emailOtpRequest.getEmailId().equals("") || emailOtpRequest.getUserId() == null || emailOtpRequest.getEmailId() == null) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }

            UsersDTO user = userService.retrieveUserById(emailOtpRequest.getUserId());
            user.setEmailId(emailOtpRequest.getEmailId());
            if (user == null) {
                return new ResponseEntity(new ApiResponse(false, "User not found!", null),
                        HttpStatus.OK);
            }
            Boolean isEmailSent = userService.sendEmail(user);
            if (!isEmailSent) {
                return new ResponseEntity(new ApiResponse(false, "Problem occured while sending email, Please try again", null),
                        HttpStatus.OK);

            }
            return new ResponseEntity(new ApiResponse(true, "OTP sent to the registered Email Id..", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/verifyEmailOtp")
    public ResponseEntity<?> verifyEmailOtp(@RequestBody EmailOtpRequest emailOtpRequest) {
        try {
            if (emailOtpRequest.getEmailId().equals("") || emailOtpRequest.getEmailId() == null || emailOtpRequest.getEmailOtp() == null || emailOtpRequest.getEmailOtp().equals("")) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }

            Boolean isEmailVerified = userService.verify_Email_Otp(emailOtpRequest.getEmailId(), emailOtpRequest.getEmailOtp());

            if (!isEmailVerified) {

                return new ResponseEntity(new ApiResponse(false, "Invalid Email OTP!", null),
                        HttpStatus.OK);

            }
            return new ResponseEntity(new ApiResponse(true, "OTP verified successfully!", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/updateEmailStatus")
    public ResponseEntity<?> updateEmailStatus(@RequestBody SigninRequest signinRequest) {
        try {
            boolean isUserAllowedToLogin = userService.validateUser(signinRequest);

            if (isUserAllowedToLogin) {
                UsersDTO result = userService.retrieveUserByMobNo(signinRequest.getMobileoremail());

                boolean isStatusUpdated = userService.updateEmailVerificationStatus(result);
                if (!isStatusUpdated) {
                    return new ResponseEntity(new ApiResponse(false, "Problem occured while updating the status", null),
                            HttpStatus.OK);
                }
                return new ResponseEntity(new ApiResponse(true, "Updated the email status Sucessfully", ""),
                        HttpStatus.OK);
            } else {

                return new ResponseEntity(new ApiResponse(false, "Access denied!", null),//Change the message based on the requi
                        HttpStatus.UNAUTHORIZED);
            }
        } catch (ResourceNotFoundException | AppException e) {
            //  return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
            printStackTrace(e);
            return new ResponseEntity(new ApiResponse(false, "Error", e),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/consumer/all")
    public ResponseEntity<?> getAllConsumers() {
        List<UsersDTO> user = userService.retrieveAllConsumers();

        if (user.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, "Consumers data not retrieved successfully!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Consumers data retrieved successfully", user),
                HttpStatus.OK);
    }

    @GetMapping("/byemailormobile/{email_or_mobile}")
    public ResponseEntity<?> getUserDataByEmailOrMob(@PathVariable(value = "email_or_mobile") String emailormobile) {
        UsersDTO user = userService.getUserDataByEmailOrMob(emailormobile);
        try {
            if (user == null) {
                return new ResponseEntity(new ApiResponse(false, "User not found!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "User found!", user),
                    HttpStatus.OK);
        } catch (ResourceNotFoundException | AppException e) {
            printStackTrace(e);
            return new ResponseEntity(new ApiResponse(false, "Error", e),
                    HttpStatus.OK);
        }
    }
    
     @GetMapping("/query-system-dashboard-data/{user_id}")
    public ResponseEntity<?> getUserOrderedDataByUserId(@PathVariable(value = "user_id") String userId) {
        List<UserOrderDetailsDTO> data = userService.retrieveUserOrderedDataByUserId(userId);
        try {
            if (data.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "User data not found!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "User data found.", data),
                    HttpStatus.OK);
        } catch (ResourceNotFoundException | AppException e) {
            printStackTrace(e);
            return new ResponseEntity(new ApiResponse(false, "Error", e),
                    HttpStatus.OK);
        }
    }

}
