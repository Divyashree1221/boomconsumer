/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.List;
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
public class EVStatusDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("order_ev_status")
    private  List<OrderEVStatusDTO> orderEvStatus;
    
     @JsonProperty("order_summary_id")
    private String orderSummaryId;
}
