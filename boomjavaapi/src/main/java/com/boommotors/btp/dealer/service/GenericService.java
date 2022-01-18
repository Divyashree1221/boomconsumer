/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.service;

import io.imagekit.sdk.models.results.Result;
import java.io.File;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author divyashree
 */
public interface GenericService {
    
    Map<String, String> generatingAuthParams();
    
    Result uploadFile(MultipartFile file);
    
}
