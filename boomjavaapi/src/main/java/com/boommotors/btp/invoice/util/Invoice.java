package com.boommotors.btp.invoice.util;

import java.sql.Timestamp;
import lombok.Data;

import java.util.List;

@Data
public class Invoice {
    private String paymentId;
    private String amount;
    private String email;
    private String mobNo;
    private String createdDate;
    private String unitPrice;
    private Integer quantity;
    private String amountPaid;
    private String total;
    private String rupee = "₹";
    
    private String descrption = "This is a payment receipt for your transaction on Booking Amount for BoomBike ";
    
}
