/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.service;

import com.boommotors.btp.consumer.dao.ConsumerDAO;
import com.boommotors.btp.consumer.dto.ConsumerDetailsDTO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleDetailsDTO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleMappingDTO;
import com.boommotors.btp.consumer.dto.MappedVehiclesDTO;
import com.boommotors.btp.consumer.dto.DashboardDataDTO;
import com.boommotors.btp.consumer.dto.MqttLiveDTO;
import com.boommotors.btp.consumer.payload.ConsumerProfileUpdateRequest;
import com.boommotors.btp.consumer.payload.ConsumerchangePasswordRequest;
import com.boommotors.btp.consumer.payload.ConsumerVehicleMappingRequest;
import com.boommotors.btp.dto.User;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dao.OrderDAO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.order.service.OrderService;
import com.boommotors.btp.payload.OrderRequest;
import com.boommotors.btp.product.dao.ProductDAO;
import com.boommotors.btp.product.dto.ColourDTO;
import com.boommotors.btp.user.dao.UserDAO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.service.EmailService;
import com.boommotors.btp.user.service.SMSService;
import com.boommotors.btp.user.service.UserService;
import com.boommotors.btp.util.DateDAO;
import com.boommotors.btp.util.DateDTO;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import com.boommotors.btp.util.RandomStringGenerator;
import com.mahalakshmi.smt.exception.BadRequestException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ramya
 */
@Service
@Transactional
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ConsumerDAO consumerRepository;

    @Autowired
    UserDAO userRepository;

    @Autowired
    OrderDAO orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    SMSService smsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RandomStringGenerator stringGenerator;

    @Autowired
    DateDAO dateRepository;

    @Autowired
    ProductDAO productRepository;

    @Autowired
    EmailService emailService;

    //SMS values
    @Value("${sms.sender.id}")
    private String smsSenderId;

    @Value("${sms.route}")
    private int smsRoute;

    @Value("${sms.templet.id}")
    private String smsTempletId;

    @Value("${sms.dealerenquiryflow.id}")
    private String dealerenquiryflowId;
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

    @Override
    public List<ConsumerVehicleMappingDTO> retrieveConsumerVehicleMappingData(String encryptedUserId) {

        String id = edUtil.encrypt(String.valueOf(124));

        System.out.println("124 : " + edUtil.encrypt(String.valueOf(124)));

        Integer userId = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedUserId)));
        List<ConsumerVehicleMappingDTO> res = new ArrayList<>();
        try {
            res = consumerRepository.fetchIsFirstTimeUser(userId);
            if (res != null) {
                return res;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    @Override
    public Boolean verifyOTP(String encryptedUserId, Integer code) {
        Integer userId = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedUserId)));

        String result = "";

        result = consumerRepository.validateOTP(userId, code);
        System.out.println("result : " + result);

        switch (result) {
            case "valid":
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
    public Boolean updateOtpVerified(List<ConsumerVehicleMappingDTO> consumerData) {
        Boolean res = false;

        ConsumerVehicleMappingDTO data = new ConsumerVehicleMappingDTO();

        for (int i = 0; i < consumerData.size(); i++) {
            data.setOtpVerified(true);
            data.setConsumerVehicleMappingId(consumerData.get(i).getConsumerVehicleMappingId());
            res = consumerRepository.updateOtpVerified(data);
        }

        return res;
    }

    @Override
    public Boolean updatePassword(ConsumerchangePasswordRequest consumerchangePasswordRequest) {
        Boolean res = false;

        res = consumerRepository.updatePassword(consumerchangePasswordRequest);
        
        return res;
    }

    @Override
    public List<ConsumerDetailsDTO> retrieveConsumerDetailsByUserId(String userId) {

        Integer id = Integer.parseInt(edUtil.decrypt(String.valueOf(userId)));
        List<ConsumerDetailsDTO> res;
        Boolean sumData;
        try {
//            sumData = consumerRepository.existsByUserId(id);
//            if (sumData == false) {
//                
//            }
            res = consumerRepository.fetchConsumerDetailsByUserId(id);
            if (!res.isEmpty()) {
                return res;
            }
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO retrieveMappedDataByOrderSummaryId(String orderSummaryId) {
        Integer summaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(orderSummaryId)));
        ConsumerVehicleMappingDTO res = null;

        try {

            res = consumerRepository.fetchMappedDataByOrderSummaryId(summaryId);

            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String createConsumerVehicleMapping(ConsumerVehicleMappingRequest request) {

        String encriptedId = "";
        UsersDTO result;
        try {
            request.setUsername(request.getEmailId());

            //Generate Random OTP
            String otp = smsService.generate();
            Integer OTP = Integer.parseInt(otp);

            if (request.getAction().equalsIgnoreCase("edit")) {
                Boolean userUpdateRes = this.updateUserDetails(request);
                Boolean orderUpdateRes = this.updateOrderSummary(request);
                System.out.println("edit userUpdateRes " + userUpdateRes);
                System.out.println("edit orderUpdateRes " + orderUpdateRes);
                encriptedId = this.addOrUpdateConsumerVehicleMapping(request, OTP);

            } else if (request.getAction().equalsIgnoreCase("finalSave")) {
                Boolean userUpdateRes = this.updateUserDetails(request);
                Boolean orderUpdateRes = this.updateOrderSummary(request);
                System.out.println("edit userUpdateRes " + userUpdateRes);
                System.out.println("edit orderUpdateRes " + orderUpdateRes);
                encriptedId = this.addOrUpdateConsumerVehicleMapping(request, OTP);
            } else if (request.getAction().equalsIgnoreCase("add")) {
                encriptedId = this.addConsumerVehicleMapping(request, OTP);
            } else {
                Boolean userUpdateRes = this.updateUserDetails(request);
                System.out.println("userUpdateRes " + userUpdateRes);
                result = userService.retrieveUserById(request.getUserId());
                encriptedId = this.createOrderSummaryVehicleMapping(request, OTP, result);
            }
            return encriptedId;

        } catch (AppException | EtAuthException | NumberFormatException e) {
            return null;
        }
    }

    public String createOrderSummaryVehicleMapping(ConsumerVehicleMappingRequest request, Integer OTP, UsersDTO userRes) {
        String encriptedId;
        String orderRes;

        ConsumerVehicleMappingDTO data = new ConsumerVehicleMappingDTO();
        try {

            OrderRequest orderRequest = new OrderRequest();

            orderRequest.setUserId(userRes.getUserId());
            orderRequest.setVariantId(request.getVariantId());
            orderRequest.setVariantName(request.getVariantName());
            orderRequest.setFinanceEmi(0.0);
            orderRequest.setInsuranceType("");
            orderRequest.setInsuranceAmount(0.0);
            orderRequest.setWarrantyAmount(0.0);
            orderRequest.setDeliveryType(request.getDeliveryType());
            orderRequest.setPincode(request.getPincode());
            orderRequest.setCity(request.getCity());
            orderRequest.setState(request.getState());
            orderRequest.setCountry(request.getCountry());
            orderRequest.setIsOutOfCoverage(false);
            orderRequest.setQuantity(request.getQuantity());
            orderRequest.setRfpSent(request.getRfpSent());
            orderRequest.setTotalAmount(request.getTotalAmount());
            orderRequest.setAdvanceAmount(499.00);
            orderRequest.setAmountPaid(request.getAmountPaid());
            orderRequest.setPackageName("");
            orderRequest.setColourAmount(0.0);
            orderRequest.setExShowroom(request.getTotalAmount());
            orderRequest.setPackageAmt(0.0);
            orderRequest.setSubTotal(request.getTotalAmount());
            orderRequest.setGstAmt(0.0);
            orderRequest.setGrossTotal(request.getTotalAmount());
            orderRequest.setSubsidyAmt(0.0);
            orderRequest.setTenure(request.getTenure());
            orderRequest.setExchange(request.getExchange());
            orderRequest.setColourName(request.getColourName());
            orderRequest.setDeliveryType(request.getDeliveryType());

            orderRes = orderService.createOrderSummary(orderRequest);

            if (!"".equals(orderRes) && orderRes != null) {
                Integer summaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(orderRes)));
                ConsumerVehicleMappingDTO mData = consumerRepository.fetchMappedDataByOrderSummaryId(summaryId);
                if (mData != null) {
                    data.setOtp(mData.getOtp());
                    request.setOtp(mData.getOtp());
                } else {
                    data.setOtp(OTP);
                    request.setOtp(OTP);
                }
                data.setUserId(userRes.getUserId());
                data.setDealerId(request.getDealerId());
                data.setVariantId(request.getVariantId());
                data.setVariantName(request.getVariantName());
                data.setColourName(request.getColourName());
                data.setImeiNumber(request.getImeiNumber());
                data.setBookingDate(dateUtil.getTimestamp());
                data.setCreatedBy(request.getCreatedBy());
                data.setOrderSummaryId(orderRes);
                data.setClusterId(request.getClusterId());
                data.setFinalSave(request.getFinalSave());
                if (request.getChassisNumber() == null || "".equalsIgnoreCase(request.getChassisNumber())) {
                    data.setChassisNumber(null);
                } else {
                    data.setChassisNumber(request.getChassisNumber());
                }
                if (request.getImeiNumber() == null || "".equalsIgnoreCase(request.getImeiNumber())) {
                    data.setImeiNumber(null);
                } else {
                    data.setImeiNumber(request.getImeiNumber());
                }
                if (request.getRegistrationNumber() == null || "".equalsIgnoreCase(request.getRegistrationNumber())) {
                    data.setRegistrationNumber(null);
                } else {
                    data.setRegistrationNumber(request.getRegistrationNumber());
                }

                ////// Mapping vehicle to consumer///////////////////
                Long id = consumerRepository.createConsumerVehicleMapping(data);
                if (id != 0 && id != null) {
                    encriptedId = edUtil.encrypt(String.valueOf(id));
                    Boolean emailSent = this.sendEmailToConsumer(request);
                    System.out.println("emailSent : " + emailSent);
                    Boolean smsSent = this.sendSMSToConsumer(request);
                    System.out.println("smsSent : " + smsSent);
                    return encriptedId;
                } else {
                    return "Problem occured while mapping vehicle to user!";
                }

            } else {
                return "Problem occured while creating order!";
            }
        } catch (AppException | EtAuthException | NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String addOrUpdateConsumerVehicleMapping(ConsumerVehicleMappingRequest request, Integer OTP) {
        String encriptedId = "";
        ConsumerVehicleMappingDTO data = new ConsumerVehicleMappingDTO();
        try {
            request.setUsername(request.getEmailId());
            request.setPassword(request.getMobileNumber());

            data.setUserId(request.getUserId());
            data.setDealerId(request.getDealerId());
            data.setVariantId(request.getVariantId());
            data.setVariantName(request.getVariantName());
            data.setColourName(request.getColourName());
            data.setClusterId(request.getClusterId());
            data.setBookingDate(request.getBookingDate());
            data.setCreatedBy(request.getCreatedBy());
            data.setOrderSummaryId(request.getOrderSummaryId());
            data.setFinalSave(request.getFinalSave());
            if (request.getChassisNumber() == null || "".equalsIgnoreCase(request.getChassisNumber())) {
                data.setChassisNumber(null);
            } else {
                data.setChassisNumber(request.getChassisNumber());
            }
            if (request.getImeiNumber() == null || "".equalsIgnoreCase(request.getImeiNumber())) {
                data.setImeiNumber(null);
            } else {
                data.setImeiNumber(request.getImeiNumber());
            }
            if (request.getRegistrationNumber() == null || "".equalsIgnoreCase(request.getRegistrationNumber())) {
                data.setRegistrationNumber(null);
            } else {
                data.setRegistrationNumber(request.getRegistrationNumber());
            }

            Integer summaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(request.getOrderSummaryId())));
            System.out.println("oder summary id " + summaryId);
            ConsumerVehicleMappingDTO mData = consumerRepository.fetchMappedDataByOrderSummaryId(summaryId);
            if (mData != null) {
                data.setOtp(mData.getOtp());
                request.setOtp(mData.getOtp());
                Boolean updateRes = consumerRepository.updateConsumerVehicleMapping(data);
                if (updateRes) {
                    encriptedId = "IMEI NO. updated successfully.";
                    Boolean emailSent = this.sendEmailToConsumer(request);
                    System.out.println("emailSent : " + emailSent);
                    Boolean smsSent = this.sendSMSToConsumer(request);
                    System.out.println("smsSent : " + smsSent);
                } else {
                    encriptedId = "Error occured while updating IMEI NO!";
                }
            } else {
                data.setOtp(OTP);
                request.setOtp(OTP);
                Long id = consumerRepository.createConsumerVehicleMapping(data);
                encriptedId = edUtil.encrypt(String.valueOf(id));
                Boolean emailSent = this.sendEmailToConsumer(request);
                System.out.println("emailSent : " + emailSent);
                Boolean smsSent = this.sendSMSToConsumer(request);
                System.out.println("smsSent : " + smsSent);
            }

            return encriptedId;
        } catch (AppException | NumberFormatException ex) {
            return null;
        }

    }

    public String addConsumerVehicleMapping(ConsumerVehicleMappingRequest request, Integer OTP) {
        ConsumerVehicleDetailsDTO res = new ConsumerVehicleDetailsDTO();
        try {
            UsersDTO exists = null;
            String userRes = "";
            exists = userService.retrieveUserByEmailOrMobNo(request.getEmailId(), request.getMobileNumber());

            if (exists != null) {
                return "Email Address/Mobile Number already in use!";
//                    
            } else {
                //////////creating user/////////////////
                String password = passwordEncoder.encode(request.getPassword());
                User user = new User();
                user.setEmail(request.getEmailId());
                user.setFirstName(request.getFirstname());
                user.setLastName(request.getLastname());
                user.setMobileNumber(request.getMobileNumber());
                user.setPassword(password);
                user.setSocialId("");
                user.setProvider("Walkin-User");
                user.setUserRoleId(3);
                userRes = userService.registerUser(user);

                //////////creating Ordersummary Data/////////////////
                if (!"".equals(userRes) && userRes != null) {
                    UsersDTO result = userService.retrieveUserById(userRes);
                    return this.createOrderSummaryVehicleMapping(request, OTP, result);
                } else {
                    return "Problem occured while creating user!";
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Boolean sendEmailToConsumer(ConsumerVehicleMappingRequest data) {
        String isEmailSent;

        Timestamp dateNow = dateUtil.getTimestamptoSendSMS();
        String istDate = dateNow.toString().trim().substring(0, 19);
        System.out.println("date String : " + istDate);
        System.out.println("Lastname : " + data.getLastname());
        String lastname = "";
        String fullName = "";
        if (data.getLastname() != null) {
            lastname = data.getLastname();
            fullName = data.getFirstname() + " " + lastname;
        } else {
            fullName = data.getFirstname();
        }

        System.out.println("lastname : " + lastname);
//        String email = "divyashreehn12@gmail.com";
        String jsonInputString = "{\n"
                + "  \"to\": [\n"
                + "    {\n"
                + "      \"name\": \"" + data.getFirstname() + "\",\n"
                + "      \"email\": \"" + data.getEmailId() + "\"\n"
                + "    }]\n"
                + "  ,\n"
                + "  \"from\": {\n"
                + "    \"name\": \"" + emailFromName + "\",\n"
                + "    \"email\": \"" + emailFromMailID + "\"\n"
                + "  },\n"
                + "  \"mail_type_id\": \"1\",\n"
                + "  \"template_id\": \"" + consumerEmailTemplate + "\",\n"
                + "  \"variables\": {\n"
                + "    \"VAR7\": \"" + fullName + "\",\n"
                + "	\"VAR18\": \"" + data.getUsername() + "\",\n"
                + "	\"VAR19\": \"" + data.getPassword() + "\",\n"
                + "	\"VAR15\":\"" + data.getOtp() + "\"\n"
                + "  },\n"
                + "  \"authkey\": \"" + emailAuthKey + "\"\n"
                + "}";

        isEmailSent = emailService.sendEmail(jsonInputString);

        if (isEmailSent.equalsIgnoreCase("success")) {
            return true;
        } else {
            return false;
        }

    }

    public boolean sendSMSToConsumer(ConsumerVehicleMappingRequest data) {
        String isSMSSent = "";
        DateDTO dateNow = dateRepository.retrieveISTTimeFromDB();
        String istDate = dateNow.getTimezone().toString().trim().substring(0, 19);
        System.out.println("date String : " + istDate);

        //Send SMS to consumer mobile
        String jsonInputString = "{\n"
                + "  \"flow_id\": \"" + dealerenquiryflowId + "\",\n"
                + "  \"sender\": \"" + smsSenderId + "\",\n"
                + "  \"mobiles\":\"91" + data.getMobileNumber() + "\",\n"
                + "  \"VAR18\": \"" + data.getUsername() + "\",\n"
                + "  \"VAR19\": \"" + data.getPassword() + "\",\n"
                + "  \"VAR1\": \"" + data.getOtp() + "\",\n"
                + "  \"VAR2\": \"" + istDate + "\"\n"
                + "}";
        System.out.println("sms " + jsonInputString);

        isSMSSent = smsService.send(data.getMobileNumber(), jsonInputString);

        if (isSMSSent.equalsIgnoreCase("success")) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean updateUserDetails(ConsumerVehicleMappingRequest request) {
        UsersDTO userData = new UsersDTO();
        try {
            userData.setFirstName(request.getFirstname());
            userData.setLastName(request.getLastname());
            userData.setEmailId(request.getEmailId());
            userData.setUserId(request.getUserId());
            userData.setUpdatedBy(request.getDealerId());

            return userRepository.updateUserInfo(userData);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean updateOrderSummary(ConsumerVehicleMappingRequest request) {
        OrderSummaryDTO summaryData = new OrderSummaryDTO();
        try {
            summaryData.setCity(request.getCity());
            summaryData.setState(request.getState());
            summaryData.setCountry(request.getCountry());
            summaryData.setPincode(request.getPincode());
            summaryData.setUpdatedBy(request.getDealerId());
            summaryData.setOrderSummaryId(request.getOrderSummaryId());

            return orderRepository.updateConsumerOrderDetails(summaryData);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ConsumerVehicleDetailsDTO retrieveVehicleDetails() {
        ConsumerVehicleDetailsDTO res = new ConsumerVehicleDetailsDTO();
        try {
            res.setBatteryLeft("89");
            res.setRangeLeft("79");
            res.setVariantName("Corbet 14");
            return res;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public DashboardDataDTO retrieveMqttLiveData(Long imei) {
        DashboardDataDTO res = null;

        try {
            System.out.println("here 1");
            res = consumerRepository.fetchMqttLiveData(imei);

            System.out.println("here 5");
            return res;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO clusterIdExists(ConsumerVehicleMappingRequest req) {
        Integer summaryId;
        try {
            if ((req.getAction().equals("edit") || req.getAction().equals("finalSave")) && req.getOrderSummaryId() != null && !"".equals(req.getOrderSummaryId())) {
                summaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(req.getOrderSummaryId())));
                return consumerRepository.clusterIdExistsForEdit(req.getClusterId(), summaryId);
            } else {
                return consumerRepository.clusterIdExistsForAdd(req.getClusterId());
            }
        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO chassisNumberExists(ConsumerVehicleMappingRequest req) {
        Integer summaryId;
        try {
            if ((req.getAction().equals("edit") || req.getAction().equals("finalSave")) && req.getOrderSummaryId() != null && !"".equals(req.getOrderSummaryId())) {
                summaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(req.getOrderSummaryId())));
                return consumerRepository.chassisNumberExistsForEdit(req.getChassisNumber(), summaryId);
            } else {
                return consumerRepository.chassisNumberExistsForAdd(req.getChassisNumber());
            }
        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO imeiNumberExists(ConsumerVehicleMappingRequest req) {
        Integer summaryId;
        try {
            if ((req.getAction().equals("edit") || req.getAction().equals("finalSave")) && req.getOrderSummaryId() != null && !"".equals(req.getOrderSummaryId())) {
                summaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(req.getOrderSummaryId())));
                return consumerRepository.imeiNumberExistsForEdit(req.getImeiNumber(), summaryId);
            } else {
                return consumerRepository.imeiNumberExistsForAdd(req.getImeiNumber());
            }
        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO registrationNumberExists(ConsumerVehicleMappingRequest req) {
        Integer summaryId;
        try {
            if ((req.getAction().equals("edit") || req.getAction().equals("finalSave")) && req.getOrderSummaryId() != null && !"".equals(req.getOrderSummaryId())) {
                summaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(req.getOrderSummaryId())));
                return consumerRepository.registrationNumberExistsForEdit(req.getRegistrationNumber(), summaryId);
            } else {
                return consumerRepository.registrationNumberExistsForAdd(req.getRegistrationNumber());
            }
        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public List<MappedVehiclesDTO> retrieveAllMappedVehiclesByUserId(String encruptedUserId) {
        String id = edUtil.encrypt(String.valueOf(124));

        Integer userId = Integer.parseInt(edUtil.decrypt(String.valueOf(encruptedUserId)));
        List<MappedVehiclesDTO> res = new ArrayList<>();
        try {
            res = consumerRepository.fetchAllMappedVehiclesByUserId(userId);
            if (res != null) {
                for (int i = 0; i < res.size(); i++) {
                    Long imei = Long.parseLong(res.get(i).getImeiNumber());
                    MqttLiveDTO socData = consumerRepository.fetchSOC(imei);
                    if (socData != null) {
                        res.get(i).setSoc(socData.getSoc());
                    }
                    Integer variantId = Integer.parseInt(edUtil.decrypt(String.valueOf(res.get(i).getVariantId())));
                    ColourDTO imageUrlData = productRepository.fetchImageUrl(variantId, res.get(i).getColourName());
                    if (imageUrlData != null) {
                        res.get(i).setImageUrl(imageUrlData.getImageUrl());
                    }
                }
                return res;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    @Override
    public Boolean updateProfile(ConsumerProfileUpdateRequest consumerProfileUpdateRequest) {
        Boolean res = false;
        try {
            res = consumerRepository.updateProfile(consumerProfileUpdateRequest);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
