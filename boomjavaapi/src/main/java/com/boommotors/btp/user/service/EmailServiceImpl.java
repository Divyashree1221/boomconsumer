/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.service;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.user.dto.UsersDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;//
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author NandiniC
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${email.url}")
    private String emailURL;

    @Value("${email.FromEmailId}")
    private String emailFromMailID;

    @Value("${email.auth.key}")
    private String emailAuthKey;

    @Value("${email.FromName}")
    private String emailFromName;

    @Override
    public String generate() {
        //For OTP generation
        //Random random = new Random();
        //return String.format("%04d", random.nextInt(10000));
        int length = 4;
        String result = "";
        int random;
        while (true) {
            random = (int) ((Math.random() * (10)));
            if (result.length() == 0 && random == 0) {//when parsed this insures that the number doesn't start with 0
                random += 1;
                result += random;
            } else if (!result.contains(Integer.toString(random))) {//if my result doesn't contain the new generated digit then I add it to the result
                result += Integer.toString(random);
            }
            if (result.length() >= length) {//when i reach the number of digits desired i break out of the loop and return the final result
                break;
            }
        }

        return result;
    }

    @Override
    public String sendEmail(String jsonInputString) {

        JSONObject responseJSON = null;
        URLConnection myURLConnection = null;
        URL myURL = null;
        BufferedReader reader = null;

        try {

            myURL = new URL(emailURL);
            HttpURLConnection postConnection = (HttpURLConnection) myURL.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setRequestProperty("User-Agent", "PostmanRuntime/7.28.0");

            final String POST_PARAMS = jsonInputString;

            System.out.println("POST_PARAMS" + POST_PARAMS);

            postConnection.setDoOutput(true);
            try (OutputStream os = postConnection.getOutputStream()) {
                os.write(POST_PARAMS.getBytes());
                os.flush();
            }

            reader = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
            //reading response
            String response;
            while ((response = reader.readLine()) != null) //print response
            {
                responseJSON = new JSONObject(response);
                System.out.println(response);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
             return null;
        } catch (IOException ex) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
             return null;
        } catch (JSONException ex) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return responseJSON.getString("status");
    }

}
