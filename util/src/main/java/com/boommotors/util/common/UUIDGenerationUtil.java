/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.util.common;

import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 *
 * @author rjanumpally
 */
@Component
public class UUIDGenerationUtil {

    /**
     * THIS IS TYPE 4 UUID
     */
    public String generateRandomUUID() {
        //initialize uuid
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
