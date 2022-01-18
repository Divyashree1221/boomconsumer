/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.service;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dao.BulkOrderDAO;
import com.boommotors.btp.order.dto.BulkEnquiryAccessoriesDTO;
import com.boommotors.btp.order.dto.BulkEnquiryDTO;
import com.boommotors.btp.order.dto.BulkEnquiryDetailsDTO;
import com.boommotors.btp.order.dto.BulkEnquiryPartsColourDTO;
import com.boommotors.btp.order.dto.BulkEnquirySuccessDTO;
import com.boommotors.btp.order.dto.ModelAccessoriesDetailsDTO;
import com.boommotors.btp.order.dto.VariantPartsColourDetailsDTO;
import com.boommotors.btp.payload.OrderRequest;
import com.boommotors.btp.product.dao.ProductDAO;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.service.EmailService;
import com.boommotors.btp.user.service.SMSService;
import com.boommotors.btp.user.service.UserService;
import com.boommotors.btp.util.DateDAO;
import com.boommotors.btp.util.DateDTO;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author divyashree
 */
@Service
public class BulkOrderServiceImpl implements BulkOrderService {

    @Autowired
    BulkOrderDAO bulkOrderRepository;

    @Autowired
    ProductDAO productRepository;

    @Autowired
    DateDAO dateRepository;

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    SMSService smsService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

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

    @Value("${email.BulkEnquiryTemplate}")
    private String bulkEnquiryTemplate;

    @Value("${email.BulkEnqmail2infoTemplate}")
    private String bulkEnqmail2infoTemplate;

    @Value("${email.auth.key}")
    private String emailAuthKey;

    @Value("${email.FromName}")
    private String emailFromName;

    //==================================== 
    @Override
    public String createBulkEnquiry(OrderRequest orderRequest) throws EtAuthException {
        try {
            Long bulkEnquiryID;

            BulkEnquiryDTO bulkEnquiry = new BulkEnquiryDTO();
            bulkEnquiry.setUserId(orderRequest.getUserId());
            bulkEnquiry.setVariantId(orderRequest.getVariantId());
            bulkEnquiry.setVariantName(orderRequest.getVariantName());
            bulkEnquiry.setColourName(orderRequest.getColourName());
            bulkEnquiry.setFinanceEmi(orderRequest.getFinanceEmi());
            bulkEnquiry.setInsuranceType(orderRequest.getInsuranceType());
            bulkEnquiry.setInsuranceAmount(orderRequest.getInsuranceAmount());
            bulkEnquiry.setWarrantyAmount(orderRequest.getWarrantyAmount());
            bulkEnquiry.setDelivery_type(orderRequest.getDeliveryType());
            bulkEnquiry.setPincode(orderRequest.getPincode());
            bulkEnquiry.setCity(orderRequest.getCity());
            bulkEnquiry.setState(orderRequest.getState());
            bulkEnquiry.setCountry(orderRequest.getCountry());
            bulkEnquiry.setIsOutOfCoverage(orderRequest.getIsOutOfCoverage());
            bulkEnquiry.setQuantity(orderRequest.getQuantity());
            bulkEnquiry.setRfpMailSent(false);
            bulkEnquiry.setRfpSmsSent(false);
            bulkEnquiry.setMailToSalesSent(false);
            bulkEnquiry.setTotalAmount(orderRequest.getTotalAmount());
            bulkEnquiry.setAdvanceAmount(orderRequest.getAdvanceAmount());
            bulkEnquiry.setAmountPaid(orderRequest.getAmountPaid());
            bulkEnquiry.setPackageName(orderRequest.getPackageName());
            bulkEnquiry.setColourAmount(orderRequest.getColourAmount());
            bulkEnquiry.setExShowroom(orderRequest.getExShowroom());
            bulkEnquiry.setPackageAmt(orderRequest.getPackageAmt());
            bulkEnquiry.setSubTotal(orderRequest.getSubTotal());
            bulkEnquiry.setGstAmt(orderRequest.getGstAmt());
            bulkEnquiry.setGrossTotal(orderRequest.getGrossTotal());
            bulkEnquiry.setSubsidyAmt(orderRequest.getSubsidyAmt());
            bulkEnquiry.setCreatedBy(orderRequest.getUserId());
            bulkEnquiry.setCreatedDate(dateUtil.getTimestamp());
            bulkEnquiry.setUpdatedBy(orderRequest.getUserId());
            bulkEnquiry.setUpdatedDate(dateUtil.getTimestamp());
            bulkEnquiry.setTenure(orderRequest.getTenure());
            bulkEnquiry.setExchange(orderRequest.getExchange());

            // Inserting Bulk enquiry data
            bulkEnquiryID = bulkOrderRepository.createBulkEnquiry(bulkEnquiry);

            //if user has choosen for custom colours insert into bulk_enquiry Parts Colour table
            if (bulkEnquiryID > 0 && "".equals(orderRequest.getColourName())) {

                String[] myArray = {orderRequest.getChasisColourID(), orderRequest.getBodyMudGuardColourID(), orderRequest.getMaskHandleBarColourID(), orderRequest.getShocksColourID(), orderRequest.getWheelRimsColourID()};

                for (String myArray1 : myArray) {
                    BulkEnquiryPartsColourDTO enquiryPartsColour = new BulkEnquiryPartsColourDTO();
                    enquiryPartsColour.setBulkEnquiryId(bulkEnquiryID.toString());
                    enquiryPartsColour.setVariantPartsColourId(myArray1);
                    enquiryPartsColour.setCreatedBy(orderRequest.getUserId());
                    // Inserting Bulk-Enquiry  Parts Colour data
                    bulkOrderRepository.createBulkEnquiryPartColour(enquiryPartsColour);
                }

            }
            if (bulkEnquiryID > 0 && orderRequest.getModelAccessories() != null && !"".equals(orderRequest.getModelAccessories())) {

                String ModelAccessories = orderRequest.getModelAccessories();
                String[] ModelAccessoriesArray = ModelAccessories.split(",");

                System.out.println("ModelAccessoriesArray : " + Arrays.toString(ModelAccessoriesArray));
                for (String ModelAccessoriesArray1 : ModelAccessoriesArray) {
                    BulkEnquiryAccessoriesDTO enquiryAccessories = new BulkEnquiryAccessoriesDTO();
                    enquiryAccessories.setBulkEnquiryId(bulkEnquiryID.toString());
                    enquiryAccessories.setModelAccessoriesId(ModelAccessoriesArray1);
                    enquiryAccessories.setCreatedBy(orderRequest.getUserId());
                    // Inserting Bulk-Enquiry Accessories data
                    bulkOrderRepository.createBulkEnquiryAccessories(enquiryAccessories);
                }

            }
            if (bulkEnquiryID > 0) {

                //fetching model_name
                Integer modelId = Integer.parseInt(edUtil.decrypt(orderRequest.getModelId()));
                ModelDTO modelDetails = null;
                boolean salesEmailres = false;
                boolean custEmailres = false;
                boolean smsRes = false;
                if (modelId > 0) {
                    modelDetails = productRepository.fetchModelById(modelId);
                }
                if (modelDetails != null) {
                    bulkEnquiry.setBulkEnquiryId(edUtil.encrypt(String.valueOf(bulkEnquiryID)));
                    UsersDTO userData = userService.retrieveUserById(bulkEnquiry.getUserId());

                    if (userData.getEmailVerified()) {
                       // custEmailres = sendEmailToCustomer(bulkEnquiry, userData, modelDetails.getModelName());
                        System.out.println("emailres : " + custEmailres);
                    }
                    salesEmailres = sendEmailToSales(bulkEnquiry, userData, modelDetails.getModelName());
                    System.out.println("salesEmailres : " + salesEmailres);
                   // smsRes = sendSMSToCustomer(bulkEnquiry, userData);
                }
                // Updating bulk enquiry email and SMS status//
                bulkEnquiry.setRfpMailSent(false);
                bulkEnquiry.setRfpSmsSent(false);
                bulkEnquiry.setMailToSalesSent(salesEmailres);
//
                boolean updateRes = bulkOrderRepository.updateBulkEnquiry(bulkEnquiry);
                System.out.println("updateRes : " + updateRes);
                return edUtil.encrypt(String.valueOf(bulkEnquiryID));
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    public boolean sendEmailToCustomer(BulkEnquiryDTO bulkEnquiry, UsersDTO userData, String model_name) {
        String isEmailSent;

        try {
            String modelName = model_name;
            String variantName = bulkEnquiry.getVariantName();
            String lastName = "";
            if (userData.getLastName() != null) {
                lastName = userData.getLastName();
            }

            System.out.println("ModelName " + modelName);
            System.out.println("variantName " + variantName);

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
                    + "  \"template_id\": \"" + bulkEnquiryTemplate + "\",\n"
                    + "  \"variables\": {\n"
                    + "    \"VAR7\": \"" + userData.getFirstName() + "\",\n"
                    + "	\"VAR8\": \"" + lastName + "\",\n"
                    + "	\"VAR3\": \"" + modelName + "\",\n"
                    + "	\"VAR4\":\"" + variantName + "\"\n"
                    + "  },\n"
                    + "  \"authkey\": \"" + emailAuthKey + "\"\n"
                    + "}";

            isEmailSent = emailService.sendEmail(jsonInputString);
//        } else {
//            throw new BadRequestException("Oops! An error occurred");
//        }
            if (isEmailSent == null) {
                return false;
            } else if (!isEmailSent.equalsIgnoreCase("success")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean sendEmailToSales(BulkEnquiryDTO bulkEnquiry, UsersDTO userData, String model_name) {
        String isEmailSent;

        try {
            String modelName = model_name;
            String variantName = bulkEnquiry.getVariantName();
            String lastName = "";
            String fullName = "";
            if (userData.getLastName() != null) {
                lastName = userData.getLastName();
                fullName = userData.getFirstName() + ' ' + userData.getLastName();
            } else {
                fullName = userData.getFirstName();
            }
            String emailVerified = "";
            if (userData.getEmailVerified()) {
                emailVerified = "Verified";
            } else {
                emailVerified = "Not Verified";
            }

            System.out.println("ModelName " + modelName);
            System.out.println("variantName " + variantName);
            System.out.println("emailVerified " + emailVerified);

            String jsonInputString = "{\n"
                    + "  \"to\": [\n"
                    + "    {\n"
                    + "      \"name\": \"" + emailFromName + "\",\n"
                    + "      \"email\": \"" + emailFromMailID + "\"\n"
                    + "    }]\n"
                    + "  ,\n"
                    + "  \"from\": {\n"
                    + "    \"name\": \"" + emailFromName + "\",\n"
                    + "    \"email\": \"" + emailFromMailID + "\"\n"
                    + "  },\n"
                    + "  \"mail_type_id\": \"1\",\n"
                    + "  \"template_id\": \"" + bulkEnqmail2infoTemplate + "\",\n"
                    + "  \"variables\": {\n"
                    + "     \"VAR7\": \"" + fullName + "\",\n"
//                    + "     \"VAR8\": \"" + lastName + "\",\n"
                    + "	\"VAR3\": \"" + modelName + "\",\n"
                    + "	\"VAR4\": \"" + variantName + "\",\n"
                    + "	\"VAR9\": \"" + userData.getMobileNumber() + "\",\n"
                    + "	\"VAR10\": \"" + userData.getEmailId() + "\",\n"
                    + "	\"VAR11\": \"" + emailVerified + "\",\n"
                    + "	\"VAR14\":\"" + bulkEnquiry.getQuantity() + "\"\n"
                    + "  },\n"
                    + "  \"authkey\": \"" + emailAuthKey + "\"\n"
                    + "}";

            isEmailSent = emailService.sendEmail(jsonInputString);

            if (isEmailSent == null) {
                return false;
            } else if (isEmailSent.equalsIgnoreCase("success")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendSMSToCustomer(BulkEnquiryDTO bulkEnquiry, UsersDTO userData) {

        String isSMSSent = "";

        DateDTO dateNow = dateRepository.retrieveISTTimeFromDB();
        System.out.println("dateNow : " + dateNow.getTimezone());
        String istDate = dateNow.getTimezone().toString().trim().substring(0, 16);
        System.out.println("date String : " + istDate);

        //Send SMS to user mobile
        String jsonInputString = "{\n"
                + "  \"flow_id\": \"" + smsflowId + "\",\n"
                + "  \"sender\": \"" + smsSenderId + "\",\n"
                + "  \"mobiles\":\"91" + userData.getMobileNumber() + "\",\n"
                //   + "  \"var1\": \"" + otp + "\",\n"
                + "  \"var2\": \"" + istDate + "\"\n"
                + "}";
        System.out.println("sms " + jsonInputString);

        isSMSSent = smsService.send(userData.getMobileNumber(), jsonInputString);

        if (isSMSSent.equalsIgnoreCase("success")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BulkEnquirySuccessDTO FetchBulkEnquiryByID(String encryptedBulkEnquiryID) throws EtAuthException {
        BulkEnquirySuccessDTO bulkEnquiryDTO = new BulkEnquirySuccessDTO();
        bulkEnquiryDTO = bulkOrderRepository.FetchBulkEnquiryByID(encryptedBulkEnquiryID);
        return bulkEnquiryDTO;
    }

    @Override
    public List<BulkEnquiryDetailsDTO> retrieveBulkEnquiryUserID(String encryptedUserID) {
        int userId = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedUserID)));

        List<BulkEnquiryDetailsDTO> bulkEnquiryData = new ArrayList<>();
        try {
            bulkEnquiryData = bulkOrderRepository.fetchBulkEnquiryByUserID(userId);
            if (bulkEnquiryData != null && !bulkEnquiryData.isEmpty()) {

                for (int i = 0; i < bulkEnquiryData.size(); i++) {
                    int bulkEnquiryId = Integer.parseInt(String.valueOf(bulkEnquiryData.get(i).getBulkEnquiryId()));

                    List<ModelAccessoriesDetailsDTO> accessoriesRes = bulkOrderRepository.fetchModelAccessories(bulkEnquiryId);
                    bulkEnquiryData.get(i).setModelAccessories(accessoriesRes);
                    List<VariantPartsColourDetailsDTO> colourRes = bulkOrderRepository.fetchPartsColourDetails(bulkEnquiryId);
                    bulkEnquiryData.get(i).setPartsColourDetails(colourRes);
                }
                return bulkEnquiryData;
            }

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }
}
