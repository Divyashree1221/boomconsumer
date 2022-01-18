/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.rowmapper;

import com.boommotors.btp.dealer.dto.DealerBankerDTO;
import com.boommotors.btp.dealer.dto.DealerKeyPersonnelDTO;
import com.boommotors.btp.dealer.dto.DealerMasterDTO;
import com.boommotors.btp.dealer.dto.DealerReferencesDTO;
import com.boommotors.btp.util.DatabaseUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class DealerMasterRowMapper implements RowMapper<DealerMasterDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();
    DatabaseUtil du = new DatabaseUtil();

    @Override
    public DealerMasterDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DealerMasterDTO data = new DealerMasterDTO();

        data.setDealerId(ed.encrypt(String.valueOf(rs.getLong("dealer_id"))));
        data.setApplicantStructure(rs.getString("applicant_structure"));
        data.setSalutation(rs.getString("salutation"));
        data.setApplicantName(rs.getString("applicant_name"));
        data.setContactPerson(rs.getString("contact_person"));
        data.setContactDesig(rs.getString("contact_desig"));
        data.setAppAddLine1(rs.getString("app_add_line1"));
        data.setAppAddLine2(rs.getString("app_add_line2"));
        data.setAppPin(rs.getString("app_pin"));
        data.setAppCity(rs.getString("app_city"));
        data.setAppState(rs.getString("app_state"));
        data.setLandPhone(rs.getString("land_phone"));
        data.setAppEmail(rs.getString("app_email"));
        data.setAppEntHgstEdu(rs.getString("app_ent_hgst_edu"));
        data.setDobDoi(rs.getDate("dob_doi"));
        data.setAppPhotoPath(rs.getString("app_photo_path"));
        data.setEntityType(rs.getString("entity_type"));
        data.setEntityStructure(rs.getString("entity_structure"));
        data.setParentEntity(rs.getBoolean("parent_entity"));
        data.setParentNumber(rs.getString("parent_number"));
        data.setEntityName(rs.getString("entity_name"));
        data.setBrandName(rs.getString("brand_name"));
        data.setAddress1(rs.getString("address1"));
        data.setAddress2(rs.getString("address2"));
        data.setCity(rs.getString("city"));
        data.setState(rs.getString("state"));
        data.setPin(rs.getString("pin"));
        data.setPremisesArea(rs.getDouble("premises_area"));
        data.setPremisesType(rs.getString("premises_type"));
        data.setDealershipClass(rs.getString("dealership_class"));
        data.setConstructionType(rs.getString("construction_type"));
        data.setTimeLine(rs.getString("time_line"));
        data.setPremisesPhoto(rs.getString("premises_photo"));
        data.setSameAsPremises(rs.getBoolean("same_as_premises"));
        data.setWorkshopAddress1(rs.getString("workshop_address1"));
        data.setWorkshopAddress2(rs.getString("workshop_address2"));
        data.setWorkshopPin(rs.getString("workshop_pin"));
        data.setWorkshopArea(rs.getDouble("workshop_area"));
        data.setWorkshopType(rs.getString("workshop_type"));
        data.setExistingRelationshipWithBoom(rs.getBoolean("existing_relationship_with_boom"));
        data.setConflictOfInterest(rs.getBoolean("conflict_of_interest"));
        data.setLegalDisputes(rs.getBoolean("legal_disputes"));
        data.setDistributorId(rs.getInt("distributor_id"));
        data.setKyc(rs.getString("kyc"));
        data.setProfileDirector(rs.getString("profile_director"));
        data.setBusinessActivity(rs.getString("business_activity"));
        data.setAudit(rs.getString("audit"));
        data.setPrimissAggrement(rs.getString("primiss_aggrement"));
        data.setStatus(rs.getString("status"));
        data.setOnboardDate(rs.getTimestamp("onboard_date"));
        data.setEndDate(rs.getTimestamp("end_date"));
        data.setDraftFinalSave(rs.getBoolean("draft_final_save"));
        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
        data.setCreatedDate(rs.getTimestamp("created_date"));
        data.setUpdatedDate(rs.getTimestamp("updated_date"));
        data.setUserId(ed.encrypt(String.valueOf(rs.getString("user_id"))));
        data.setDealershipNumber(rs.getString("dealership_number"));
        data.setAutoDealershipExp(rs.getBoolean("auto_dealership_exp"));
        data.setWhyBoomAuto(rs.getString("why_boom_auto"));
        data.setProposedInvestment(rs.getString("proposed_investment"));
        data.setContactMobile(rs.getString("contact_mobile"));
        data.setContactEmail(rs.getString("contact_email"));
        data.setSignature(rs.getString("signature"));
        data.setSigFullName(rs.getString("sig_full_name"));
        data.setSigDesig(rs.getString("sig_desig"));
        data.setSigPlace(rs.getString("sig_place"));
        data.setSigDate(rs.getDate("sig_date"));
        data.setWorkshopCity(rs.getString("workshop_city"));
        data.setWorkshopState(rs.getString("workshop_state"));

        boolean doesApplMobileExists = du.isThere(rs, "app_mobile");
        if (doesApplMobileExists) {
            if (!rs.wasNull()) {
                data.setAppMobile(rs.getString("app_mobile"));
            } else {
                data.setAppMobile("");
            }
        } else {
            data.setAppMobile("");
        }
        
         boolean doesSlNoExists = du.isThere(rs, "sl_no");
        if (doesSlNoExists) {
            if (!rs.wasNull()) {
                data.setSlNo(rs.getInt("sl_no"));
            } else {
                data.setSlNo(0);
            }
        } else {
            data.setSlNo(0);
        }

        List<DealerBankerDTO> bankerData = new ArrayList<>();
        if (!rs.wasNull()) {
            data.setDealerBankerInfo(bankerData);

        } else {
            data.setDealerBankerInfo(bankerData);

        }

        List<DealerKeyPersonnelDTO> personnelData = new ArrayList<>();
        if (!rs.wasNull()) {
            data.setDealerPersonnelInfo(personnelData);

        } else {
            data.setDealerPersonnelInfo(personnelData);

        }

        List<DealerReferencesDTO> refData = new ArrayList<>();
        if (!rs.wasNull()) {
            data.setDealeReferenceInfo(refData);

        } else {
            data.setDealeReferenceInfo(refData);

        }

        return data;
    }

}
