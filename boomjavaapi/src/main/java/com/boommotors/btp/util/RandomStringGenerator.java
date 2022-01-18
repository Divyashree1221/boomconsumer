/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.util;

import java.util.Random;
import org.springframework.stereotype.Component;

/**
 *
 * @author divyashree
 */
@Component
public class RandomStringGenerator {
    
    /**
     *
     * @param length
     * @return string
     */
    public static String getAlphaNumericString(int length)
    {
  
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(length);
  
        for (int i = 0; i < length; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }
    
     public int getRandomNumber() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }
}
