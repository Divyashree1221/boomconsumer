package com.boommotors.btp.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author rjanumpally
 */
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {

    private Long createdBy;
    private Long updatedBy;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
