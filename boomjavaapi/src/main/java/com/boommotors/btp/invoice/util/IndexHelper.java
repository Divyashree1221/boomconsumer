package com.boommotors.btp.invoice.util;


import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;

public class IndexHelper {
    
    @Autowired
    DateUtil dateUtil;

    public static Invoice getInvoice(OrderTransactionDTO data) throws ParseException {
//        System.out.println("Transaction data : " + data.getEmail());
//        Invoice invoice = new Invoice();
//        invoice.setPaymentId("pay_1234");
//        invoice.setAmount(1000.00);
//        invoice.setEmail("abc@gmail.com");
//        invoice.setMobNo("9087654321");
//        //invoice.setCreatedDate(dateUtil.getTimestamp());
//        invoice.setUnitPrce(data.getAmount());
//        invoice.setQuantity(data.getQuantity());
//        invoice.setAmount(data.getAmount());
//        invoice.setTotal(data.getAmount());
        
        Invoice invoice = new Invoice();
        invoice.setPaymentId(data.getRazorpayOrderId());
        System.out.println("Order ID : " + data.getRazorpayOrderId());
        
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String rs = format.format(data.getAmount());
        String rsPrint = rs.replace("Rs.", "");
        System.out.println("Amount format : " + rsPrint);
        
        invoice.setAmount(rsPrint);
        invoice.setEmail(data.getEmail());
        invoice.setMobNo(data.getContact());
        
        System.out.println("Date Created : " + data.getCreatedDate());
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = oldFormat.parse(data.getCreatedDate().toString());
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String output = newFormat.format(date);
        System.out.println("New Date : " + output);
        invoice.setCreatedDate(output);
        
        invoice.setUnitPrice(rsPrint);
        invoice.setQuantity(data.getQuantity());
        invoice.setAmount(rsPrint);
        invoice.setTotal(rsPrint);
       
        return invoice;
    }
}


