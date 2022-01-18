/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.dao;

import com.boommotors.btp.dto.User;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.user.dto.UserOrderDetailsDTO;
import com.boommotors.btp.user.dto.UserRoleDTO;
import com.boommotors.btp.user.dto.UsersDTO;
import java.util.List;

/**
 *
 * @author divyashree
 */
public interface UserDAO {

    Long create(User user) throws EtAuthException;

//    Boolean updatePassword(UsersDTO data);
    Integer getCountByEmail(String email);

    User findById(Integer userId);

    UsersDTO retrieveUserById(Integer userId);

    UsersDTO fetchUserByEmail(String email);

    User fetchByEmail(String mobNo);

    UsersDTO fetchUserByMobNo(String mobno);

    UsersDTO fetchUserByEmailOrMobno(String mobileNoOrEmail);

    UsersDTO fetchUsersByEmailOrMobno(String email, String mobno);

    List<UsersDTO> findAllByMobileNo(String mobileNo);

    List<UsersDTO> retrieveAllUsers();

    Boolean updateUserOtp(UsersDTO userdto);

    String validateOTP(String mobileNoOrEmail, Integer otp);

    Boolean deleteOtp(Long id, UsersDTO data);

    Boolean updateOTPVerificationStatus(UsersDTO data);

    Boolean updateEmailVerificationStatus(UsersDTO data);

    Boolean updateProfile(UsersDTO data);

    Boolean updateUserEmailOtp(UsersDTO userdto);

    String verify_Email_Otp(String email, Integer emailOtp);

    List<UserRoleDTO> fetchUserRoleByUserType(String roleType);

    User fetchUserByEmailForConsumerApp(String email);

    List<UsersDTO> fetchAllConsumers();

    List<UserOrderDetailsDTO> fetchUserOrderedDataByUserId(Integer userId);
    
    Boolean updateUserInfo(UsersDTO data);
    
    Boolean updateFailedAttempts(UsersDTO data);
}
