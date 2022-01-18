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
public class MytemplateLikesDTO {
    
     private static final long serialVersionUID = 1L;
     
    @JsonProperty("my_template_likes_id")
    private String myTemplateLlikesId;
     
    @JsonProperty("contest_templates_id")
    private String contestTemplatesId;

    @JsonProperty("user_id")
    private String userId;   

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    
}
