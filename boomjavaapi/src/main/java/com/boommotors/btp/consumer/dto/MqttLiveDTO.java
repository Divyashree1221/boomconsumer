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
public class MqttLiveDTO {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("im")
    private Long im;
    
    @JsonProperty("ps")
    private String ps;
    
    @JsonProperty("g_fx")
    private Integer gfx;
    
    @JsonProperty("dt")
    private Integer dt;
    
    @JsonProperty("tm")
    private Integer tm;

    @JsonProperty("lt")
    private Double lt;

    @JsonProperty("lt_d")
    private String ltd;

    @JsonProperty("ln")
    private Double ln;

    @JsonProperty("ln_d")
    private String lnd;

    @JsonProperty("gkm")
    private Double gkm;

    @JsonProperty("hd")
    private Double hd;

    @JsonProperty("nsat")
    private Integer nsat;

    @JsonProperty("alt")
    private Double alt;

    @JsonProperty("nws")
    private Integer nws;

    @JsonProperty("lac")
    private Integer lac;

    @JsonProperty("cid")
    private String cid;

    @JsonProperty("d1")
    private Double d1;

    @JsonProperty("d2")
    private Double d2;

    @JsonProperty("d3")
    private Double d3;

    @JsonProperty("r1")
    private Integer r1;

    @JsonProperty("r2")
    private Integer r2;

    @JsonProperty("a1")
    private Integer a1;

    @JsonProperty("a2")
    private Integer a2;

    @JsonProperty("mu_v")
    private Double muv;

    @JsonProperty("mu_t")
    private Double mut;

    @JsonProperty("v_km")
    private Integer vkm;

    @JsonProperty("m_a")
    private Integer ma;

    @JsonProperty("c_v")
    private Integer cv;

    @JsonProperty("t_s")
    private Integer ts;

    @JsonProperty("c_t")
    private Integer ct;

    @JsonProperty("m_t")
    private Integer mt;

    @JsonProperty("soc")
    private Integer soc;

    @JsonProperty("b_a")
    private Integer ba;

    @JsonProperty("b_v")
    private Integer bv;

    @JsonProperty("b_t")
    private Integer bt;

    @JsonProperty("x")
    private Double x;

    @JsonProperty("y")
    private Double y;

    @JsonProperty("z")
    private Double z;
    
    @JsonProperty("ri")
    private Integer ri;
    
    @JsonProperty("fsn")
    private Integer fsn;
    
    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    

}
