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
 * @author divyashree
 */

@Getter
@Setter
@NoArgsConstructor
public class BulkEnquiryPartsColourDTO {
    
    private static final long serialVersionUID = 1L;

    @JsonProperty("bulk_enquiry_parts_colour_id")
    private String bulkEnquiryPartsColourId;

    @JsonProperty("bulk_enquiry_id")
    private String bulkEnquiryId;

    @JsonProperty("variant_parts_colour_id")
    private String variantPartsColourId;

    @JsonProperty("created_by")
    private String createdBy;
    
    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    
}
