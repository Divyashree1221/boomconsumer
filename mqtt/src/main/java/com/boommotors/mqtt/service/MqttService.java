/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.mqtt.service;

import com.boommotors.mqtt.util.LogFileUtil;
import com.boommotors.util.common.AppConstants;
import com.google.gson.Gson;
import com.boommotors.util.common.JsonUtil;
import com.boommotors.util.common.SchemaUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 *
 * @author divyashree
 */
@Service
public class MqttService implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    private MqttClient client = null;

    @Value("${mqtt.username}")
    private String mqttUserName;

    @Value("${mqtt.password}")
    private String mqttPassword;

    @Value("${mqtt.ipAddress}")
    private String mqttIpAddress;

    @Value("${mqtt.port}")
    private String mqttPort;

    @Value("${mqtt.topic}")
    private String mqttTopic;

    private boolean mqttHaveCredential = true;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    LogFileUtil logUtil;

    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    SchemaUtils schemaUtils;

    @Override
    public void connectionLost(Throwable arg0) {
        // TODO Auto-generated method stub
        logger.warn(arg0.getMessage() + " :" + arg0.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // TODO Auto-generated method stub
        System.out.println("Delivery Complete");

        try {
            logger.info("deliveryCompleted ");
            //logger.info("deliveryComplete : " + arg0.getMessage().getPayload().toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            logger.error("Exception is: ", e);
        }
    }

    public void publish(String topicSuffix, String content) {
        MqttMessage message = new MqttMessage();
        message.setPayload(new Gson().toJson(content).getBytes());
        message.setQos(2);
        /**
         * A retained message is a normal MQTT message with the retained flag
         * set to true. The broker stores the last retained message and the
         * corresponding QoS for that topic.
         */
        message.setRetained(false);
        //System.out.println("content::" + content.toString());
        try {
            String topic = mqttTopic + topicSuffix;
            if (client.isConnected()) {
                logger.info("Connection Status :" + client.isConnected());
            } else {
                logger.warn("Connection Status :" + client.isConnected());
            }
            client.publish(topic, message);
        } catch (MqttPersistenceException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            logger.error("Exception is: ", e);
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            logger.error("Exception is: ", e);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("Exception is: ", e);
        }
    }

    public void startMqtt() {

        MemoryPersistence persistence = new MemoryPersistence();
        try {
            client = new MqttClient("tcp://" + mqttIpAddress + ":" + mqttPort, MqttClient.generateClientId(),
                    persistence);

        } catch (MqttException e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
            logger.error(e1.getMessage());
        }

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setMaxInflight(3000);
        connectOptions.setAutomaticReconnect(true);
        if (mqttHaveCredential) {
            connectOptions.setUserName(mqttUserName);
            connectOptions.setPassword(mqttPassword.toCharArray());
        }

        client.setCallback(this);

        // client.connect();
        // MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        try {
            IMqttToken mqttConnectionToken = client.connectWithResult(connectOptions);

            logger.info("Connection Status :" + mqttConnectionToken.isComplete());
            //client.subscribe("#");
            client.subscribe(mqttTopic + "#");
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            logger.error("Exception is: ", e);
        }
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println("message : " + mm.toString());
        System.out.println("id : " + mm.getId());
        System.out.println("payload : " + mm.getPayload());
        System.out.println("Qos : " + mm.getQos());
        System.out.println("String : " + string);
        System.out.println("Message Arrived");

        try {

            if (!"Hello From publish".equalsIgnoreCase(mm.toString())) {
                JSONObject record = new JSONObject(mm.toString().trim());
                String action = extractIncomingMessageCategory(record);

                System.out.println("here");
                System.out.println("action : " + action);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractIncomingMessageCategory(JSONObject record) {
        try {
            if (record.has("action") && !record.isNull("action")) {
                return record.getString("action");
            } else {
                return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

}
