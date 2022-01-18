package com.boommotors.mqtt;

import com.boommotors.mqtt.service.MqttService;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.boommotors.mqtt.*",
    "com.boommotors.btp.*",
"com.boommotors.util.*",
"com.boommotors.exception.*"})
@EnableAutoConfiguration
@SpringBootApplication
public class MqttApplication implements CommandLineRunner {

    @Autowired
    MqttService mqttService;

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));//Change it to IST +5:30
    }

    public static void main(String[] args) {
        SpringApplication.run(MqttApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        mqttService.startMqtt();
    }

}
