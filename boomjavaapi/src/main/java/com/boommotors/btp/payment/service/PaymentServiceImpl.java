/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.service;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.invoice.util.IndexHelper;
import com.boommotors.btp.invoice.util.Invoice;
import com.boommotors.btp.order.dto.BulkEnquiryDTO;
import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.payment.dao.PaymentDAO;
import com.boommotors.btp.payment.dto.OrderDTO;
import com.boommotors.btp.payment.dto.PaymentDetailsDTO;
import com.boommotors.btp.payment.payload.RazorpayOnlinePaymentOrderTransactionRequest;
import com.boommotors.btp.payment.payload.RazorpayOrderRequest;
import com.boommotors.btp.payment.payload.RazorpayOrderTransactionRequest;
import com.boommotors.btp.product.dao.ProductDAO;
import com.boommotors.btp.product.dto.ModelDTO;
import com.boommotors.btp.product.dto.VariantsDTO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.service.EmailService;
import com.boommotors.btp.user.service.SMSService;
import com.boommotors.btp.user.service.UserService;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.ServletContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import io.imagekit.sdk.utils.Utils;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.Refund;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

/**
 *
 * @author Ramya
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    PaymentDAO paymentRepository;

    @Autowired
    ProductDAO productRepository;

    @Autowired
    UserService userService;

    @Autowired
    SMSService smsService;

    @Autowired
    EmailService emailService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ServletContext servletContext;

    @Value("${razorpay.keyId}")
    private String razorpayKeyId;

    @Value("${razorpay.keySecret}")
    private String razorpayKeySecret;

    private final TemplateEngine templateEngine;

    public PaymentServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Value("${sms.paymentflow.id}")
    private String smspaymentflowId;

    @Value("${sms.sender.id}")
    private String smsSenderId;

    @Value("${email.FromName}")
    private String emailFromName;

    @Value("${email.FromEmailId}")
    private String emailFromMailID;

    @Value("${email.AdvanceBookingTemplate}")
    private String emailAdvanceBookingTemplate;

    @Value("${email.auth.key}")
    private String emailAuthKey;

    @Value("${email.BulkEnqmail2infoTemplate}")
    private String bulkEnqmail2infoTemplate;

    @Override
    public OrderSummaryDTO retrieveOrderSummary(String orderSummaryId) {
        int orderSummaryIdInt = Integer.parseInt(edUtil.decrypt(orderSummaryId));
        System.out.println("1 : " + edUtil.encrypt(String.valueOf(1)));
        System.out.println("3 : " + edUtil.encrypt(String.valueOf(3)));
        try {
            return paymentRepository.fetchOrderSummary(orderSummaryIdInt);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public OrderDTO createRazorpayOrder(RazorpayOrderRequest razorpayOrderRequest, OrderSummaryDTO orderSummaryData) throws RazorpayException {

        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        String userId = edUtil.decrypt(razorpayOrderRequest.getUserId());
        String receipt = userId.concat(razorpayOrderRequest.getPaymentType());
        JSONObject orderRequest = new JSONObject();
        Double advanceAmount = orderSummaryData.getAdvanceAmount() * 100;
        orderRequest.put("amount", advanceAmount); // amount in the smallest currency unit
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", receipt);
        orderRequest.put("payment_capture", 1);

        Order order = razorpay.Orders.create(orderRequest);
        System.out.println("Order : " + order);

        Order result = razorpay.Orders.fetch(order.get("id"));

        OrderDTO res = new OrderDTO();
        res.setId(result.get("id"));
        res.setAmount(result.get("amount"));
        res.setAmountPaid(result.get("amount_paid"));
        res.setAmountDue(result.get("amount_due"));
        res.setCurrency(result.get("currency"));
        res.setReceipt(result.get("receipt"));
        res.setStatus(result.get("status"));
        res.setAttempts(result.get("attempts"));

        OrderDetailDTO data = new OrderDetailDTO();
        data.setOrderSummaryId(razorpayOrderRequest.getOrderSummaryId());
        data.setRazorpayOrderId(res.getId());
        data.setPaymentType(razorpayOrderRequest.getPaymentType());
        Double amount = Double.valueOf(res.getAmount());
        amount = amount / 100;
        data.setAmount(amount);
        data.setCreatedBy(razorpayOrderRequest.getUserId());
        boolean isPaid;
        if (res.getAmountPaid() == 0) {
            isPaid = false;
        } else {
            isPaid = true;
        }
        data.setIsPaid(isPaid);

        String orderDetailIdEncryted = saveOrderdetail(data);
        System.out.println("OrderDetailID : " + edUtil.decrypt(orderDetailIdEncryted));
        res.setOrderDetailId(orderDetailIdEncryted);
        return res;

    }

    @Override
    public OrderDTO createOnlinePaymentRazorpayOrder(RazorpayOrderRequest razorpayOrderRequest) throws RazorpayException {

        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        String userId = edUtil.decrypt(razorpayOrderRequest.getUserId());
        String receipt = userId.concat(razorpayOrderRequest.getPaymentType());
        JSONObject orderRequest = new JSONObject();
        Double inputAmount = razorpayOrderRequest.getAmount() * 100;
        orderRequest.put("amount", inputAmount); // amount in the smallest currency unit
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", receipt);
        orderRequest.put("payment_capture", 1);

        Order order = razorpay.Orders.create(orderRequest);
        System.out.println("Order : " + order);

        Order result = razorpay.Orders.fetch(order.get("id"));

        OrderDTO res = new OrderDTO();
        res.setId(result.get("id"));
        res.setAmount(result.get("amount"));
        res.setAmountPaid(result.get("amount_paid"));
        res.setAmountDue(result.get("amount_due"));
        res.setCurrency(result.get("currency"));
        res.setReceipt(result.get("receipt"));
        res.setStatus(result.get("status"));
        res.setAttempts(result.get("attempts"));

        OrderDetailDTO data = new OrderDetailDTO();
        data.setOrderSummaryId(razorpayOrderRequest.getOrderSummaryId());
        data.setRazorpayOrderId(res.getId());
        data.setPaymentType(razorpayOrderRequest.getPaymentType());
        Double amount = Double.valueOf(res.getAmount());
        amount = amount / 100;
        data.setAmount(amount);
        data.setCreatedBy(razorpayOrderRequest.getUserId());
        boolean isPaid;
        if (res.getAmountPaid() == 0) {
            isPaid = false;
        } else {
            isPaid = true;
        }
        data.setIsPaid(isPaid);

        String orderDetailIdEncryted = saveOrderdetail(data);
        System.out.println("OrderDetailID : " + edUtil.decrypt(orderDetailIdEncryted));
        res.setOrderDetailId(orderDetailIdEncryted);
        return res;

    }

    @Override
    public String saveOrderdetail(OrderDetailDTO orderDetail) {
        try {

            Long id = paymentRepository.saveOrderDtail(orderDetail);
            System.out.println("ID : " + id);
            String orderDetailId = edUtil.encrypt(String.valueOf(id));
            return orderDetailId;

        } catch (Exception ex) {
            logger.error("Responding with error. Message - {}", ex.getMessage());
            //throw new AppException("Exception occured while processing the request!", ex);
            ex.printStackTrace();

        }
        return null;
    }

    @Override
    public String saveOrderTransaction(RazorpayOrderTransactionRequest razorpayOrderTransactionRequest, Payment payment) {
        try {

            OrderTransactionDTO data = new OrderTransactionDTO();
            data.setOrderSummaryId(razorpayOrderTransactionRequest.getOrderSummaryId());
            data.setRazorpayPaymentId(razorpayOrderTransactionRequest.getRazorpayPaymentId());
            data.setRazorpayOrderId(razorpayOrderTransactionRequest.getRazorpayOrderId());
            data.setPaymentSuccess(1);
            Object status = payment.get("status");
            if (!JSONObject.NULL.equals(status)) {
                data.setStatus(payment.get("status"));
            }

            Object method = payment.get("method");
            if (!JSONObject.NULL.equals(method)) {
                data.setPaymentMethod(payment.get("method"));
            }
            Object cardId = payment.get("card_id");
            if (!JSONObject.NULL.equals(cardId)) {
                data.setCardId(payment.get("card_id"));
            }

            Object captured = payment.get("captured");
            if (!JSONObject.NULL.equals(captured)) {
                data.setCaptured(payment.get("captured"));
            }

            Object entity = payment.get("entity");
            if (!JSONObject.NULL.equals(entity)) {
                data.setEntity(payment.get("entity"));
            }

            Object currency = payment.get("currency");
            if (!JSONObject.NULL.equals(currency)) {
                data.setCurrency(payment.get("currency"));
            }

            Object description = payment.get("description");
            if (!JSONObject.NULL.equals(description)) {
                data.setDescription(payment.get("description"));
            }

            Object bank = payment.get("bank");
            if (!JSONObject.NULL.equals(bank)) {
                data.setBank(payment.get("bank"));
            }

            Object wallet = payment.get("wallet");
            if (!JSONObject.NULL.equals(wallet)) {
                data.setWallet(payment.get("wallet"));
            }

            Object vpa = payment.get("vpa");
            if (!JSONObject.NULL.equals(vpa)) {
                data.setVpa(payment.get("vpa"));
            }

            Object fee = payment.get("fee");
            Double feeDouble = Double.parseDouble(fee.toString());
            Double feeBy100 = feeDouble / 100;
            System.out.println("Fee Double : " + feeDouble);
            System.out.println("Fee By 100 : " + feeBy100);
            if (!JSONObject.NULL.equals(fee)) {
                data.setFee(feeBy100);
            }

            Object refundStatus = payment.get("refund_status");
            if (!JSONObject.NULL.equals(refundStatus)) {
                data.setRefundStatus(payment.get("refund_status"));
            }

            data.setAuthCode(razorpayOrderTransactionRequest.getSignature());

            Object amountRefunded = payment.get("amount_refunded");
            Double amountRefundedDouble = Double.parseDouble(amountRefunded.toString());
            Double amountRefundedBy100 = amountRefundedDouble / 100;
            if (!JSONObject.NULL.equals(amountRefunded)) {
                data.setAmountRefunded(amountRefundedBy100);
            }

            Object errorReason = payment.get("error_reason");
            if (!JSONObject.NULL.equals(errorReason)) {
                data.setErrorReason(payment.get("error_reason"));
            }

            Object errorDescription = payment.get("error_description");
            if (!JSONObject.NULL.equals(errorDescription)) {
                data.setErrorDescription(payment.get("error_description"));
            }

            Object contact = payment.get("contact");
            if (!JSONObject.NULL.equals(contact)) {
                data.setContact(payment.get("contact"));
            }

            Object invoiceId = payment.get("invoice_id");
            if (!JSONObject.NULL.equals(invoiceId)) {
                data.setInvoiceId(payment.get("invoice_id"));
            }

            Object international = payment.get("international");
            if (!JSONObject.NULL.equals(international)) {
                data.setInternational(payment.get("international"));
            }

            Object amount = payment.get("amount");
            Double amountDouble = Double.parseDouble(amount.toString());
            Double amountBy100 = amountDouble / 100;
            if (!JSONObject.NULL.equals(amount)) {
                data.setAmount(amountBy100);
            }

            Object errorSource = payment.get("error_source");
            if (!JSONObject.NULL.equals(errorSource)) {
                data.setErrorSource(payment.get("error_source"));
            }

            Object errorStep = payment.get("error_step");
            if (!JSONObject.NULL.equals(errorStep)) {
                data.setErrorStep(payment.get("error_step"));
            }

            Object tax = payment.get("tax");
            Double taxDouble = Double.parseDouble(tax.toString());
            Double taxBy100 = taxDouble / 100;
            if (!JSONObject.NULL.equals(tax)) {
                data.setTax(taxBy100);
            }

            Object errorCode = payment.get("error_code");
            if (!JSONObject.NULL.equals(errorCode)) {
                data.setErrorCode(payment.get("errorCode"));
            }

            Object emailId = payment.get("email");
            if (!JSONObject.NULL.equals(emailId)) {
                data.setEmail(payment.get("email"));
            }

            data.setCreatedBy(razorpayOrderTransactionRequest.getUserId());
            data.setCreatedDate(dateUtil.getTimestamp());

            System.out.println("Data : " + data.toString());
            //auth_code here

            Long id = paymentRepository.saveOrderTransaction(data);
            System.out.println("Auth here 1 : " + razorpayOrderTransactionRequest.getSignature());
            System.out.println("ID : " + id);
            String orderTransactionId = edUtil.encrypt(String.valueOf(id));

            if (!orderTransactionId.equals("")) {

                OrderDetailDTO orderDetailData = new OrderDetailDTO();
                orderDetailData.setOrderDetailId(razorpayOrderTransactionRequest.getOrderDetailId());
                orderDetailData.setIsPaid(true);
                orderDetailData.setUpdatedBy(razorpayOrderTransactionRequest.getUserId());
                orderDetailData.setUpdatedDate(dateUtil.getTimestamp());

                boolean updateIsPaid = paymentRepository.updateOrderDetailIsPaid(orderDetailData);
                System.out.println("IsPaid : " + updateIsPaid);

                UsersDTO userData = userService.retrieveUserById(razorpayOrderTransactionRequest.getUserId());
                String mobileNo = userData.getMobileNumber();

                Integer ordersumaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(razorpayOrderTransactionRequest.getOrderSummaryId())));
                OrderSummaryDTO orderSummaryData = paymentRepository.fetchOrderSummary(ordersumaryId);
                Integer variantId = Integer.parseInt(edUtil.decrypt(String.valueOf(orderSummaryData.getVariantId())));
                VariantsDTO variantData = productRepository.fetchVariantDetails(variantId);
                String variantName = variantData.getVariantName();

                Integer modelId = Integer.parseInt(edUtil.decrypt(String.valueOf(variantData.getModelId())));
                ModelDTO modelData = productRepository.fetchModelById(modelId);
                String modelName = modelData.getModelName();

                String razorPayOrderId = razorpayOrderTransactionRequest.getRazorpayOrderId();
                String lastname = "";
                String fullName = "";
                if (userData.getLastName() != null) {
                    lastname = userData.getLastName();
                    fullName = userData.getFirstName() +' '+userData.getLastName();
                }
                else{
                    fullName = userData.getFirstName();
                }

                String isEmailSent;
                int amt = (int) Math.round(amountBy100);
                // int amt = Integer.parseInt(amount.toString());
                System.out.println("amt " + amt);
                if (updateIsPaid) {
                    String isSMSSent = "";
                    //Send OTP to user mobile
//                    String jsonInputString = "{\n"
//                            + "  \"flow_id\": \"" + smspaymentflowId + "\",\n"
//                            + "  \"sender\": \"" + smsSenderId + "\",\n"
//                            + "  \"mobiles\":\"91" + mobileNo + "\",\n"
//                            + "  \"VAR3\": \"" + modelName + "\",\n"
//                            + "  \"VAR4\": \"" + variantName + "\",\n"
//                            + "  \"VAR5\": \"" + razorPayOrderId + "\",\n"
//                            + "  \"VAR13\": \"" + "" + "\"\n"
//                            + "}";

                    String jsonInputString = "{\n"
                            + "  \"flow_id\": \"" + smspaymentflowId + "\",\n"
                            + "  \"sender\": \"" + smsSenderId + "\",\n"
                            + "  \"mobiles\":\"91" + mobileNo + "\",\n"
                            + "  \"VAR4\": \"" + variantName + "\",\n"
                            + "  \"VAR5\": \"" + razorPayOrderId + "\",\n"
                            + "  \"VAR17\": \"" + amt + "\"\n"
                            + "}";

                    isSMSSent = smsService.send(mobileNo, jsonInputString);
                    System.out.println("isSMSSent : " + isSMSSent);

                    //Deleting existing directory
                    boolean res = this.deleteDirectory("C:\\Invoice");
                    System.out.println("directory deleted :: " + res);

                    //uploading invoice to image kit dynamically by creating directory
                    Result pdfResult = getPDF(razorpayOrderTransactionRequest.getRazorpayPaymentId(), userData.getFirstName());
                    String attachmentPath1 = pdfResult.getUrl();
                    System.out.println("Real Path : " + attachmentPath1.toString());

                    String dashBoardUrl = "<a href=https://www.boommotors.com/Platform/login>login</a>";
                    String attachmentPath = "http://www.africau.edu/images/default/sample.pdf";
                    String fileName = pdfResult.getName();

                    String jsonInputForEmailString = "{\n"
                            + "  \"to\": [\n"
                            + "  {\n"
                            + "    \"name\": \"" + userData.getFirstName() + "\",\n"
                            + "    \"email\": \"" + userData.getEmailId() + "\"\n"
                            + "  }  ],\n"
                            + "  \"from\": {\n"
                            + "    \"name\": \"" + emailFromName + "\", \n"
                            + "    \"email\": \"" + emailFromMailID + "\" \n"
                            + "   },\n"
                            + " \"attachments\": [\n"
                            + "    {\n"
                            + "      \"filePath\": \"" + attachmentPath1 + "\",\n"
                            + "      \"fileName\": \"" + fileName + "\"\n"
                            + "    }\n"
                            + "  ],"
                            + "  \"mail_type_id\": \"1\",\n"
                            + "  \"template_id\": \"" + emailAdvanceBookingTemplate + "\",\n"
                            + "  \"variables\": {	\n"
                            + "    \"VAR7\": \"" + fullName + "\", \n"
//                            + "    \"VAR8\": \"" + lastname + "\", \n"
                            + "    \"VAR4\": \"" + variantName + "\", \n"
                            + "    \"VAR17\": \"" + amt + "\", \n"
                            + "\"VAR5\": \"" + razorPayOrderId + "\", \n"
                            + "     \"VAR13\": \"" + dashBoardUrl + "\" },\n"
                            + "  \"authkey\": \"" + emailAuthKey + "\" \n"
                            + "}";

                    System.out.println("Input JSON for email : " + jsonInputForEmailString);

//As per new requirement send email on successful payment has been commented.
//Again uncommented on 16Nov21
//Again commented on 19Nov21 due to template aproval from client
//uncommented on 22Nov21 
                    isEmailSent = emailService.sendEmail(jsonInputForEmailString);
                    System.out.println("isEmailSent" + isEmailSent);

                    Boolean sentEmailToSales = false;
                    /// Sending Bulkenquiry email when Quantity is more than 1. On 17Dec2021  
                    if (orderSummaryData.getQuantity() > 1) {
                        sentEmailToSales = this.sendEmailToSales(orderSummaryData, userData);
                    }
                    System.out.println("sentEmailToSales : " + sentEmailToSales);
                }
            }
            return orderTransactionId;

        } catch (AppException | IOException | NumberFormatException | ParseException e) {
            logger.error("Responding with error. Message - {}", e.getMessage());
        }
        return null;
    }

    public boolean deleteDirectory(String path) throws IOException {
        boolean res = false;
        File dir = new File(path);
        if (dir.exists()) {
            String[] entries = dir.list();
            for (String s : entries) {
                File currentFile = new File(dir.getPath(), s);
                currentFile.delete();
            }

            res = dir.delete();
            return res;
        } else {
            System.out.println("directory does not exists!");
            return res;
        }

    }

    @Override
    public String saveOnlinePaymentOrderTransaction(RazorpayOnlinePaymentOrderTransactionRequest razorpayOnlinePaymentOrderTransactionRequest, Payment payment) {
        try {

            OrderTransactionDTO data = new OrderTransactionDTO();
            data.setOrderSummaryId(razorpayOnlinePaymentOrderTransactionRequest.getOrderSummaryId());
            data.setRazorpayPaymentId(razorpayOnlinePaymentOrderTransactionRequest.getRazorpayPaymentId());
            data.setRazorpayOrderId(razorpayOnlinePaymentOrderTransactionRequest.getRazorpayOrderId());
            data.setPaymentSuccess(1);
            Object status = payment.get("status");
            if (!JSONObject.NULL.equals(status)) {
                data.setStatus(payment.get("status"));
            }

            Object method = payment.get("method");
            if (!JSONObject.NULL.equals(method)) {
                data.setPaymentMethod(payment.get("method"));
            }
            Object cardId = payment.get("card_id");
            if (!JSONObject.NULL.equals(cardId)) {
                data.setCardId(payment.get("card_id"));
            }

            Object captured = payment.get("captured");
            if (!JSONObject.NULL.equals(captured)) {
                data.setCaptured(payment.get("captured"));
            }

            Object entity = payment.get("entity");
            if (!JSONObject.NULL.equals(entity)) {
                data.setEntity(payment.get("entity"));
            }

            Object currency = payment.get("currency");
            if (!JSONObject.NULL.equals(currency)) {
                data.setCurrency(payment.get("currency"));
            }

            Object description = payment.get("description");
            if (!JSONObject.NULL.equals(description)) {
                data.setDescription(payment.get("description"));
            }

            Object bank = payment.get("bank");
            if (!JSONObject.NULL.equals(bank)) {
                data.setBank(payment.get("bank"));
            }

            Object wallet = payment.get("wallet");
            if (!JSONObject.NULL.equals(wallet)) {
                data.setWallet(payment.get("wallet"));
            }

            Object vpa = payment.get("vpa");
            if (!JSONObject.NULL.equals(vpa)) {
                data.setVpa(payment.get("vpa"));
            }

            Object fee = payment.get("fee");
            Double feeDouble = Double.parseDouble(fee.toString());
            Double feeBy100 = feeDouble / 100;
            System.out.println("Fee Double : " + feeDouble);
            System.out.println("Fee By 100 : " + feeBy100);
            if (!JSONObject.NULL.equals(fee)) {
                data.setFee(feeBy100);
            }

            Object refundStatus = payment.get("refund_status");
            if (!JSONObject.NULL.equals(refundStatus)) {
                data.setRefundStatus(payment.get("refund_status"));
            }

            data.setAuthCode(razorpayOnlinePaymentOrderTransactionRequest.getSignature());

            Object amountRefunded = payment.get("amount_refunded");
            Double amountRefundedDouble = Double.parseDouble(amountRefunded.toString());
            Double amountRefundedBy100 = amountRefundedDouble / 100;
            if (!JSONObject.NULL.equals(amountRefunded)) {
                data.setAmountRefunded(amountRefundedBy100);
            }

            Object errorReason = payment.get("error_reason");
            if (!JSONObject.NULL.equals(errorReason)) {
                data.setErrorReason(payment.get("error_reason"));
            }

            Object errorDescription = payment.get("error_description");
            if (!JSONObject.NULL.equals(errorDescription)) {
                data.setErrorDescription(payment.get("error_description"));
            }

            Object contact = payment.get("contact");
            if (!JSONObject.NULL.equals(contact)) {
                data.setContact(payment.get("contact"));
            }

            Object invoiceId = payment.get("invoice_id");
            if (!JSONObject.NULL.equals(invoiceId)) {
                data.setInvoiceId(payment.get("invoice_id"));
            }

            Object international = payment.get("international");
            if (!JSONObject.NULL.equals(international)) {
                data.setInternational(payment.get("international"));
            }

            Object amount = payment.get("amount");
            Double amountDouble = Double.parseDouble(amount.toString());
            Double amountBy100 = amountDouble / 100;
            if (!JSONObject.NULL.equals(amount)) {
                data.setAmount(amountBy100);
            }

            Object errorSource = payment.get("error_source");
            if (!JSONObject.NULL.equals(errorSource)) {
                data.setErrorSource(payment.get("error_source"));
            }

            Object errorStep = payment.get("error_step");
            if (!JSONObject.NULL.equals(errorStep)) {
                data.setErrorStep(payment.get("error_step"));
            }

            Object tax = payment.get("tax");
            Double taxDouble = Double.parseDouble(tax.toString());
            Double taxBy100 = taxDouble / 100;
            if (!JSONObject.NULL.equals(tax)) {
                data.setTax(taxBy100);
            }

            Object errorCode = payment.get("error_code");
            if (!JSONObject.NULL.equals(errorCode)) {
                data.setErrorCode(payment.get("errorCode"));
            }

            Object emailId = payment.get("email");
            if (!JSONObject.NULL.equals(emailId)) {
                data.setEmail(payment.get("email"));
            }

            data.setCreatedBy(razorpayOnlinePaymentOrderTransactionRequest.getUserId());
            data.setCreatedDate(dateUtil.getTimestamp());

            System.out.println("Data : " + data.toString());
            //auth_code here

            Long id = paymentRepository.saveOrderTransaction(data);
            System.out.println("Auth here 1 : " + razorpayOnlinePaymentOrderTransactionRequest.getSignature());
            System.out.println("ID : " + id);
            String orderTransactionId = edUtil.encrypt(String.valueOf(id));

            if (!orderTransactionId.equals("")) {

//                OrderDetailDTO orderDetailRes = paymentRepository.fetchOrderDetailByRazorPayOrderId(razorpayOrderTransactionRequest.getRazorpayOrderId());
//                System.out.println("orderDetails : " + orderDetailRes.getOrderDetailId());
//                
//                
                OrderDetailDTO orderDetailData = new OrderDetailDTO();
                orderDetailData.setOrderDetailId(razorpayOnlinePaymentOrderTransactionRequest.getOrderDetailId());
                orderDetailData.setIsPaid(true);
                orderDetailData.setUpdatedBy(razorpayOnlinePaymentOrderTransactionRequest.getUserId());
                orderDetailData.setUpdatedDate(dateUtil.getTimestamp());

                boolean updateIsPaid = paymentRepository.updateOrderDetailIsPaid(orderDetailData);
                System.out.println("IsPaid : " + updateIsPaid);

                UsersDTO userData = userService.retrieveUserById(razorpayOnlinePaymentOrderTransactionRequest.getUserId());
                String mobileNo = userData.getMobileNumber();

                Integer ordersumaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(razorpayOnlinePaymentOrderTransactionRequest.getOrderSummaryId())));
                OrderSummaryDTO orderSummaryData = paymentRepository.fetchOrderSummary(ordersumaryId);

                orderDetailData.setOrderDetailId(razorpayOnlinePaymentOrderTransactionRequest.getOrderDetailId());
                orderDetailData.setIsPaid(true);
                orderDetailData.setUpdatedBy(razorpayOnlinePaymentOrderTransactionRequest.getUserId());
                orderDetailData.setUpdatedDate(dateUtil.getTimestamp());

                //update amount filed in order_summary table with adding existing amount and paid amount
                OrderSummaryDTO updateData = new OrderSummaryDTO();
                updateData.setOrderSummaryId(razorpayOnlinePaymentOrderTransactionRequest.getOrderSummaryId());
                updateData.setAmountPaid(orderSummaryData.getAmountPaid() + data.getAmount());
                updateData.setUpdatedBy(razorpayOnlinePaymentOrderTransactionRequest.getUserId());
                updateData.setUpdatedDate(dateUtil.getTimestamp());

                boolean updateAmountPaid = paymentRepository.updateOrderSummaryAmount(updateData);

                System.out.println("updateAmountPaid : " + updateAmountPaid);

                Integer variantId = Integer.parseInt(edUtil.decrypt(String.valueOf(orderSummaryData.getVariantId())));
                VariantsDTO variantData = productRepository.fetchVariantDetails(variantId);
                String variantName = variantData.getVariantName();

                Integer modelId = Integer.parseInt(edUtil.decrypt(String.valueOf(variantData.getModelId())));
                ModelDTO modelData = productRepository.fetchModelById(modelId);
                String modelName = modelData.getModelName();

                String razorPayOrderId = razorpayOnlinePaymentOrderTransactionRequest.getRazorpayOrderId();
                String lastname = "";
                if (userData.getLastName() != null) {
                    lastname = userData.getLastName();
                }

                //commenting this section because sms and email template are not created. now, uncomment once its created
                /*  String isEmailSent;

                if (updateIsPaid) {
                    String isSMSSent = "";
                    //Send OTP to user mobile
                    String jsonInputString = "{\n"
                            + "  \"flow_id\": \"" + smspaymentflowId + "\",\n"
                            + "  \"sender\": \"" + smsSenderId + "\",\n"
                            + "  \"mobiles\":\"91" + mobileNo + "\",\n"
                            + "  \"VAR3\": \"" + modelName + "\",\n"
                            + "  \"VAR4\": \"" + variantName + "\",\n"
                            + "  \"VAR5\": \"" + razorPayOrderId + "\",\n"
                            + "  \"VAR13\": \"" + "" + "\"\n"
                            + "}";

                    isSMSSent = smsService.send(mobileNo, jsonInputString);
                    System.out.println("isSMSSent : " + isSMSSent);

                    String dashBoardUrl = "<a href=http://13.233.151.51/Boom_Dev/mycontest>login</a>";
                    String attachmentPath = "https://www.boommotors.com/resources/img/bcity/bcity1.jpg";
                    String fileName = "order";

                    String jsonInputForEmailString = "{\n"
                            + "  \"to\": [\n"
                            + "  {\n"
                            + "    \"name\": \"" + userData.getFirstName() + "\",\n"
                            + "    \"email\": \"" + userData.getEmailId() + "\"\n"
                            + "  }  ],\n"
                            + "  \"from\": {\n"
                            + "    \"name\": \"" + emailFromName + "\", \n"
                            + "    \"email\": \"" + emailFromMailID + "\" \n"
                            + "   },\n"
                            + " \"attachments\": [\n"
                            + "    {\n"
                            + "      \"filePath\": \"" + attachmentPath + "\",\n"
                            + "      \"fileName\": \"" + fileName + "\"\n"
                            + "    }\n"
                            + "  ],"
                            + "  \"mail_type_id\": \"1\",\n"
                            + "  \"template_id\": \"" + emailAdvanceBookingTemplate + "\",\n"
                            + "  \"variables\": {	\n"
                            + "    \"VAR7\": \"" + userData.getFirstName() + "\", \n"
                            + "    \"VAR8\": \"" + lastname + "\", \n"
                            + "    \"VAR3\": \"" + modelName + "\", \n"
                            + "    \"VAR4\": \"" + variantName + "\", \n"
                            + "\"VAR5\": \"" + razorPayOrderId + "\", \n"
                            + "     \"VAR13\": \"" + dashBoardUrl + "\" },\n"
                            + "  \"authkey\": \"" + emailAuthKey + "\" \n"
                            + "}";

                    System.out.println("Input JSON for email : " + jsonInputForEmailString);

                    isEmailSent = emailService.sendEmail(jsonInputForEmailString);

                    System.out.println("isEmailSent" + isEmailSent);
                } */
            }
            return orderTransactionId;

        } catch (Exception e) {
            logger.error("Responding with error. Message - {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderDetailDTO retrieveOrderDetailByRazorPayOrderId(String razorpayOrderId) {

        try {
            System.out.println("ID : " + razorpayOrderId);

            return paymentRepository.fetchOrderDetailByRazorPayOrderId(razorpayOrderId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    public boolean updateOrderDetailIsPaid(OrderDetailDTO orderData) {

        try {
            OrderDetailDTO data = new OrderDetailDTO();
            data.setOrderDetailId(orderData.getOrderDetailId());
            data.setIsPaid(true);

            return paymentRepository.updateOrderDetailIsPaid(data);

        } catch (Exception ex) {
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public PaymentDetailsDTO retrievePaymentDetails(String paymentId) {

        try {
            return paymentRepository.fetchPaymentDetails(paymentId);
        } catch (Exception ex) {
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public OrderTransactionDTO retrieveOrderTransaction(String razorpayPaymentId) {
        try {
            return paymentRepository.fetchOrderTransaction(razorpayPaymentId);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Responding with error. Message - {}", ex.getMessage());
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public Result getPDF(String razorpayPaymentId, String firstName) throws IOException, ParseException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        //HttpServletResponse response = (HttpServletResponse) org.apache.catalina.core.ApplicationFilterChain.getLastServicedResponse();

        OrderTransactionDTO result = paymentService.retrieveOrderTransaction(razorpayPaymentId);
        System.out.println("ORDER ID : " + result.getRazorpayOrderId());

        System.out.println("Transaction Data : " + result.getEmail());

        /* Do Business Logic*/
        Invoice invoice = IndexHelper.getInvoice(result);

        System.out.println("Invoice Data : " + invoice.getEmail());

        /* Create HTML using Thymeleaf template Engine */
        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("orderEntry", invoice);
        String orderHtml = templateEngine.process("index", context);

        /* Setup Source and target I/O streams */
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();

        boolean fileCreataed = false;
        String filePath = "C:\\Invoice\\";
        String fullPath = "";
        String fileName = "";
        File file;
        try {
            file = new File("C:\\Invoice");
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory is created!");
                    fileCreataed = true;
                } else {
                    fileCreataed = false;
                    System.out.println("Failed to create directory!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (fileCreataed) {
            Integer i = (int) (new Date().getTime() / 1000);
            System.out.println("file created");
            //fileName = firstName + "_" + i.toString() + ".pdf";
            //fullPath = filePath + firstName;
            try {
                OutputStream out = new FileOutputStream("C:\\Invoice\\receipt.pdf");
                out.write(bytes);
                out.close();
                System.out.println("out" + out);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            
//            

        }

        ImageKit imageKit = ImageKit.getInstance();
        Configuration config = Utils.getSystemConfig(PaymentServiceImpl.class);
        imageKit.setConfig(config);

        String base64 = Utils.fileToBase64(new File("C:\\Invoice\\receipt.pdf"));
        //System.out.println("Full Path : " + fullPath);
        //System.out.println("base64 Value : " + base64);
        FileCreateRequest fileCreateRequest = new FileCreateRequest(base64, "receipt.pdf");
        fileCreateRequest.setFileName("receipt.pdf");
        System.out.println("FileCreateRequest : " + fileCreateRequest);
        fileCreateRequest.setFolder("Booking_Advance_Receipt");
        fileCreateRequest.setUseUniqueFileName(true);
        Result resultPdf = ImageKit.getInstance().upload(fileCreateRequest);

        System.out.println("Result from imagekit : " + resultPdf);

        String pdfUrl = resultPdf.getUrl();
        System.out.println("Path : " + pdfUrl);
        return resultPdf;

    }

    public void save(MultipartFile file, String uploadPath) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init(uploadPath);
            }
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public void init(String uploadPath) {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (Exception e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    @Override
    public String refundFullAmount(OrderTransactionDTO transRes) {

        try {
            String paymentId = transRes.getRazorpayPaymentId();
            Double amount = transRes.getAmount() * 100;
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            // Full Refund
            Refund refund = razorpay.Payments.refund(paymentId);

//            // Partial Refund
//            JSONObject refundRequest = new JSONObject();
//            refundRequest.put("amount", amount); // Amount should be in paise
//            refund = razorpay.Payments.refund(paymentId, refundRequest);
            System.out.println("refund " + refund.toString());
            return refund.get("status");
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public boolean sendEmailToSales(OrderSummaryDTO orderSummaryData, UsersDTO userData) {
        String isEmailSent;

        try {
            String variantName = orderSummaryData.getVariantName();
            String lastName = "";
           String fullName = "";
                if (userData.getLastName() != null) {
                    lastName = userData.getLastName();
                    fullName = userData.getFirstName() +' '+userData.getLastName();
                }
                else{
                    fullName = userData.getFirstName();
                }
            String emailVerified = "";
            if (userData.getEmailVerified()) {
                emailVerified = "Verified";
            } else {
                emailVerified = "Not Verified";
            }

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
                    + "	\"VAR4\": \"" + variantName + "\",\n"
                    + "	\"VAR9\": \"" + userData.getMobileNumber() + "\",\n"
                    + "	\"VAR10\": \"" + userData.getEmailId() + "\",\n"
                    + "	\"VAR11\": \"" + emailVerified + "\",\n"
                    + "	\"VAR14\":\"" + orderSummaryData.getQuantity() + "\"\n"
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
}
