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
 * @author Ramya
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderEVStatusDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("ev_status_id")
    private String evStatusId;

    @JsonProperty("ev_status")
    private String evStatus;

    @JsonProperty("order_ev_status_id")
    private String orderEvStatusId;

    @JsonProperty("order_summary_id")
    private String orderSummaryId;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("order_EVStatusId")
    private Integer order_EVStatusId;

}
