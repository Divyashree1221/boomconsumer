/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.service;

import com.boommotors.btp.dto.User;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.payload.OtpRequest;
import com.boommotors.btp.payload.SigninRequest;
import com.boommotors.btp.user.dto.UserOrderDetailsDTO;
import com.boommotors.btp.user.dto.UserRoleDTO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.payload.EditProfileReuest;
import java.util.List;

/**
 *
 * @author divyashree
 */
public interface UserService {

    String registerUser(User user) throws EtAuthException;

    //  Boolean resetPassword(ResetPasswordRequest resetPasswordRq);
    UsersDTO retrieveUserByEmail(String email);

    User fetchUserByEmail(String email);

    UsersDTO retrieveUserByMobNo(String mobno);

    UsersDTO retrieveUserByEmailOrMobNo(String email, String mobno);

    User findById(String id);

    UsersDTO retrieveUserById(String id);

    List<UsersDTO> retrieveAllUsers();

    Boolean updateUserOtp(OtpRequest otpRequest);

    Boolean isUserAllowedToLogin(SigninRequest signinRequest);

    Boolean validateUser(SigninRequest signinRequest);

    Boolean generateOneTimePassword(String mobileNoOrEmail, Integer count);

    Boolean verify(String mobileNoOrEmail, Integer code);

    Boolean sendEmail(UsersDTO userData);

    Boolean updateEmailVerificationStatus(UsersDTO userData);

    Boolean updateProfile(EditProfileReuest editProfileReuest);

    Boolean verify_Email_Otp(String email, Integer emailOtp);

    List<UserRoleDTO> retrieveUserRoleByUserType();

    List<UsersDTO> retrieveAllConsumers();

    UsersDTO getUserDataByEmailOrMob(String emailormob);

    List<UserOrderDetailsDTO> retrieveUserOrderedDataByUserId(String userId);
    
    void increaseFailedAttempts(UsersDTO user);

    Boolean unlockWhenTimeExpired(UsersDTO user);
    
    void userLock(UsersDTO user);
}
