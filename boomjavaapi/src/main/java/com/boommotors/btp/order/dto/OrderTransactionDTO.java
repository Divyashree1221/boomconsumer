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
 * @author Ramya
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderTransactionDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("order_transactions_id")
    private String orderTransactionsId;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("razorpay_payment_id")
    private String razorpayPaymentId;

    @JsonProperty("razorpay_order_id")
    private String razorpayOrderId;

    @JsonProperty("payment_success")
    private Integer paymentSuccess;

    @JsonProperty("status")
    private String status;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("captured")
    private Boolean captured;

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;

    @JsonProperty("bank")
    private String bank;

    @JsonProperty("wallet")
    private String wallet;

    @JsonProperty("vpa")
    private String vpa;

    @JsonProperty("fee")
    private Double fee;

    @JsonProperty("refund_status")
    private String refundStatus;

    @JsonProperty("auth_code")
    private String authCode;

    @JsonProperty("amount_refunded")
    private Double amountRefunded;

    @JsonProperty("error_reason")
    private String errorReason;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("invoice_id")
    private String invoiceId;

    @JsonProperty("international")
    private Boolean international;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("error_source")
    private String errorSource;

    @JsonProperty("error_step")
    private String errorStep;

    @JsonProperty("tax")
    private Double tax;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("razorpay_transaction_id")
    private String razorpayTransactionId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("email_id")
    private String email;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

}
