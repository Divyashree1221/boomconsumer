/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.dto;

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
public class MqttLoginDTO {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("im")
    private Long im;
    
    @JsonProperty("mac")
    private String mac;
    
    @JsonProperty("l_lt")
    private Double iLt;
    
    @JsonProperty("l_ltd")
    private String iLtd;
    
    @JsonProperty("l_ln")
    private Double iLn;

    @JsonProperty("l_lnd")
    private String iLnd;

    @JsonProperty("g_fx")
    private Integer gfx;

    @JsonProperty("bs")
    private Integer bs;

    @JsonProperty("clid")
    private String clid;

    @JsonProperty("odo")
    private Integer odo;

    @JsonProperty("dat")
    private Integer dat;

    @JsonProperty("tm")
    private Integer tm;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

}
