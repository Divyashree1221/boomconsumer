/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.service;

import com.boommotors.btp.user.dao.UserDAO;
import com.boommotors.btp.dto.User;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.payload.OtpRequest;
import com.boommotors.btp.payload.SigninRequest;
import com.boommotors.btp.user.dao.RolesDAO;
import com.boommotors.btp.user.dto.RoleAccessDTO;
import com.boommotors.btp.user.dto.UserOrderDetailsDTO;
import com.boommotors.btp.user.dto.UserRoleDTO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.payload.EditProfileReuest;
import com.boommotors.btp.util.DateDAO;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import com.mahalakshmi.smt.exception.BadRequestException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author divyashree
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userRepository;

    @Autowired
    RolesDAO roleRepository;

    @Autowired
    DateDAO dateRepository;

    @Autowired
    SMSService smsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    EmailService emailService;

    //SMS values
    @Value("${sms.sender.id}")
    private String smsSenderId;

    @Value("${sms.route}")
    private int smsRoute;

    @Value("${sms.templet.id}")
    private String smsTempletId;

    @Value("${sms.flow.id}")
    private String smsflowId;
//============================
    //EMAIL values

    @Value("${email.url}")
    private String emailURL;

    @Value("${email.FromEmailId}")
    private String emailFromMailID;

    @Value("${email.Otptemplate}")
    private String emailOtptemplate;

    @Value("${email.auth.key}")
    private String emailAuthKey;

    @Value("${email.FromName}")
    private String emailFromName;

    @Value("${email.ConsumerEmailTemplate}")
    private String consumerEmailTemplate;

    @Value("${email.DealerEmailTemplate}")
    private String dealerEmailTemplate;

    //==================================== 
//    @Value("${login.LOCK_TIME_DURATION}")
//    private Integer lockTimeDuration;
//
//    @Value("${login.MAX_FAILED_ATTEMPTS}")
//    private Long maxFailedAttempts;
    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    private static final long LAST_LOCK_TIME_DURATION = 30 * 60 * 1000; // 30 minutes

    @Override
    public String registerUser(User user) throws EtAuthException {
        try {
            String email = null;
            Pattern pattern = Pattern.compile("^(.+)@(.+)$");
            System.out.println("Pattern : " + pattern);
            if (user.getEmail() != null) {
                email = user.getEmail().toLowerCase();
            }
            if (!pattern.matcher(email).matches()) {
                throw new EtAuthException("Invalid email format");
            }
            Integer count = userRepository.getCountByEmail(email);

            if (count > 0) {
                throw new EtAuthException("Email already in use");
            }
            //create user
            long userId = userRepository.create(user);

            String encryptedId = edUtil.encrypt(String.valueOf(userId));

            return encryptedId;
            // return userRepository.findById(userId);
        } catch (AppException | EtAuthException ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public UsersDTO retrieveUserByEmail(String email) {
        UsersDTO res = null;
        try {
            res = userRepository.fetchUserByEmail(email);
            return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public UsersDTO retrieveUserByEmailOrMobNo(String email, String mobno) {
        UsersDTO res = null;
        try {
            res = userRepository.fetchUsersByEmailOrMobno(email, mobno);
            return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public User fetchUserByEmail(String email) {
        User res = null;
        try {
            res = userRepository.fetchByEmail(email);
            return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public UsersDTO retrieveUserByMobNo(String mobno) {
        UsersDTO res = null;
        try {
            res = userRepository.fetchUserByMobNo(mobno);
            return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public User findById(String encryptedId) {
        int id = Integer.parseInt(edUtil.decrypt(encryptedId));
        try {
            return userRepository.findById(id);
        } catch (Exception ex) {
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public UsersDTO retrieveUserById(String encryptedId) {
        Integer id = Integer.parseInt(edUtil.decrypt(encryptedId));

        try {
            return userRepository.retrieveUserById(id);
        } catch (Exception ex) {
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<UsersDTO> retrieveAllUsers() {
        try {
            return userRepository.retrieveAllUsers();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    @Override
    public Boolean updateUserOtp(OtpRequest otpRequest) {
        try {
            UsersDTO userdto = new UsersDTO();
            userdto.setLoginOtp(otpRequest.getLoginotp());
            userdto.setUserId(otpRequest.getUserId());
            userdto.setMobileNumber(otpRequest.getMobileNumber());
            return userRepository.updateUserOtp(userdto);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    @Override
    public Boolean isUserAllowedToLogin(SigninRequest signinRequest) {
        List<Integer> allowedRoleIds = new ArrayList();
        if (null != signinRequest.getSource()) {
            List<RoleAccessDTO> sourceRoles = roleRepository.findAllRolesBySource(signinRequest.getSource());
            if (sourceRoles != null & sourceRoles.size() > 0) {
                sourceRoles.forEach((role) -> {
                    allowedRoleIds.add(role.getRoleId());
                });
            }
        }
        List<UsersDTO> userData = userRepository.findAllByMobileNo(signinRequest.getMobileoremail());
        return userData.stream().anyMatch(element -> allowedRoleIds.contains(element.getUserRoleId().intValue()));
    }

    @Override
    public Boolean validateUser(SigninRequest signinRequest) {
        List<UsersDTO> userData = userRepository.findAllByMobileNo(signinRequest.getMobileoremail());
        return userData.size() > 0;
    }

    @Override
    public Boolean generateOneTimePassword(String mobileNo, Integer count) {
        try {
            String isSMSSent = "";
            String isTextLocalSMSSent = "";
            String isVoiceCallSent = "";
            List<UsersDTO> user = userRepository.findAllByMobileNo(mobileNo);//For single user change this line Ex: UsersDTo user = userRepository.findByMobileNo(mobileNo); below this If(user!=null)
            //Generate Random OTP
            String otp = smsService.generate();

            Boolean isOTPUpdated = false;
            /**
             * UPDATE TO USER TBL
             *
             */
            UsersDTO data = new UsersDTO();
            data.setUserId(String.valueOf(user.get(0).getUserId()));//For single user change this line, remove get(0)
            data.setLoginOtp(Integer.parseInt(otp));
            //data.setOtpVerified(0);
            //data.setStatus(0);
            data.setMobileNumber(mobileNo);
            data.setUpdatedDate(dateUtil.getTimestamp());
            String userId = edUtil.encrypt(String.valueOf(user.get(0).getUserId()));//For single user change this line, remove get(0)
            data.setUpdatedBy(userId);

            isOTPUpdated = userRepository.updateUserOtp(data);

            Timestamp dateNow = dateUtil.getTimestamptoSendSMS();
            String istDate = dateNow.toString().trim().substring(0, 19);
            System.out.println("date String : " + istDate);
            if (isOTPUpdated) {

                System.out.println("OTP : " + otp);

                //Send OTP to user mobile
                String jsonInputString = "{\n"
                        + "  \"flow_id\": \"" + smsflowId + "\",\n"
                        + "  \"sender\": \"" + smsSenderId + "\",\n"
                        + "  \"mobiles\":\"91" + mobileNo + "\",\n"
                        + "  \"VAR1\": \"" + otp + "\",\n"
                        + "  \"VAR2\": \"" + istDate + "\"\n"
                        + "}";
                System.out.println("OTP" + jsonInputString);

                System.out.println("Date and Time : " + dateUtil.getTimestamp());

                switch (count) {
                    case 0:
                        //MSG91 SMS
                        isSMSSent = smsService.send(user.get(0).getMobileNumber(), jsonInputString);
                        break;
                    case 1:
                        //Text Local
                        isTextLocalSMSSent = smsService.sendTextLocalSMS(user.get(0).getMobileNumber(), otp);
                        break;
                    case 2:
                        /////MSG91 Voice call
                        //isSMSSent = smsService.send(user.get(0).getMobileNumber(), jsonInputString);
                        System.out.println("Control is here........");
                        isVoiceCallSent = smsService.sendVoiceCall(user.get(0).getMobileNumber(), otp);
                        break;
                    default:
                        if (count % 2 == 0) {
                            isSMSSent = smsService.send(user.get(0).getMobileNumber(), jsonInputString);
                        } else {
                            isTextLocalSMSSent = smsService.sendTextLocalSMS(user.get(0).getMobileNumber(), otp);
                        }
                        break;
                }

            } else {
                throw new BadRequestException("Oops! An error occurred");
            }
            System.out.println("isSMSSent " + isSMSSent + " isTextLocalSMSSent " + isTextLocalSMSSent);
            return isSMSSent.equalsIgnoreCase("success") || "success".equalsIgnoreCase(isTextLocalSMSSent)
                    || isVoiceCallSent.equalsIgnoreCase("success");
        } catch (AppException | BadRequestException | NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param mobileNoOrEmail
     * @param page (1: Login, 2: forgot password)
     * @param userId
     * @param code
     * @return
     */
    @Override
    public Boolean verify(String mobileNoOrEmail, Integer code) {
        String result = "";

        result = userRepository.validateOTP(mobileNoOrEmail, code);

        switch (result) {
            case "valid":
                UsersDTO userData = userRepository.fetchUserByEmailOrMobno(mobileNoOrEmail);
                //    System.out.println("userData::Id::"+userData.getUserId());
                //    System.out.println("userData::Id:deryted:::"+edUtil.decrypt(userData.getUserId()));
                UsersDTO data = new UsersDTO();
                data.setUserId(userData.getUserId());
                data.setMobile_verified(true);
                data.setUpdatedDate(dateUtil.getTimestamp());
                data.setUpdatedBy(edUtil.decrypt(userData.getUserId()));

                if (!userData.getMobile_verified()) {
                    userRepository.updateOTPVerificationStatus(data);
                }

                //IF valid delete the OTP
                userRepository.deleteOtp(Long.getLong(edUtil.decrypt(userData.getUserId())), data);

                return true;
            case "invalid":
                return false;
            case "not_found":
                throw new BadRequestException("Not found!");
            case "exception":
                throw new BadRequestException("Oops! An error occurred");
            default:
                return false;
        }
    }

    @Override
    public Boolean updateProfile(EditProfileReuest editProfileReuest) {
        UsersDTO data = new UsersDTO();
        data.setUserId(editProfileReuest.getUserId());
        data.setFirstName(editProfileReuest.getFirstName());
        data.setLastName(editProfileReuest.getLastName());

        return userRepository.updateProfile(data);

    }

    @Override
    public Boolean sendEmail(UsersDTO userData) {
        String isEmailSent;

        //Generate Random OTP
        String otp = emailService.generate();

        Boolean isEmailUpdated = false;
        /**
         * UPDATE TO USER TBL
         *
         */

        UsersDTO data = new UsersDTO();
        data.setUserId(String.valueOf(userData.getUserId()));//For single user change this line, remove get(0)
        data.setEmailId(userData.getEmailId());
        data.setEmailVerified(false);
        data.setEmailOtp(Integer.parseInt(otp));
        data.setMobileNumber(userData.getMobileNumber());
        data.setUpdatedDate(dateUtil.getTimestamp());
        String userId = edUtil.encrypt(String.valueOf(userData.getUserId()));//For single user change this line, remove get(0)
        data.setUpdatedBy(userId);
        //update otp to the user table  
        isEmailUpdated = userRepository.updateUserEmailOtp(data);

        if (isEmailUpdated) {

            Timestamp dateNow = dateUtil.getTimestamptoSendSMS();
            String istDate = dateNow.toString().trim().substring(0, 19);
            System.out.println("date String : " + istDate);
            System.out.println("Lastname : " + userData.getLastName());
            String lastname = "";
            String fullName = "";
            if (userData.getLastName() != null) {
                lastname = userData.getLastName();
                fullName = userData.getFirstName() + " " + lastname;
            } else {
                fullName = userData.getFirstName();
            }

            System.out.println("lastname : " + lastname);

            String jsonInputString = "{\n"
                    + "  \"to\": [\n"
                    + "    {\n"
                    + "      \"name\": \"" + userData.getFirstName() + "\",\n"
                    + "      \"email\": \"" + userData.getEmailId() + "\"\n"
                    + "    }]\n"
                    + "  ,\n"
                    + "  \"from\": {\n"
                    + "    \"name\": \"" + emailFromName + "\",\n"
                    + "    \"email\": \"" + emailFromMailID + "\"\n"
                    + "  },\n"
                    + "  \"mail_type_id\": \"1\",\n"
                    + "  \"template_id\": \"" + emailOtptemplate + "\",\n"
                    + "  \"variables\": {\n"
                    + "    \"VAR7\": \"" + fullName + "\",\n"
                    //                    + "	\"VAR8\": \"" + lastname + "\",\n"
                    + "	\"VAR9\": \"" + userData.getMobileNumber() + "\",\n"
                    + "    \"VAR2\": \"" + istDate + "\",\n"
                    + "	\"VAR15\":\"" + otp + "\"\n"
                    + "  },\n"
                    + "  \"authkey\": \"" + emailAuthKey + "\"\n"
                    + "}";

            isEmailSent = emailService.sendEmail(jsonInputString);
        } else {
            throw new BadRequestException("Oops! An error occurred");
        }

        if (isEmailSent.equalsIgnoreCase("success")) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean verify_Email_Otp(String email, Integer emailOtp) {
        String result = "";

        result = userRepository.verify_Email_Otp(email, emailOtp);

        switch (result) {
            case "valid":
                UsersDTO userData = userRepository.fetchUserByEmailOrMobno(email);
                UsersDTO data = new UsersDTO();
                data.setUserId(userData.getUserId());
                data.setEmailVerified(true);
                data.setUpdatedDate(dateUtil.getTimestamp());
                data.setUpdatedBy(edUtil.decrypt(userData.getUserId()));

                userRepository.updateEmailVerificationStatus(data);

                return true;
            case "invalid":
                return false;
            case "not_found":
                throw new BadRequestException("Not found!");
            case "exception":
                throw new BadRequestException("Oops! An error occurred");
            default:
                return false;
        }
    }

    @Override
    public Boolean updateEmailVerificationStatus(UsersDTO userData) {

        UsersDTO data = new UsersDTO();
        data.setUserId(userData.getUserId());
        data.setEmailVerified(true);
        data.setUpdatedDate(dateUtil.getTimestamp());
        data.setUpdatedBy(edUtil.decrypt(userData.getUserId()));
        userRepository.updateEmailVerificationStatus(data);
        return true;

    }

    @Override
    public List<UserRoleDTO> retrieveUserRoleByUserType() {
        List<UserRoleDTO> res = null;
        String roleType = "Dealer";
        try {
            res = userRepository.fetchUserRoleByUserType(roleType);
            return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<UsersDTO> retrieveAllConsumers() {
        try {
            return userRepository.fetchAllConsumers();
        } catch (Exception ex) {
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    @Override
    public UsersDTO getUserDataByEmailOrMob(String emailormob) {
        UsersDTO res = null;
        try {
            res = userRepository.fetchUserByEmailOrMobno(emailormob);
            return res;

        } catch (Exception ex) {
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<UserOrderDetailsDTO> retrieveUserOrderedDataByUserId(String userId) {
        List<UserOrderDetailsDTO> res;
        int id = Integer.parseInt(edUtil.decrypt(userId));
        try {
            res = userRepository.fetchUserOrderedDataByUserId(id);
            return res;

        } catch (Exception ex) {
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public void increaseFailedAttempts(UsersDTO user) {
        try {
            int newFailAttempts = user.getFailedAttempt() + 1;
            user.setFailedAttempt(newFailAttempts);
            user.setAccountLocked(false);
            user.setLockTime(dateUtil.getTimestamp());
            if (newFailAttempts >= 3) {
                user.setAccountLocked(true);
            }
            Boolean res = userRepository.updateFailedAttempts(user);
//            System.out.println("res " + res);
        } catch (Exception ex) {
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    public Boolean resetFailedAttempts(UsersDTO user) {
        try {
            user.setFailedAttempt(0);
            Boolean res = userRepository.updateFailedAttempts(user);
//            System.out.println("res " + res);
            return res;
        } catch (Exception ex) {
            return false;
//            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public void userLock(UsersDTO user) {
        try {
            if(user.getLockTime() == null){
                this.increaseFailedAttempts(user);
            }
            else{
                long lockTimeInMillis = user.getLockTime().getTime();                
                long currentTimeInMillis = dateUtil.getTimestamp().getTime();
                long totalTime = lockTimeInMillis + LAST_LOCK_TIME_DURATION;
                if (currentTimeInMillis < totalTime){
                    this.increaseFailedAttempts(user);
                } else {
//                    System.out.println("difference time" + (currentTimeInMillis - (lockTimeInMillis + LAST_LOCK_TIME_DURATION)));
                    Boolean resetRes = this.resetFailedAttempts(user);
                    if (resetRes == true) {
                        this.increaseFailedAttempts(user);
                    }
                }
            }
        } catch (Exception ex) {
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public Boolean unlockWhenTimeExpired(UsersDTO user) {
        try {
            if (user.getAccountLocked() == true) {
                long lockTimeInMillis = user.getLockTime().getTime();
                long currentTimeInMillis = dateUtil.getTimestamp().getTime();
//                System.out.println("lock date " + user.getLockTime());
//                System.out.println("cuurrent date " + dateUtil.getTimestamp());
//                System.out.println("lockTimeInMillis " + lockTimeInMillis);
//                System.out.println("currentTimeInMillis" + currentTimeInMillis);
                if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
                    user.setAccountLocked(false);
                    user.setLockTime(null);
                    user.setFailedAttempt(0);

                    Boolean res = userRepository.updateFailedAttempts(user);
//                    System.out.println("res " + res);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
}
