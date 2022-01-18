/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.service;

/**
 *
 * @author divyashree
 */
public interface SMSService {

    String generate();

    String send(String mobileNo, String jsonInputString);
    
    String sendTextLocalSMS(String mobileNo, String otp);
    
    String sendVoiceCall(String mobileNo, String otp);
}
