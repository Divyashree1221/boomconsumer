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
public class OrderFinanceStatusDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("finance_status_id")
    private String financeStatusId;

    @JsonProperty("finance_status")
    private String financeStatus;

    @JsonProperty("order_finance_status_id")
    private String orderFinanceStatusId;

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

    @JsonProperty("order_financeStatusId")
    private Integer order_financeStatusId;
}
