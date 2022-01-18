/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author srilatha
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class EtAuthException extends RuntimeException {

    public EtAuthException(String message) {
        super(message);
    }
}
