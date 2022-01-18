/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.service;

import com.boommotors.btp.util.DateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import static org.graalvm.compiler.debug.DebugOptions.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author rjanumpally
 */
@Service
public class SMSServiceImpl implements SMSService {

    @Autowired
    DateUtil dateUtil;

    @Value("${sms.api.url}")
    private String smsApiUrl;

    @Value("${sms.auth.key}")
    private String smsAuthKey;

    @Value("${sms.sender.id}")
    private String smsSenderId;

    @Value("${sms.route}")
    private int smsRoute;

    @Value("${sms.templet.id}")
    private String smsTempletId;

    @Value("${sms.flow.id}")
    private String smsflowId;

    @Value("${textlocal.api.url}")
    private String textLocalApiUrl;

    @Value("${textlocal.api.key}")
    private String textLocalApiKey;

    @Value("${textlocal.sender.id}")
    private String textlocalSender;
    
    @Value("${voicecall.api.url}")
    private String voiceCallApiUrl;

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
    public String send(String mobileNo, String jsonInputString) {

        JSONObject responseJSON = null;
        //Prepare Url       
        URL myURL = null;
        BufferedReader reader = null;

        try {
            //prepare connection
            myURL = new URL(smsApiUrl);
            HttpURLConnection postConnection = (HttpURLConnection) myURL.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("authkey", smsAuthKey);
            postConnection.setRequestProperty("Content-Type", "application/json");

            postConnection.setDoOutput(true);
            try (OutputStream os = postConnection.getOutputStream()) {
                os.write(jsonInputString.getBytes());
                os.flush();
            }
            reader = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
            //reading response
            String response;

            while ((response = reader.readLine()) != null) //print response
            {
                responseJSON = new JSONObject(response);
                // System.out.println(response);
                System.out.println(responseJSON.getString("type"));
            }

            //finally close connection
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJSON.getString("type");
    }

    @Override
    public String sendTextLocalSMS(String mobileNo, String otp) {
        try {
            JSONObject responseJSON = null;
            Timestamp dateNow = dateUtil.getTimestamptoSendSMS();
            String istDate = dateNow.toString().trim().substring(0, 19);
            // Construct data
            String apiKey = "apikey=" + URLEncoder.encode(textLocalApiKey, "UTF-8");
            String message = "&message=" + URLEncoder.encode(otp + " is the SECRET OTP for verifying your phone number on Boom Motors. This OTP is generated on " + istDate + " and is valid for 90 seconds. Contact customer support if you did not request for it",
                    "UTF-8");
            String sender = "&sender=" + URLEncoder.encode(textlocalSender, "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode("91" + mobileNo, "UTF-8");

            // Send data
            String data = textLocalApiUrl + apiKey + numbers + message + sender;
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            try ( // Get the response
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                String sResult = "";
                while ((line = rd.readLine()) != null) {
                    // Process line...
                    sResult = sResult + line + " ";
                    responseJSON = new JSONObject(sResult);
                    System.out.println("responseJSON " + responseJSON);
                }
            }

            System.out.println(responseJSON.getString("status"));

            return responseJSON.getString("status");

        } catch (IOException | JSONException e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }

    @Override
    public String sendVoiceCall(String mobileNo, String otp) {
        try {
            JSONObject responseJSON = null;
            BufferedReader reader = null;
            Timestamp dateNow = dateUtil.getTimestamptoSendSMS();
            String istDate = dateNow.toString().trim().substring(0, 19);
            
            
            // Construct data
            String authKey = "authkey=" + smsAuthKey;
            String from = "&from=" + smsSenderId;
            String to = "&to=91" + mobileNo;
            String voiceCallMsg = "&message=" + otp + " is the SECRET OTP for verifying your phone number on Boom Motors. This OTP is generated on " + istDate + " and is valid for 90 seconds. Contact customer support if you did not request for it";
            
            // Send data
            String data = voiceCallApiUrl + authKey + from + to + voiceCallMsg;
            URL myURL = new URL(data);
            
            HttpURLConnection postConnection = (HttpURLConnection) myURL.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("authkey", smsAuthKey);
            postConnection.setRequestProperty("Content-Type", "application/json");

            postConnection.setDoOutput(true);
            try (OutputStream os = postConnection.getOutputStream()) {
                //os.write(jsonInputString.getBytes());
                os.flush();
            }
            reader = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
            //reading response
            String response;

            while ((response = reader.readLine()) != null) //print response
            {
                responseJSON = new JSONObject(response);
                // System.out.println(response);
                System.out.println("here is type : " + responseJSON.getString("type"));
                System.out.println("here is message : " + responseJSON.getString("message"));
            }

            //finally close connection
            reader.close();
            
            System.out.println(responseJSON.getString("type"));

            return responseJSON.getString("type");
            
        }catch (IOException | JSONException e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
        
    }

}
