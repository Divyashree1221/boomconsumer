/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.product.dto;

import com.boommotors.btp.user.dto.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Ramya
 */
@Getter
@Setter
@NoArgsConstructor
public class ModelPackageDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("model_accessories_id")
    private String modelAccessoriesId;

    @JsonProperty("model_accessories_details")
    private String modelAccessoriesDetails;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("package_accessories_id")
    private String packageAccessoriesId;

    @JsonProperty("package_name")
    private String packageName;

}
