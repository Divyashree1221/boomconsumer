/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
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
public class OrderAccessoriesDTO {
        private static final long serialVersionUID = 1L;

    @JsonProperty("order_accessories_id")
    private String orderAccessoriesId;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("model_accessories_id")
    private String modelAccessoriesId;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    
}
