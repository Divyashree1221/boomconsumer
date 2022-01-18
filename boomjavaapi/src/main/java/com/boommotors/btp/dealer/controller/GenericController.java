/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.controller;

import com.boommotors.btp.dealer.service.GenericService;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.payload.ApiResponse;
import io.imagekit.sdk.models.results.Result;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author divyashree
 */
@RestController
@RequestMapping("/api/generic")
public class GenericController {

    @Autowired
    GenericService genericService;

    /**
     * upload file paths to imageKit
     *
     * @param filePath
     * @return result
     */
    @GetMapping("/imagekit/authentication")
    public Object generatingAuthenticationParameters() {
        try {
            Map<String, String> result = null;

            result = genericService.generatingAuthParams();

            if (result == null) {
                return new ResponseEntity(new ApiResponse(false, "Problem occured while generating auth parameters!", null),
                        HttpStatus.OK);
            }

            return new ResponseEntity(new ApiResponse(true, "Auth parameters generated successfully.", result),
                    HttpStatus.OK);
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }

    }

    @GetMapping("/imagekit/auth")
    public Object generatingAuthParams() {
        try {
            Map<String, String> result = null;

            result = genericService.generatingAuthParams();

            if (result == null) {
                return null;
            }

            return result;
        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/files/upload")
    public ResponseEntity<?> UploadFile(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        try {
            
            Result res = genericService.uploadFile(file);
            if (!"".equals(res) && res != null) {
                return new ResponseEntity(new ApiResponse(true, "File uploaded successfully!!", res),
                        HttpStatus.OK);
            }
            return new ResponseEntity(new ApiResponse(false, "Problem occured while uploading File!", null),
                    HttpStatus.OK);

        } catch (ResourceNotFoundException | AppException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.OK);
        }
    }
}
