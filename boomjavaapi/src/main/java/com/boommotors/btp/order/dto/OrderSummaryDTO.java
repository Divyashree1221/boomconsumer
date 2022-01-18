/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author NandiniC
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderSummaryDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

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
    private String delivery_type;

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
    
    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    
    
    @JsonProperty("package_name")
    private String packageName;
    
    @JsonProperty("colour_amount")
    private Double colourAmount;
    
    @JsonProperty("ex_showroom")
    private Double exShowroom ;
    
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
    
    @JsonProperty("tenure")
    private Integer tenure;
    
    @JsonProperty("exchange")
    private String exchange;

}
