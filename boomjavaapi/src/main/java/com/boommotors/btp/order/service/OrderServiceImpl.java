/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.service;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dao.OrderDAO;
import com.boommotors.btp.order.dto.EVStatusDTO;
import com.boommotors.btp.order.dto.FinanceStatusDTO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.boommotors.btp.order.dto.OrderPartsColourDTO;
import com.boommotors.btp.order.dto.OrderAccessoriesDTO;
import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.order.dto.OrderEVStatusDTO;
import com.boommotors.btp.order.dto.OrderFinanceStatusDTO;
import com.boommotors.btp.order.dto.OrderSummaryAndRazorpayDTO;
import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.order.dto.OrderTransactionStatusDTO;
import com.boommotors.btp.order.dto.TransactionDetailsDTO;
import com.boommotors.btp.order.dto.TransactionStatusDTO;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import com.boommotors.btp.payload.OrderRequest;
import static com.boommotors.btp.payment.controller.PaymentController.getHash;
import com.boommotors.btp.payment.payload.RazorpayOrderTransactionRequest;
import com.boommotors.btp.payment.service.PaymentService;
import com.boommotors.btp.product.dao.ProductDAO;
import com.boommotors.btp.product.dto.VariantStatePriceDTO;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author NandiniC
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderRepository;

    @Autowired
    ProductDAO productRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Value("${razorpay.keyId}")
    private String razorpayKeyId;

    @Value("${razorpay.keySecret}")
    private String razorpayKeySecret;

    @Override
    public String createOrderSummary(OrderRequest orderRequest) throws EtAuthException {
        try {
            Long newOrderSummaryID;

            OrderSummaryDTO orderSummary = new OrderSummaryDTO();
            orderSummary.setUserId(orderRequest.getUserId());
            orderSummary.setVariantId(orderRequest.getVariantId());
            orderSummary.setVariantName(orderRequest.getVariantName());
            orderSummary.setColourName(orderRequest.getColourName());
            orderSummary.setFinanceEmi(orderRequest.getFinanceEmi());
            orderSummary.setInsuranceType(orderRequest.getInsuranceType());
            orderSummary.setInsuranceAmount(orderRequest.getInsuranceAmount());
            orderSummary.setWarrantyAmount(orderRequest.getWarrantyAmount());
            orderSummary.setDelivery_type(orderRequest.getDeliveryType());
            orderSummary.setPincode(orderRequest.getPincode());
            orderSummary.setCity(orderRequest.getCity());
            orderSummary.setState(orderRequest.getState());
            orderSummary.setCountry(orderRequest.getCountry());
            orderSummary.setIsOutOfCoverage(orderRequest.getIsOutOfCoverage());
            orderSummary.setQuantity(orderRequest.getQuantity());
            orderSummary.setRfpSent(orderRequest.getRfpSent());
            orderSummary.setTotalAmount(orderRequest.getTotalAmount());
            orderSummary.setAdvanceAmount(orderRequest.getAdvanceAmount());
            orderSummary.setAmountPaid(orderRequest.getAmountPaid());
            orderSummary.setPackageName(orderRequest.getPackageName());
            orderSummary.setColourAmount(orderRequest.getColourAmount());
            orderSummary.setExShowroom(orderRequest.getExShowroom());
            orderSummary.setPackageAmt(orderRequest.getPackageAmt());
            orderSummary.setSubTotal(orderRequest.getSubTotal());
            orderSummary.setGstAmt(orderRequest.getGstAmt());
            orderSummary.setGrossTotal(orderRequest.getGrossTotal());
            orderSummary.setSubsidyAmt(orderRequest.getSubsidyAmt());
            orderSummary.setTenure(orderRequest.getTenure());
            orderSummary.setExchange(orderRequest.getExchange());

            // Inserting order summary data
            newOrderSummaryID = orderRepository.createOrderSummary(orderSummary);

            //if user has choosen for custom colours insert into order Parts Colour table
            if (newOrderSummaryID > 0 && orderRequest.getColourName() == "") {

                String[] myArray = {orderRequest.getChasisColourID(), orderRequest.getBodyMudGuardColourID(), orderRequest.getMaskHandleBarColourID(), orderRequest.getShocksColourID(), orderRequest.getWheelRimsColourID()};

                for (int i = 0; i < myArray.length; i++) {
                    OrderPartsColourDTO orderPartsColour = new OrderPartsColourDTO();
                    orderPartsColour.setOrderSummaryId(newOrderSummaryID.toString());
                    orderPartsColour.setVariantPartsColourId(myArray[i]);
                    orderPartsColour.setCreatedBy(orderRequest.getUserId());
                    // Inserting order Parts Colour data
                    orderRepository.createOrderPartColour(orderPartsColour);

                }

            }
            if (newOrderSummaryID > 0 && orderRequest.getModelAccessories() != null && orderRequest.getModelAccessories() != "") {

                String ModelAccessories = orderRequest.getModelAccessories();
                String[] ModelAccessoriesArray = ModelAccessories.split(",");

                System.out.println("ModelAccessoriesArray : " + ModelAccessoriesArray);
                for (int i = 0; i < ModelAccessoriesArray.length; i++) {
                    OrderAccessoriesDTO orderAccessories = new OrderAccessoriesDTO();
                    orderAccessories.setOrderSummaryId(newOrderSummaryID.toString());
                    orderAccessories.setModelAccessoriesId(ModelAccessoriesArray[i]);
                    orderAccessories.setCreatedBy(orderRequest.getUserId());

                    // Inserting order Accessories data
                    orderRepository.createOrderAccessories(orderAccessories);
                }

            }
            return edUtil.encrypt(String.valueOf(newOrderSummaryID));

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    @Override
    public List<OrderSummaryAndRazorpayDTO> retrieveOrderSummaryByUserId(String userId) {
        try {
            List<OrderSummaryAndRazorpayDTO> summaryData;
            Integer id = Integer.parseInt(edUtil.decrypt(String.valueOf(userId)));

            summaryData = orderRepository.fetchOrderSummaryByUserId(id);

            if (summaryData != null && !summaryData.isEmpty()) {

                for (int i = 0; i < summaryData.size(); i++) {
                    int summaryId = Integer.parseInt(edUtil.decrypt(String.valueOf(summaryData.get(i).getOrderSummaryId())));

                    List<TransactionDetailsDTO> transData = orderRepository.fetchPaymentDetails(summaryId);
                    summaryData.get(i).setTransactionDetails(transData);
                }
                return summaryData;
            }

            return null;

        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public List<EVStatusDTO> retrieveOrderSummaryEVStatusByUserIdForStatus(String userId) {
        try {
            // EVStatusDTO  evStatusDTO = new EVStatusDTO();
            List<OrderSummaryDTO> result = new ArrayList<>();
            Integer id = Integer.parseInt(edUtil.decrypt(String.valueOf(userId)));
            System.out.println("User id : " + edUtil.encrypt(String.valueOf(101)));
            result = orderRepository.fetchOrderSummaryByUserIdForStatus(id);

            List<EVStatusDTO> finalResult = new ArrayList<>();

            if (!result.isEmpty()) {

                for (int i = 0; i < result.size(); i++) {
                    EVStatusDTO resultData = new EVStatusDTO();
                    System.out.println("ID : " + result.get(i).getOrderSummaryId());
                    List<OrderEVStatusDTO> res = retrieveOrderEVStatus(result.get(i).getOrderSummaryId());
                    if (res != null) {
                        resultData.setOrderEvStatus(res);
                        resultData.setOrderSummaryId(result.get(i).getOrderSummaryId());
                        finalResult.add(resultData);

                    }
                    int orderSummayid = Integer.parseInt(edUtil.decrypt(result.get(i).getOrderSummaryId()));
                    System.out.println("OrderSummary ID : " + orderSummayid);
                }
            }

            return finalResult;

        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    public List<OrderEVStatusDTO> retrieveOrderEVStatus(String orderSummaryId) {
        try {
            int id = Integer.parseInt(edUtil.decrypt(orderSummaryId));
            return orderRepository.fetchOrderEVStatus(id);
        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public List<FinanceStatusDTO> retrieveOrderSummaryFinanceStatusByUserIdForStatus(String userId) {
        try {
            List<OrderSummaryDTO> result = new ArrayList<>();
            Integer id = Integer.parseInt(edUtil.decrypt(String.valueOf(userId)));
            System.out.println("User id : " + edUtil.encrypt(String.valueOf(101)));
            result = orderRepository.fetchOrderSummaryByUserIdForStatus(id);

            List<FinanceStatusDTO> finalResult = new ArrayList<>();

            if (!result.isEmpty()) {

                for (int i = 0; i < result.size(); i++) {
                    FinanceStatusDTO resultData = new FinanceStatusDTO();
                    System.out.println("ID : " + result.get(i).getOrderSummaryId());
                    List<OrderFinanceStatusDTO> res = retrieveOrderFinanceStatus(result.get(i).getOrderSummaryId());
                    if (res != null) {
                        resultData.setOrderFinanceStatus(res);
                        resultData.setOrderSummaryId(result.get(i).getOrderSummaryId());
                        finalResult.add(resultData);
                    }
                    int orderSummayid = Integer.parseInt(edUtil.decrypt(result.get(i).getOrderSummaryId()));
                    System.out.println("OrderSummary ID : " + orderSummayid);
                }
            }
            //System.out.println("Data : " + finalResult.getOrderEvStatus().get(0).getEvStatusId());
            return finalResult;

        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    public List<OrderFinanceStatusDTO> retrieveOrderFinanceStatus(String orderSummaryId) {
        try {
            int id = Integer.parseInt(edUtil.decrypt(orderSummaryId));
            return orderRepository.fetchOrderFinanceStatus(id);
        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public List<TransactionStatusDTO> retrieveOrderSummaryTransactionStatusByUserIdForStatus(String userId) {
        try {
            List<OrderSummaryDTO> result = new ArrayList<>();
            Integer id = Integer.parseInt(edUtil.decrypt(String.valueOf(userId)));
            System.out.println("User id : " + edUtil.encrypt(String.valueOf(101)));
            result = orderRepository.fetchOrderSummaryByUserIdForStatus(id);

            List<TransactionStatusDTO> finalResult = new ArrayList<>();

            if (!result.isEmpty()) {

                for (int i = 0; i < result.size(); i++) {
                    TransactionStatusDTO resultData = new TransactionStatusDTO();
                    System.out.println("ID : " + result.get(i).getOrderSummaryId());
                    List<OrderTransactionStatusDTO> res = retrieveOrderTransactionStatus(result.get(i).getOrderSummaryId());
                    if (res != null) {
                        resultData.setOrderTransactionStatus(res);
                        resultData.setOrderSummaryId(result.get(i).getOrderSummaryId());
                        finalResult.add(resultData);
                    }
                    int orderSummayid = Integer.parseInt(edUtil.decrypt(result.get(i).getOrderSummaryId()));
                    System.out.println("OrderSummary ID : " + orderSummayid);
                }
            }
            //System.out.println("Data : " + finalResult.getOrderEvStatus().get(0).getEvStatusId());
            return finalResult;

        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    public List<OrderTransactionStatusDTO> retrieveOrderTransactionStatus(String orderSummaryId) {
        try {
            int id = Integer.parseInt(edUtil.decrypt(orderSummaryId));
            return orderRepository.fetchOrderTransactionStatus(id);
        } catch (AppException | NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public void createOrderStatus(String encryptedOrderSummaryId, String encryptedUserId) throws EtAuthException {
        try {
            //create Order EV status
            OrderEVStatusDTO orderEVStatus = new OrderEVStatusDTO();

            orderEVStatus.setOrderSummaryId(encryptedOrderSummaryId);
            orderEVStatus.setEvStatusId("1");
            orderEVStatus.setCreatedBy(encryptedUserId);

            orderRepository.createOrderEvStatus(orderEVStatus);

            //create Order Finance status
            OrderFinanceStatusDTO orderFinanceStatus = new OrderFinanceStatusDTO();

            orderFinanceStatus.setOrderSummaryId(encryptedOrderSummaryId);
            orderFinanceStatus.setFinanceStatusId("1");
            orderFinanceStatus.setCreatedBy(encryptedUserId);

            orderRepository.createOrderFinanceStatus(orderFinanceStatus);

            //create Order Transaction status
            OrderTransactionStatusDTO orderTransactionStatus = new OrderTransactionStatusDTO();

            orderTransactionStatus.setOrderSummaryId(encryptedOrderSummaryId);
            orderTransactionStatus.setTransactionStatusId("1");
            orderTransactionStatus.setCreatedBy(encryptedUserId);

            orderRepository.createOrderTransactionStatus(orderTransactionStatus);
        } catch (EtAuthException ex) {
        }

    }

    @Override
    public void createOrderTransactionStatus(String encryptedOrderSummaryId, String encryptedUserId) throws EtAuthException {
        try {
            //create Order Transaction status
            OrderTransactionStatusDTO orderTransactionStatus = new OrderTransactionStatusDTO();

            orderTransactionStatus.setOrderSummaryId(encryptedOrderSummaryId);
            orderTransactionStatus.setTransactionStatusId("6");
            orderTransactionStatus.setCreatedBy(encryptedUserId);

            orderRepository.createOrderTransactionStatus(orderTransactionStatus);
        } catch (EtAuthException ex) {
        }
    }

    @Override
    public Boolean updateOrderSummary(OrderRequest orderRequest) {
        Boolean res = false;
        try {
            OrderSummaryDTO orderSummary = new OrderSummaryDTO();
            orderSummary.setOrderSummaryId(orderRequest.getOrderSummaryId());
            int id = Integer.parseInt(edUtil.decrypt(orderRequest.getOrderSummaryId()));
            OrderSummaryDTO sumRes = orderRepository.fetchOrderDetailsBySummaryId(id);
            int variantId = Integer.parseInt(edUtil.decrypt(orderRequest.getVariantId()));
            if (sumRes != null) {
                List<VariantStatePriceDTO> varRes = productRepository.fetchVariantStatePrice(variantId, orderRequest.getState());
                if (!varRes.isEmpty()) {
                    System.out.println("(varRes :: " + varRes.get(0).getBasePrice());
                    orderSummary.setExShowroom(varRes.get(0).getBasePrice());
                    orderSummary.setGrossTotal(varRes.get(0).getBasePrice());
                    orderSummary.setSubTotal(varRes.get(0).getBasePrice());
                    orderSummary.setTotalAmount(varRes.get(0).getBasePrice());
                } else {
                    orderSummary.setExShowroom(orderRequest.getTotalAmount());
                    orderSummary.setGrossTotal(orderRequest.getTotalAmount());
                    orderSummary.setSubTotal(orderRequest.getTotalAmount());
                    orderSummary.setTotalAmount(orderRequest.getTotalAmount());
                }

                orderSummary.setUserId(orderRequest.getUserId());
                orderSummary.setUpdatedBy(orderRequest.getOemUserId());
                orderSummary.setVariantId(orderRequest.getVariantId());
                orderSummary.setVariantName(orderRequest.getVariantName());
                orderSummary.setColourName(orderRequest.getColourName());
                res = orderRepository.updateOrderSummary(orderSummary);
            }
            return res;
        } catch (AppException | NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public String orderCancellation(OrderRequest orderRequest) {
        String res = "";
        String refundRes = "";
        try {
            OrderTransactionDTO transData = new OrderTransactionDTO();
            OrderEVStatusDTO evStatus = new OrderEVStatusDTO();
            OrderTransactionStatusDTO transStatus = new OrderTransactionStatusDTO();

            int id = Integer.parseInt(edUtil.decrypt(orderRequest.getOrderSummaryId()));
            OrderTransactionDTO transRes = orderRepository.fetchOrderTransaction(id);
            if (transRes != null) {
                transData.setOrderSummaryId(orderRequest.getOrderSummaryId());
                transData.setRefundStatus("true");
                transData.setAmountRefunded(transRes.getAmount());
                transData.setUpdatedBy(orderRequest.getOemUserId());

                //////// calling refund method     
                refundRes = paymentService.refundFullAmount(transRes);
                System.out.println("refundRes " + refundRes);

                if ("processed".equals(refundRes)) {
                    boolean updateRes = orderRepository.updateOrderTransaction(transData);

                    //// creating record in Order_ev_status table for order canncellation(status -5)
                    evStatus.setEvStatusId("5");
                    evStatus.setOrderSummaryId(orderRequest.getOrderSummaryId());
                    evStatus.setCreatedBy(orderRequest.getOemUserId());
                    long statusId = orderRepository.createOrderEvStatus(evStatus);

                    //// creating record in Order_transaction_status table for order canncellation(status - 13)
                    transStatus.setTransactionStatusId("13");
                    transStatus.setOrderSummaryId(orderRequest.getOrderSummaryId());
                    transStatus.setCreatedBy(orderRequest.getOemUserId());

                    long transId = orderRepository.createOrderTransactionStatus(transStatus);
                    System.out.println("update res : " + res + " ev create res: " + statusId + " transId: " + transId);
                    if (updateRes == true && statusId != 0 && transId != 0) {
                        res = refundRes;
                    } else {
                        res = "Issue occured while updating changes to tables.";
                    }
                } else {
                    return refundRes;
                }
            }
            return res;
        } catch (AppException | EtAuthException | NumberFormatException ex) {
            return ex.getMessage();
        }
    }

    @Override
    public void autoUpdateOrderTransactionTable() {
        RazorpayOrderTransactionRequest request = new RazorpayOrderTransactionRequest();
        List<OrderDetailDTO> detailRes;
        Timestamp fromDate = dateUtil.getYesterdaysTimestamp();
        Timestamp toDate = dateUtil.getTimestamp();
        System.out.println("fromDate : " + fromDate + " toDate : " + toDate);
        try {
            //Fetching failed order details from order_detail table
            detailRes = orderRepository.fetchFailedOrderDetails(fromDate, toDate);
            if (detailRes != null && !detailRes.isEmpty()) {
                System.out.println("order detail table Res : " + detailRes.toString());

                for (int i = 0; i < detailRes.size(); i++) {

                    //Fetching order summary details based from order_summary table
                    Integer sumId = Integer.parseInt(edUtil.decrypt(detailRes.get(i).getOrderSummaryId()));
                    OrderSummaryDTO sumRes = orderRepository.fetchOrderDetailsBySummaryId(sumId);
                    if (sumRes != null) {

                        request.setOrderDetailId(detailRes.get(i).getOrderDetailId());
                        request.setOrderSummaryId(detailRes.get(i).getOrderSummaryId());
                        request.setRazorpayOrderId(detailRes.get(i).getRazorpayOrderId());
                        request.setColourName(sumRes.getColourName());
                        request.setUserId(sumRes.getUserId());
                        request.setVariantName(sumRes.getVariantName());
                        request.setVariantId(sumRes.getVariantId());

                        //Fetching Payments for an order
                        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
                        List<Payment> payments = razorpay.Orders.fetchPayments(detailRes.get(i).getRazorpayOrderId());
                        if (payments != null && !payments.isEmpty()) {
                            for (int j = 0; j < payments.size(); j++) {
                                String status = payments.get(j).get("status");
                                Object captured = payments.get(j).get("captured");

                                if ((status.equalsIgnoreCase("captured")) && (captured.equals(true))) {
                                    String payload = detailRes.get(i).getRazorpayOrderId() + '|' + payments.get(j).get("id");
                                    String actualSignature = getHash(payload, razorpayKeySecret);
                                    request.setRazorpayPaymentId(payments.get(j).get("id"));
                                    request.setSignature(actualSignature);
                                    System.out.println("getRazorpayOrderId: " + detailRes.get(i).getRazorpayOrderId());
                                    String res = this.saveOrderTransaction(request);
                                    System.out.println("save res : " + res + " time : " + dateUtil.getTimestamp());

                                } else {
                                    System.out.println("payment not captured" + " time : " + dateUtil.getTimestamp());
                                }
                            }
                        } else {
                            System.out.println("Payment details are not found in razorpay!" + " time : " + dateUtil.getTimestamp());
                        }
                        Thread.sleep(4000);
                    }
                }
            } else {
                System.out.println("No records found in order detail table!");
            }

        } catch (AppException | NumberFormatException | InterruptedException | RazorpayException ex) {
            ex.printStackTrace();
        }
    }

    public String saveOrderTransaction(RazorpayOrderTransactionRequest razorpayOrderTransactionRequest) {
        String res;
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            Payment payment = razorpay.Payments.fetch(razorpayOrderTransactionRequest.getRazorpayPaymentId());
            System.out.println("Payment details : " + payment);
            res = paymentService.saveOrderTransaction(razorpayOrderTransactionRequest, payment);
            return res;
        } catch (RazorpayException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
