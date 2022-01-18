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
public class ContestTemplatesDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("contest_templates_id")
    private String contestTemplatesId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("template_name")
    private String templateName;

    @JsonProperty("user_nickname")
    private String userNickname;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("base_image")
    private String baseImage;

    @JsonProperty("body_color_id")
    private String bodyColorId;

    @JsonProperty("body_image")
    private String bodyImage;

    @JsonProperty("frame_color_id")
    private String frameColorId;

    @JsonProperty("frame_image")
    private String frameImage;

    @JsonProperty("mask_color_id")
    private String maskColorId;

    @JsonProperty("mask_image")
    private String maskImage;

    @JsonProperty("rims_color_id")
    private String rimsColorId;

    @JsonProperty("rims_image")
    private String rimsImage;

    @JsonProperty("shock_color_id")
    private String shockColorId;

    @JsonProperty("shock_image")
    private String shockImage;

    @JsonProperty("likes_count")
    private Integer likesCount;

    @JsonProperty("template_image")
    private String templateImage;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    
    @JsonProperty("order_summary_id")
    private String orderSummaryId;

   @JsonProperty("total_records")
    private Integer totalRecords;
   
    @JsonProperty("userLikecliked")
    private Integer userLikecliked;
   
   

}
