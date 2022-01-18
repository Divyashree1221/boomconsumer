/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.service;

import com.boommotors.btp.user.dto.UsersDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author NandiniC
 */
public interface EmailService {
    
     String generate();    

     String sendEmail(String jsonInputString);
    
}
