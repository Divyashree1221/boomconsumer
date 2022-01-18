/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.controller;

import com.boommotors.btp.util.EncryptDecrypt;
import com.boommotors.btp.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rjanumpally
 */
@RestController
@RequestMapping("/api/common/util")
public class CommonUtilController {

    @Autowired
    EncryptDecrypt edUtil;

    @GetMapping("/decrypt/{data}")
    public ResponseEntity<?> decryptValue(@PathVariable(value = "data") String data) {

        if ("".equals(data)) {
            return new ResponseEntity(new ApiResponse(false, "Please provide value to decrypt.", null),
                    HttpStatus.BAD_REQUEST);
        }
        String decryptedValue = edUtil.decrypt(data);
        return new ResponseEntity(new ApiResponse(true, "Decrypted value!", decryptedValue),
                HttpStatus.OK);
    }
    
}
