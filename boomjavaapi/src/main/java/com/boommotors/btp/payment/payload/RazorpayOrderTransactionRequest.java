/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Ramya
 */
public class RazorpayOrderTransactionRequest {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("razorpay_payment_id")
    private String razorpayPaymentId;

    @JsonProperty("razorpay_order_id")
    private String razorpayOrderId;

    @JsonProperty("order_detail_id")
    private String orderDetailId;

    @JsonProperty("signature")
    private String signature;

    //inserted then below fields to insert contest data during payment success
    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("colour_name")
    private String colourName;

    private String chasisColourID;

    private String bodyMudGuardColourID;

    private String maskHandleBarColourID;

    private String shocksColourID;

    private String wheelRimsColourID;

    private Boolean participateInContest;

    @JsonProperty("template_name")
    private String templateName;

    @JsonProperty("user_nickname")
    private String userNickname;

    @JsonProperty("base_image")
    private String baseImage;

    @JsonProperty("body_image")
    private String bodyImage;

    @JsonProperty("frame_image")
    private String frameImage;

    @JsonProperty("mask_image")
    private String maskImage;

    @JsonProperty("rims_image")
    private String rimsImage;

    @JsonProperty("shock_image")
    private String shockImage;

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }

    public String getChasisColourID() {
        return chasisColourID;
    }

    public void setChasisColourID(String chasisColourID) {
        this.chasisColourID = chasisColourID;
    }

    public String getBodyMudGuardColourID() {
        return bodyMudGuardColourID;
    }

    public void setBodyMudGuardColourID(String bodyMudGuardColourID) {
        this.bodyMudGuardColourID = bodyMudGuardColourID;
    }

    public String getMaskHandleBarColourID() {
        return maskHandleBarColourID;
    }

    public void setMaskHandleBarColourID(String maskHandleBarColourID) {
        this.maskHandleBarColourID = maskHandleBarColourID;
    }

    public String getShocksColourID() {
        return shocksColourID;
    }

    public void setShocksColourID(String shocksColourID) {
        this.shocksColourID = shocksColourID;
    }

    public String getWheelRimsColourID() {
        return wheelRimsColourID;
    }

    public void setWheelRimsColourID(String wheelRimsColourID) {
        this.wheelRimsColourID = wheelRimsColourID;
    }

    public Boolean getParticipateInContest() {
        return participateInContest;
    }

    public void setParticipateInContest(Boolean participateInContest) {
        this.participateInContest = participateInContest;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
    }

    public String getBodyImage() {
        return bodyImage;
    }

    public void setBodyImage(String bodyImage) {
        this.bodyImage = bodyImage;
    }

    public String getFrameImage() {
        return frameImage;
    }

    public void setFrameImage(String frameImage) {
        this.frameImage = frameImage;
    }

    public String getMaskImage() {
        return maskImage;
    }

    public void setMaskImage(String maskImage) {
        this.maskImage = maskImage;
    }

    public String getRimsImage() {
        return rimsImage;
    }

    public void setRimsImage(String rimsImage) {
        this.rimsImage = rimsImage;
    }

    public String getShockImage() {
        return shockImage;
    }

    public void setShockImage(String shockImage) {
        this.shockImage = shockImage;
    }

    public String getUserId() {
        return userId;
    }

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public String getSignature() {
        return signature;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

}
