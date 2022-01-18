/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.mqtt.controller;

import com.boommotors.mqtt.service.MqttService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ramya
 */
@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    @Autowired
    private MqttService mqttService;


    @PostMapping("/publish/{imei}/{topic_name}")
    public String publishByTopic(@PathVariable(value = "imei") String imei, 
            @PathVariable(value = "topic_name") String topicName,
            @RequestBody String message) {
        mqttService.publish("boom/telematic/bike/1.0.0/" + imei + "/" + topicName, message);
        return "Success";
    }

    @PostMapping("/publish")
    public String publish(@RequestBody Map<String, Object> message) {

        return "Success";
    }
    
    
    @GetMapping("/test")
    public String test() {

        return "Success";
    }
}
