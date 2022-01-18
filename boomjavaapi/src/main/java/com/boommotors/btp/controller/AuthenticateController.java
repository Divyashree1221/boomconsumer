package com.boommotors.btp.controller;

import com.boommotors.btp.consumer.dao.ConsumerDAO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleMappingDTO;
import com.boommotors.btp.consumer.dto.MqttLoginDTO;
import com.boommotors.btp.consumer.payload.ConsumerOTPRequest;
import com.boommotors.btp.consumer.service.ConsumerService;
import com.boommotors.btp.dto.User;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.payload.ApiResponse;
import com.boommotors.btp.payload.EmailOtpRequest;
import com.boommotors.btp.payload.JwtAuthenticationConsumerResponse;
import com.boommotors.btp.security.JwtTokenProvider;
import com.boommotors.btp.payload.JwtAuthenticationResponse;
import com.boommotors.btp.payload.LoginRequest;
import com.boommotors.btp.payload.SignUpRequest;
import com.boommotors.btp.payload.SigninRequest;
import com.boommotors.btp.product.service.ProductService;
import com.boommotors.btp.user.dao.UserDAO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.service.UserService;
import com.boommotors.btp.util.DateUtil;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;
import com.boommotors.btp.user.dto.UserRoleDTO;
import com.boommotors.btp.util.EncryptDecrypt;
import com.mahalakshmi.smt.exception.BadRequestException;
import java.sql.Timestamp;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author divyashree
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticateController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

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

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        Integer count = 0;
        try {
            // validate if all the fields are entered
            if (signUpRequest.getFirstName().equals("") || signUpRequest.getEmail().equals("")
                    || signUpRequest.getMobileNumber().equals("")
                    //|| signUpRequest.getProvider().equals("")
                    || signUpRequest.getUserRoleId().equals("")) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }

            if (signUpRequest.getSource() == 0) {
                UsersDTO res = null;
                res = userService.retrieveUserByEmailOrMobNo(signUpRequest.getEmail(), signUpRequest.getMobileNumber());

                if (res != null) {
                    return new ResponseEntity(new ApiResponse(false, "Email Address/Mobile Number already in use!", null),
                            HttpStatus.OK);
                }

                // prepare the user data
                User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(), signUpRequest.getMobileNumber(), null, "", signUpRequest.getUserRoleId(), passwordEncoder.encode(signUpRequest.getMobileNumber()));

                // Creating user's record
                String userIdEncryted = userService.registerUser(user);

                // select the user record that has been created
                UsersDTO result = userService.retrieveUserById(userIdEncryted);

                if (result.getUserId() != null) {
//                
                    //generate one time password and send to the registered Mobile no.

                    boolean isMsgSent = userService.generateOneTimePassword(signUpRequest.getMobileNumber(), count);
                    if (!isMsgSent) {
                        return new ResponseEntity(new ApiResponse(false, "Problem occured while sending sms to the registered Mobile number, Please try again", null),
                                HttpStatus.OK);
                    }
                    //send welcome email to the registered email id
                    boolean isEmailSent = userService.sendEmail(result);

                    if (!isEmailSent) {
                        return new ResponseEntity(new ApiResponse(false, "Problem occured while sending email to the registered email Id, Please try again", null),
                                HttpStatus.OK);
                    }

                    return new ResponseEntity(new ApiResponse(true, "OTP sent to the registered mobile number!", result),
                            HttpStatus.OK);

                    // return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", result));
                } else {
                    return new ResponseEntity(new ApiResponse(false, "Problem occured while creating the user, Please try again", null),
                            HttpStatus.OK);
                }
            }
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
        return null;

    }

    @PostMapping("/verifyEmailOtp")
    public ResponseEntity<?> verifyEmailOtp(@RequestBody EmailOtpRequest emailOtpRequest) {
        try {
            if (emailOtpRequest.getEmailId().equals("") || emailOtpRequest.getEmailId() == null || emailOtpRequest.getEmailOtp() == null || emailOtpRequest.getEmailOtp().equals("")) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }
            //retreive user by email id
            UsersDTO result = userService.retrieveUserByEmailOrMobNo(emailOtpRequest.getEmailId(), "");

            if (result != null) {
                Boolean isEmailVerified = userService.verify_Email_Otp(emailOtpRequest.getEmailId(), emailOtpRequest.getEmailOtp());

                if (!isEmailVerified) {

                    return new ResponseEntity(new ApiResponse(false, "Invalid Email OTP!", null),
                            HttpStatus.OK);

                }
                return new ResponseEntity(new ApiResponse(true, "Email OTP verified successfully!", null),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity(new ApiResponse(false, "Incorrect Email ID, Please try again", null),
                        HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/verifyMobileOtp")
    public ResponseEntity<?> verifyMobileOtp(@RequestBody SigninRequest signinRequest) {
        try {
            if (signinRequest.getMobileoremail().equals("") || signinRequest.getOtp().equals("") || signinRequest.getMobileoremail() == null || signinRequest.getOtp() == null) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }
            boolean isUserAllowedToLogin = userService.validateUser(signinRequest);
            if (isUserAllowedToLogin) {
                try {

                    // select the user record that has been created
                    UsersDTO result = userService.retrieveUserByMobNo(signinRequest.getMobileoremail());

                    Long duration = (long) (90) * 1000;
                    Timestamp otpSentTime = result.getMobileOtpIssueTime();
                    System.out.println("otpSentTime -before 90 sec::" + otpSentTime);
                    Timestamp presentTime = dateUtil.getTimestamp();
                    System.out.println("presentTime::" + presentTime);

                    long elapsed = presentTime.getTime() - otpSentTime.getTime();
                    System.out.println("elapsed::" + elapsed);

                    if (elapsed <= duration) {
                        boolean isOTPValid = userService.verify(signinRequest.getMobileoremail(), signinRequest.getOtp());

                        if (isOTPValid) {//validate OTP
                            return new ResponseEntity(new ApiResponse(true, "OTP verified successfully!", null),
                                    HttpStatus.OK);
                        } else {

                            return new ResponseEntity(new ApiResponse(false, "Invalid mobile OTP!", null),//Change msg according to the requi
                                    HttpStatus.OK);
                        }
                    } else {
                        return new ResponseEntity(new ApiResponse(false, "OTP expired!", null),
                                HttpStatus.OK);
                    }
                } catch (BadRequestException e) {
                    return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                            HttpStatus.OK);
                }

            } else {
                return new ResponseEntity(new ApiResponse(false, "Not a valid user!", null),
                        HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | AppException e) {
            //e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/sendMobileOtp")
    public ResponseEntity<?> validateUser(@RequestBody SigninRequest signinRequest) {
        try {
            if (signinRequest.getMobileoremail().equals("") || signinRequest.getPage().equals("") || signinRequest.getMobileoremail() == null || signinRequest.getPage() == null
                    || signinRequest.getCount() == null || Objects.equals(signinRequest.getCount(), "")) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }
            boolean isUserAllowedToLogin = userService.validateUser(signinRequest);

            if (isUserAllowedToLogin) {

                boolean isMsgSent = userService.generateOneTimePassword(signinRequest.getMobileoremail(), signinRequest.getCount());
                if (!isMsgSent) {
                    return new ResponseEntity(new ApiResponse(false, "Problem occured while sending message, Please try again", null),
                            HttpStatus.OK);
                }
                return new ResponseEntity(new ApiResponse(true, "OTP sent to the registered mobile number.", signinRequest.getPage()),
                        HttpStatus.OK);
            } else {

                return new ResponseEntity(new ApiResponse(false, "Not a valid user!", null),//Change the message based on the requi
                        HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | AppException e) {
            //  return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
            printStackTrace(e);
            return new ResponseEntity(new ApiResponse(false, "Error", e),
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
            return new ResponseEntity(new ApiResponse(true, "OTP sent to the registered Email Id.", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SigninRequest signinRequest) {
        try {
            if (signinRequest.getMobileoremail().equals("") || signinRequest.getMobileoremail() == null) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }
            boolean isUserAllowedToLogin = userService.validateUser(signinRequest);
            if (isUserAllowedToLogin) {

                signinRequest.setPassword(signinRequest.getMobileoremail());
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                signinRequest.getMobileoremail(),
                                signinRequest.getPassword()
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenProvider.generateToken(authentication);
                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

            } else {
                return new ResponseEntity(new ApiResponse(false, "Not a valid user!", null),
                        HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser1(@RequestBody SigninRequest signinRequest) {
        try {
            if (signinRequest.getMobileoremail().equals("") || signinRequest.getOtp().equals("") || signinRequest.getMobileoremail() == null || signinRequest.getOtp() == null) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }
            boolean isUserAllowedToLogin = userService.validateUser(signinRequest);
            if (isUserAllowedToLogin) {
                try {
                    // select the user record that has been created
                    UsersDTO result = userService.retrieveUserByMobNo(signinRequest.getMobileoremail());
                    Boolean lcokRes = userService.unlockWhenTimeExpired(result);
                    if (result.getAccountLocked() == false && result.getFailedAttempt() < 3) {
                        // if(result.getMobile_verified() == true){
                        Long duration = (long) (90) * 1000;
                        Timestamp otpSentTime = result.getMobileOtpIssueTime();
//                        System.out.println("otpSentTime -before 90 sec::" + otpSentTime);
                        Timestamp presentTime = dateUtil.getTimestamp();
//                        System.out.println("presentTime::" + presentTime);
                        long elapsed = presentTime.getTime() - otpSentTime.getTime();
                        System.out.println("elapsed::" + elapsed);

                        if (elapsed <= duration) {

                            boolean isOTPValid = userService.verify(signinRequest.getMobileoremail(), signinRequest.getOtp());

                            if (isOTPValid) {//validate OTP
                                // System.out.println("mobileorEmail:"+signinRequest.getMobileoremail());
                                signinRequest.setPassword(signinRequest.getMobileoremail());
//                        signinRequest.setPassword(passwordEncoder.encode(signinRequest.getMobileoremail()));
                                Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                signinRequest.getMobileoremail(),
                                                signinRequest.getPassword()
                                        )
                                );

                                SecurityContextHolder.getContext().setAuthentication(authentication);

                                String jwt = tokenProvider.generateToken(authentication);
                                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
                            } else {
                                userService.userLock(result);
                                return new ResponseEntity(new ApiResponse(false, "Please enter the correct OTP!", null),//Change msg according to the requi
                                        HttpStatus.OK);
                            }
                        } else {
                            return new ResponseEntity(new ApiResponse(false, "OTP expired!", null),
                                    HttpStatus.OK);
                        }
                    } else {
                        return new ResponseEntity(new ApiResponse(false, "Your account has been locked due to 3 failed attempts. It will be unlocked after 24 hours.", null),
                                HttpStatus.OK);
                    }

                } catch (BadRequestException e) {
                    return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                            HttpStatus.OK);
                }

            } else {
                return new ResponseEntity(new ApiResponse(false, "Not a valid user!", null),
                        HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    public ResponseEntity<?> tokenGeneration(@RequestBody LoginRequest loginRequest) {

        // System.out.println("Password::Encoded::" + passwordEncoder.encode(loginRequest.getMobileNumber()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMobileNumber()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (loginRequest.getSource() == 0) {
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } else {
            return null;
        }

    }

    @GetMapping("/validate/byemailormobno/{email_id_or_mobno}")
    public ResponseEntity<?> retrieveByEmailId(@PathVariable(value = "email_id_or_mobno") String emailId) {
        UsersDTO result = userService.retrieveUserByEmailOrMobNo(emailId, emailId);
        if (result == null) {
            return new ResponseEntity(new ApiResponse(false, "No Account found!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Account already exists!", result),
                HttpStatus.OK);
    }

    @GetMapping("/validate/bymobno/{mobile_no}")
    public ResponseEntity<?> retrieveByMobNo(@PathVariable(value = "mobile_no") String mobno) {
        UsersDTO result = userService.retrieveUserByMobNo(mobno);
        if (result == null) {
            return new ResponseEntity(new ApiResponse(false, "No mobile number found!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "Mobile Number already exists!", result),
                HttpStatus.OK);
    }

    @GetMapping("/user-role/bydealer")
    public ResponseEntity<?> retrieveUserRoleByRoleType() {
        List<UserRoleDTO> result = userService.retrieveUserRoleByUserType();
        if (result == null) {
            return new ResponseEntity(new ApiResponse(false, "No user role found!", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(true, "User Role already exists!", result),
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {
            if (loginRequest.getEmail().equals("") || loginRequest.getPassword().equals("")
                    || loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);
            }

            User res = userRepository.fetchUserByEmailForConsumerApp(loginRequest.getEmail());

            if (res != null) {
                String passwordFromDB = res.getPassword();
                String inputPassword = loginRequest.getPassword();
                Boolean isPasswordMatch = passwordEncoder.matches(inputPassword, passwordFromDB);

                if (isPasswordMatch) {
                    List<ConsumerVehicleMappingDTO> isFirstTimeUser = consumerRepository.fetchIsFirstTimeUser(res.getUserId());
                    Long imeiNumber = Long.parseLong(isFirstTimeUser.get(0).getImeiNumber());
                    List<MqttLoginDTO> mqttLoginData = consumerRepository.fetchMqttLoginData(imeiNumber);
                    if (mqttLoginData == null) {
                        return new ResponseEntity(new ApiResponse(false, "You don't have bike mapped!", null),
                                HttpStatus.OK);
                    }
                    return this.tokenGenerationForConsumerApp(loginRequest, !isFirstTimeUser.get(0).getOtpVerified(),
                            isFirstTimeUser.get(0).getUserId(), isFirstTimeUser.get(0).getImeiNumber(),
                            mqttLoginData.get(0).getMac(), mqttLoginData.get(0).getClid());
                } else {
                    return new ResponseEntity(new ApiResponse(false, "Invalid password!", null),
                            HttpStatus.OK);
                }
            } else {
                return new ResponseEntity(new ApiResponse(false, "Invalid user!", null),
                        HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

    public ResponseEntity<?> tokenGenerationForConsumerApp(@RequestBody LoginRequest loginRequest,
            Boolean isFirstTimeUser, String userId, String imei, String mac, String clusterID) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
//        if (loginRequest.getSource() == 0) {
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationConsumerResponse(jwt, isFirstTimeUser, userId, imei, mac, clusterID));
//        } else {
//            return null;
//        }

    }

    @PostMapping("/verifyConsumerOtp")
    public ResponseEntity<?> verifyConsumerOTP(@RequestBody ConsumerOTPRequest consumerOTPRequest) {
        try {
            if (consumerOTPRequest.getUserId().equals("") || consumerOTPRequest.getOtp().equals("") || consumerOTPRequest.getUserId() == null || consumerOTPRequest.getOtp() == null) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }
            List<ConsumerVehicleMappingDTO> result = consumerService.retrieveConsumerVehicleMappingData(consumerOTPRequest.getUserId());
            if (result != null) {
                boolean isOTPValid = consumerService.verifyOTP(consumerOTPRequest.getUserId(), consumerOTPRequest.getOtp());
                if (isOTPValid) {//validate OTP
                    consumerService.updateOtpVerified(result);
                    return new ResponseEntity(new ApiResponse(true, "OTP verified successfully!", null),
                            HttpStatus.OK);
                } else {

                    return new ResponseEntity(new ApiResponse(false, "Invalid  OTP!", null),//Change msg according to the requi
                            HttpStatus.OK);
                }

            } else {
                return new ResponseEntity(new ApiResponse(false, "Not a valid user!", null),
                        HttpStatus.OK);
            }

        } catch (ResourceNotFoundException | AppException e) {
            //e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
    }

//    
//    /**
//     *
//     * @param page (1: Login, 2: forgot password)
//     * @return
//     */
//    @PostMapping("/resendOtp")
//    public ResponseEntity<?> resendOneTimePasswordRequest(@RequestBody OtpRequest OtpRequest) {
//             UsersDTO user = userService.retrieveUserById(OtpRequest.getUserId());
//        
//        if (null != OtpRequest.getType())
//        switch (OtpRequest.getType()) {
//        //resend Mobile otp
//            case 1:
//            {
//                SigninRequest   signinRequest = new SigninRequest();
//                signinRequest.setMobileoremail(user.getMobileNumber());
//                signinRequest.setPage(OtpRequest.getPage());
//                return   validateUser(signinRequest);
//            }
//        //resend Email otp
//            case 2:
//            {
//                EmailOtpRequest emailOtpRequest = new EmailOtpRequest();
//                emailOtpRequest.setEmailId(user.getEmailId());
//                emailOtpRequest.setUserId(user.getUserId());
//                return  sendEmailOtp(emailOtpRequest);
//            }
//        //resend both otp
//            case 3:
//            {
//                
//                //send Mobile OTP
//                SigninRequest   signinRequest = new SigninRequest();
//                signinRequest.setMobileoremail(user.getMobileNumber());
//                signinRequest.setPage(OtpRequest.getPage());
//                validateUser(signinRequest);
//                
//                
//                //send email OTP
//                
//                EmailOtpRequest emailOtpRequest = new EmailOtpRequest();
//                emailOtpRequest.setEmailId(user.getEmailId());
//                emailOtpRequest.setUserId(user.getUserId());
//                return   sendEmailOtp(emailOtpRequest);
//            }
//            default:
//                break;
//        }
//       
//    }
    @PostMapping("/dealer/registerUser")
    public ResponseEntity<?> dealerRegisterUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            Integer count = 0;
            // validate if all the fields are entered
            if (signUpRequest.getFirstName().equals("") || signUpRequest.getEmail().equals("")
                    || signUpRequest.getMobileNumber().equals("")
                    //|| signUpRequest.getProvider().equals("")
                    || signUpRequest.getUserRoleId().equals("")) {

                return new ResponseEntity(new ApiResponse(false, "Please enter all mandatoy fileds!", null),
                        HttpStatus.OK);

            }

            if (signUpRequest.getSource() == 0) {
                UsersDTO res = null;
                res = userService.retrieveUserByEmailOrMobNo(signUpRequest.getEmail(), signUpRequest.getMobileNumber());

                if (res != null) {
                    return new ResponseEntity(new ApiResponse(false, "Email Address/Mobile Number already in use!", null),
                            HttpStatus.OK);
                }

                // prepare the user data
                User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(), signUpRequest.getMobileNumber(), null, "", signUpRequest.getUserRoleId(), passwordEncoder.encode(signUpRequest.getMobileNumber()));

                // Creating user's record
                String userIdEncryted = userService.registerUser(user);

                // select the user record that has been created
                UsersDTO result = userService.retrieveUserById(userIdEncryted);

                if (result.getUserId() != null) {
//                
                    //generate one time password and send to the registered Mobile no.

                    boolean isMsgSent = userService.generateOneTimePassword(signUpRequest.getMobileNumber(), count);
                    if (!isMsgSent) {
                        return new ResponseEntity(new ApiResponse(false, "Problem occured while sending sms to the registered Mobile number, Please try again", null),
                                HttpStatus.OK);
                    }

                    return new ResponseEntity(new ApiResponse(true, "OTP sent to the registered mobile number!", result),
                            HttpStatus.OK);

                    // return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", result));
                } else {
                    return new ResponseEntity(new ApiResponse(false, "Problem occured while creating the user, Please try again", null),
                            HttpStatus.OK);
                }
            }
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error", null),
                    HttpStatus.OK);
        }
        return null;

    }

}
