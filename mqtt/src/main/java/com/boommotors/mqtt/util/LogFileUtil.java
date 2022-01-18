/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.mqtt.util;

import com.boommotors.util.common.DateUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author rjanumpally
 */
@Component
public class LogFileUtil {

    @Value("${mqtt.log.path}")
    private String mqttLogPath;

    @Autowired
    DateUtil dateUtil;

    public void write(String fileName, String level, String message) {
        BufferedWriter out = null;
        try {
            fileName = fileName.replaceAll("\\s+", "_").toLowerCase();
            //String directoryName = "C:\\Workspace\\JR\\PROJECTS\\GITHUB-V3\\logs\\" + fileName;
            String directoryName = mqttLogPath + fileName;

            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.
            }

            fileName = directoryName + File.separator + "mqtt_" + String.valueOf(dateUtil.getTodayDate()).replaceAll("-", "_") + ".log";
            //System.out.println("fileName::" + fileName);
            File myFile = new File(fileName);
            // check if file exist, otherwise create the file before writing
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            // Open given file in append mode. 
            out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(System.lineSeparator() + dateUtil.getTimestamp() + "[" + level.toUpperCase() + "] " + "::" + message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {

            }
        }
    }
}
