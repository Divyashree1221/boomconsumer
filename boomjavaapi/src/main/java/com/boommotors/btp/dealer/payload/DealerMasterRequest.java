/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.sql.Date;
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
public class DealerMasterRequest {

    @JsonProperty("dealer_id")
    private String dealerId;

    @JsonProperty("applicant_structure")
    private String applicantStructure;

    @JsonProperty("salutation")
    private String salutation;

    @JsonProperty("applicant_name")
    private String applicantName;

    @JsonProperty("contact_person")
    private String contactPerson;

    @JsonProperty("contact_desig")
    private String contactDesig;

    @JsonProperty("app_add_line1")
    private String appAddLine1;

    @JsonProperty("app_add_line2")
    private String appAddLine2;

    @JsonProperty("app_pin")
    private String appPin;

    @JsonProperty("app_city")
    private String appCity;

    @JsonProperty("app_state")
    private String appState;

    @JsonProperty("land_phone")
    private String landPhone;

    @JsonProperty("app_email")
    private String appEmail;

    @JsonProperty("app_ent_hgst_edu")
    private String appEntHgstEdu;

    @JsonProperty("dob_doi")
    private Date dobDoi;

    @JsonProperty("app_photo_path")
    private String appPhotoPath;

    @JsonProperty("entity_type")
    private String entityType;

    @JsonProperty("entity_structure")
    private String entityStructure;

    @JsonProperty("parent_entity")
    private Boolean parentEntity;

    @JsonProperty("parent_number")
    private String parentNumber;

    @JsonProperty("entity_name")
    private String entityName;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pin")
    private String pin;

    @JsonProperty("premises_area")
    private Double premisesArea;

    @JsonProperty("premises_type")
    private String premisesType;

    @JsonProperty("dealership_class")
    private String dealershipClass;

    @JsonProperty("construction_type")
    private String constructionType;

    @JsonProperty("time_line")
    private String timeLine;

    @JsonProperty("premises_photo")
    private String premisesPhoto;

    @JsonProperty("same_as_premises")
    private Boolean sameAsPremises;

    @JsonProperty("workshop_address1")
    private String workshopAddress1;

    @JsonProperty("workshop_address2")
    private String workshopAddress2;

    @JsonProperty("workshop_pin")
    private String workshopPin;

    @JsonProperty("workshop_area")
    private Double workshopArea;

    @JsonProperty("workshop_type")
    private String workshopType;

    @JsonProperty("existing_relationship_with_boom")
    private Boolean existingRelationshipWithBoom;

    @JsonProperty("conflict_of_interest")
    private Boolean conflictOfInterest;

    @JsonProperty("legal_disputes")
    private Boolean legalDisputes;

    @JsonProperty("distributor_id")
    private Integer distributorId;

    @JsonProperty("kyc")
    private String kyc;

    @JsonProperty("profile_director")
    private String profileDirector;

    @JsonProperty("business_activity")
    private String businessActivity;

    @JsonProperty("audit")
    private String audit;

    @JsonProperty("primiss_aggrement")
    private String primissAggrement;

    @JsonProperty("status")
    private String status;

    @JsonProperty("onboard_date")
    private Timestamp onboardDate;

    @JsonProperty("end_date")
    private Timestamp endDate;

    @JsonProperty("draft_final_save")
    private Boolean draftFinalSave;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("dealer_banker_info")
    private List<DealerBankerRequest> dealerBankerInfo;

    @JsonProperty("dealer_personnel_info")
    private List<DealerKeyPersonnelRequest> dealerPersonnelInfo;

    @JsonProperty("dealer_reference_info")
    private List<DealerReferenceRequest> dealeReferenceInfo;

    @JsonProperty("dealership_number")
    private String dealershipNumber;

    @JsonProperty("auto_dealership_exp")
    private Boolean autoDealershipExp;

    @JsonProperty("why_boom_auto")
    private String whyBoomAuto;

    @JsonProperty("proposed_investment")
    private String proposedInvestment;

    @JsonProperty("contact_mobile")
    private String contactMobile;

    @JsonProperty("contact_email")
    private String contactEmail;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("sig_full_name")
    private String sigFullName;

    @JsonProperty("sig_desig")
    private String sigDesig;

    @JsonProperty("sig_place")
    private String sigPlace;

    @JsonProperty("sig_date")
    private Date sigDate;

    @JsonProperty("workshop_city")
    private String workshopCity;

    @JsonProperty("workshop_state")
    private String workshopState;

}
