/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payload;

/**
 *
 * @author divyashree
 */
public class ApiResponseForProducts {
    
    private Boolean success;
    private String message;
    
    private Object data_model;
    private Object data_variant;
    private Object data_add_ons;

    
    public ApiResponseForProducts(Boolean success, String message, Object data_model, Object data_variant, Object data_add_ons ) {
        this.success = success;
        this.message = message;
        this.data_model = data_model;
        this.data_variant = data_variant;
        this.data_add_ons = data_add_ons;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Object getData_model() {
        return data_model;
    }

    public void setData_model(Object data_model) {
        this.data_model = data_model;
    }

    public Object getData_variant() {
        return data_variant;
    }

    public void setData_variant(Object data_variant) {
        this.data_variant = data_variant;
    }

    public Object getData_add_ons() {
        return data_add_ons;
    }

    public void setData_add_ons(Object data_add_ons) {
        this.data_add_ons = data_add_ons;
    }

    
}
