/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.dto;

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
public class UsersDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("email_id")
    private String emailId;

    @JsonProperty("user_role_id")
    private Long userRoleId;

    @JsonProperty("social_id")
    private String socialId;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("login_otp")
    private Integer loginOtp;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("updated_date")
    private Timestamp updatedDate;

    @JsonProperty("mobile_verified")
    private Boolean mobile_verified;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("email_otp")
    private Integer emailOtp;

    @JsonProperty("mobile_otp_issue_time")
    private Timestamp mobileOtpIssueTime;

    @JsonProperty("email_otp_issue_time")
    private Timestamp emailOtpIssueTime;

    @JsonProperty("account_locked")
    private Boolean accountLocked;

    @JsonProperty("failed_attempt")
    private Integer failedAttempt;

    @JsonProperty("lock_time")
    private Timestamp lockTime;
}
