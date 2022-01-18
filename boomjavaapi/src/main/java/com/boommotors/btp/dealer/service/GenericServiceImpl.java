/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.service;

import com.boommotors.btp.dealer.dao.GenericDAO;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import com.boommotors.btp.util.RandomStringGenerator;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import io.imagekit.sdk.utils.Utils;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

/**
 *
 * @author divyashree
 */
@Service
public class GenericServiceImpl implements GenericService {

    private static final Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    GenericDAO genericRepository;

    @Autowired
    RandomStringGenerator stringGenerator;

    private final TemplateEngine templateEngine;

    public GenericServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Map<String, String> generatingAuthParams() {
        try {
            ImageKit imageKit = ImageKit.getInstance();
            Configuration config = Utils.getSystemConfig(GenericServiceImpl.class);
//            System.out.println("getPrivateKey " + config.getPrivateKey());
//            System.out.println("getPublicKey " + config.getPublicKey());
//            System.out.println("getUrlEndpoint " + config.getUrlEndpoint());
            imageKit.setConfig(config);

            Map<String, String> authenticationParameters = ImageKit.getInstance().getAuthenticationParameters();
            System.out.println(authenticationParameters);
            return authenticationParameters;

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GenericServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Result uploadFile(MultipartFile file) {
        Result result = null;
        try {

            ImageKit imageKit = ImageKit.getInstance();
            Configuration config = Utils.getSystemConfig(GenericServiceImpl.class);
            imageKit.setConfig(config);
            String fileName = file.getOriginalFilename();

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            System.out.println("extension " + extension);

            FileCreateRequest fileCreateRequest = new FileCreateRequest(file.getBytes(), fileName);
            fileCreateRequest.setUseUniqueFileName(true);
            fileCreateRequest.setFolder("DealerOnBoard");
            result = ImageKit.getInstance().upload(fileCreateRequest);

            System.out.println("Result from imagekit : " + result);

            return result;

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GenericServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
