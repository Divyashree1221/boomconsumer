/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author NandiniC
 */
public class OrderRequest {
    
    @JsonProperty("oem_user_id")
    private String oemUserId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("colour_name")
    private String colourName;

    @JsonProperty("finance_emi")
    private Double financeEmi;

    @JsonProperty("insurance_type")
    private String insuranceType;

    @JsonProperty("insurance_amount")
    private Double insuranceAmount;

    @JsonProperty("warranty_amount")
    private Double warrantyAmount;

    @JsonProperty("delivery_type")
    private String deliveryType;

    @JsonProperty("pincode")
    private String pincode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("is_out_of_coverage")
    private Boolean isOutOfCoverage;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("rfp_sent")
    private Boolean rfpSent;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("advance_amount")
    private Double advanceAmount;

    @JsonProperty("amount_paid")
    private Double amountPaid;

    @JsonProperty("package_name")
    private String packageName;

    @JsonProperty("colour_amount")
    private Double colourAmount;

    private String chasisColourID;

    private String bodyMudGuardColourID;

    private String maskHandleBarColourID;

    private String shocksColourID;

    private String wheelRimsColourID;

    private String modelAccessories;

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

    @JsonProperty("ex_showroom")
    private Double exShowroom;

    @JsonProperty("package_amt")
    private Double packageAmt;

    @JsonProperty("sub_total")
    private Double subTotal;

    @JsonProperty("gst_amt")
    private Double gstAmt;

    @JsonProperty("gross_total")
    private Double grossTotal;

    @JsonProperty("subsidy_amt")
    private Double subsidyAmt;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("model_id")
    private String modelId;

    @JsonProperty("tenure")
    private Integer tenure;

    @JsonProperty("exchange")
    private String exchange;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public Double getExShowroom() {
        return exShowroom;
    }

    public void setExShowroom(Double exShowroom) {
        this.exShowroom = exShowroom;
    }

    public Double getPackageAmt() {
        return packageAmt;
    }

    public void setPackageAmt(Double packageAmt) {
        this.packageAmt = packageAmt;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getGstAmt() {
        return gstAmt;
    }

    public void setGstAmt(Double gstAmt) {
        this.gstAmt = gstAmt;
    }

    public Double getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(Double grossTotal) {
        this.grossTotal = grossTotal;
    }

    public Double getSubsidyAmt() {
        return subsidyAmt;
    }

    public void setSubsidyAmt(Double subsidyAmt) {
        this.subsidyAmt = subsidyAmt;
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

    public String getModelAccessories() {
        return modelAccessories;
    }

    public void setModelAccessories(String modelAccessories) {
        this.modelAccessories = modelAccessories;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Double getFinanceEmi() {
        return financeEmi;
    }

    public void setFinanceEmi(Double financeEmi) {
        this.financeEmi = financeEmi;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Double getWarrantyAmount() {
        return warrantyAmount;
    }

    public void setWarrantyAmount(Double warrantyAmount) {
        this.warrantyAmount = warrantyAmount;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getIsOutOfCoverage() {
        return isOutOfCoverage;
    }

    public void setIsOutOfCoverage(Boolean isOutOfCoverage) {
        this.isOutOfCoverage = isOutOfCoverage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getRfpSent() {
        return rfpSent;
    }

    public void setRfpSent(Boolean rfpSent) {
        this.rfpSent = rfpSent;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(Double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Double getColourAmount() {
        return colourAmount;
    }

    public void setColourAmount(Double colourAmount) {
        this.colourAmount = colourAmount;
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

    public String getOrderSummaryId() {
        return orderSummaryId;
    }

    public void setOrderSummaryId(String orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    
    
    public String getOemUserId() {
        return oemUserId;
    }

    public void setOemUserId(String oemUserId) {
        this.oemUserId = oemUserId;
    }
    
}
