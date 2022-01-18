/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author NandiniC
 */
@Getter
@Setter
@NoArgsConstructor
public class BulkEnquirySuccessDTO {

    private static final long serialVersionUID = 1L;
    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("model_name")
    private String modelName;

}
