package com.boommotors.btp.invoice.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private BigDecimal amount;
    private String cardNumber;
    private String cvv;
    private String month;
    private String year;
}
