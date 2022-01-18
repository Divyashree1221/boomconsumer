/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.dao;

import com.boommotors.btp.dealer.dto.DealerBankerDTO;
import com.boommotors.btp.dealer.dto.DealerKeyPersonnelDTO;
import com.boommotors.btp.dealer.dto.DealerMasterDTO;
import com.boommotors.btp.dealer.dto.DealerReferencesDTO;
import com.boommotors.btp.dealer.dto.DealershipEnquiryDTO;
import com.boommotors.btp.dealer.dto.HeardFromWhereDTO;
import com.boommotors.btp.dealer.dto.NewDealershipEnquiryDTO;
import com.boommotors.btp.dealer.dto.StateDTO;
import com.boommotors.btp.dealer.rowmapper.DealerBankerRowMapper;
import com.boommotors.btp.dealer.rowmapper.DealerKeyPersonnelRowMapper;
import com.boommotors.btp.dealer.rowmapper.DealerMasterRowMapper;
import com.boommotors.btp.dealer.rowmapper.DealerReferencesRowMapper;
import com.boommotors.btp.dealer.rowmapper.HeardFromWhereRowMapper;
import com.boommotors.btp.dealer.rowmapper.StateRowMapper;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.order.dao.*;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ramya
 */
@Repository
public class DealerDAOImpl extends JdbcDaoSupport implements DealerDAO {

    private static final Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    EncryptDecrypt edUtil;

    @Override
    public Long createDealerMaster(DealerMasterDTO data) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".dealer_master(applicant_structure, salutation, applicant_name, contact_person, contact_desig, app_add_line1, app_add_line2, ");
            sbQry.append("app_pin, app_city, app_state, land_phone, app_email, app_ent_hgst_edu, dob_doi, app_photo_path, entity_type, entity_structure, parent_entity, ");
            sbQry.append("parent_number, entity_name, brand_name, address1, address2, city, state, pin, premises_area, premises_type, dealership_class, construction_type, ");
            sbQry.append("time_line, premises_photo, same_as_premises, workshop_address1, workshop_address2, workshop_pin, workshop_area, workshop_type, existing_relationship_with_boom, ");
            sbQry.append("conflict_of_interest, legal_disputes, distributor_id, kyc, profile_director, business_activity, audit, primiss_aggrement, status, onboard_date, end_date, ");
            sbQry.append("draft_final_save, created_by, created_date, user_id, dealership_number, auto_dealership_exp, why_boom_auto, proposed_investment, ");
            sbQry.append("contact_mobile, contact_email, signature, sig_full_name, sig_desig, sig_place, sig_date, workshop_city, workshop_state )");
            sbQry.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
            sbQry.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, data.getApplicantStructure());
                preparedStatement.setString(2, data.getSalutation());
                preparedStatement.setString(3, data.getApplicantName());
                preparedStatement.setString(4, data.getContactPerson());
                preparedStatement.setString(5, data.getContactDesig());
                preparedStatement.setString(6, data.getAppAddLine1());
                preparedStatement.setString(7, data.getAppAddLine2());
                preparedStatement.setString(8, data.getAppPin());
                preparedStatement.setString(9, data.getAppCity());
                preparedStatement.setString(10, data.getAppState());
                preparedStatement.setString(11, data.getLandPhone());
                preparedStatement.setString(12, data.getAppEmail());
                preparedStatement.setString(13, data.getAppEntHgstEdu());
                preparedStatement.setDate(14, data.getDobDoi());
                preparedStatement.setString(15, data.getAppPhotoPath());
                preparedStatement.setString(16, data.getEntityType());
                preparedStatement.setString(17, data.getEntityStructure());
                preparedStatement.setBoolean(18, data.getParentEntity());
                preparedStatement.setString(19, data.getParentNumber());
                preparedStatement.setString(20, data.getEntityName());
                preparedStatement.setString(21, data.getBrandName());
                preparedStatement.setString(22, data.getAddress1());
                preparedStatement.setString(23, data.getAddress2());
                preparedStatement.setString(24, data.getCity());
                preparedStatement.setString(25, data.getState());
                preparedStatement.setString(26, data.getPin());
                preparedStatement.setDouble(27, data.getPremisesArea());
                preparedStatement.setString(28, data.getPremisesType());
                preparedStatement.setString(29, data.getDealershipClass());
                preparedStatement.setString(30, data.getConstructionType());
                preparedStatement.setString(31, data.getTimeLine());
                preparedStatement.setString(32, data.getPremisesPhoto());
                preparedStatement.setBoolean(33, data.getSameAsPremises());
                preparedStatement.setString(34, data.getWorkshopAddress1());
                preparedStatement.setString(35, data.getWorkshopAddress2());
                preparedStatement.setString(36, data.getWorkshopPin());
                preparedStatement.setDouble(37, data.getWorkshopArea());
                preparedStatement.setString(38, data.getWorkshopType());
                preparedStatement.setBoolean(39, data.getExistingRelationshipWithBoom());
                preparedStatement.setBoolean(40, data.getConflictOfInterest());
                preparedStatement.setBoolean(41, data.getLegalDisputes());
                preparedStatement.setInt(42, data.getDistributorId());
                preparedStatement.setString(43, data.getKyc());
                preparedStatement.setString(44, data.getProfileDirector());
                preparedStatement.setString(45, data.getBusinessActivity());
                preparedStatement.setString(46, data.getAudit());
                preparedStatement.setString(47, data.getPrimissAggrement());
                preparedStatement.setString(48, data.getStatus());
                preparedStatement.setTimestamp(49, data.getOnboardDate());
                preparedStatement.setTimestamp(50, data.getEndDate());
                preparedStatement.setBoolean(51, data.getDraftFinalSave());
                preparedStatement.setInt(52, Integer.parseInt(edUtil.decrypt(String.valueOf(data.getCreatedBy()))));
                preparedStatement.setTimestamp(53, dateUtil.getTimestamp());
                preparedStatement.setInt(54, Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUserId()))));
                preparedStatement.setString(55, data.getDealershipNumber());
                preparedStatement.setBoolean(56, data.getAutoDealershipExp());
                preparedStatement.setString(57, data.getWhyBoomAuto());
                preparedStatement.setString(58, data.getProposedInvestment());
                preparedStatement.setString(59, data.getContactMobile());
                preparedStatement.setString(60, data.getContactEmail());
                preparedStatement.setString(61, data.getSignature());
                preparedStatement.setString(62, data.getSigFullName());
                preparedStatement.setString(63, data.getSigDesig());
                preparedStatement.setString(64, data.getSigPlace());
                preparedStatement.setDate(65, data.getSigDate());
                preparedStatement.setString(66, data.getWorkshopCity());
                preparedStatement.setString(67, data.getWorkshopState());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("dealer_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Failed to create dealer master");
        }
    }

    @Override
    public Long createDealerBanker(DealerBankerDTO dealerBanker) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".dealer_banker(dealer_id, nameofbank, branch, duration_of_relationship, typeoffacility, current_limit, created_by, created_date )");
            sbQry.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(String.valueOf(dealerBanker.getDealerId()))));
                preparedStatement.setString(2, dealerBanker.getNameofbank());
                preparedStatement.setString(3, dealerBanker.getBranch());
                preparedStatement.setString(4, dealerBanker.getDurationOfRelationship());
                preparedStatement.setString(5, dealerBanker.getTypeoffacility());
                preparedStatement.setString(6, dealerBanker.getCurrentLimit());
                preparedStatement.setInt(7, Integer.parseInt(edUtil.decrypt(String.valueOf(dealerBanker.getCreatedBy()))));
                preparedStatement.setTimestamp(8, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("dealer_banker_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Failed to insert dealer banker details.");
        }

    }

    @Override
    public Long createDealerKeyPersonnel(DealerKeyPersonnelDTO dealerPersonnel) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".dealer_key_personnel(dealer_id, designation, qualification, personnel_name, currently_working, created_by, created_date )");
            sbQry.append("VALUES (?, ?, ?, ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(String.valueOf(dealerPersonnel.getDealerId()))));
                preparedStatement.setString(2, dealerPersonnel.getDesignation());
                preparedStatement.setString(3, dealerPersonnel.getQualification());
                preparedStatement.setString(4, dealerPersonnel.getPersonnelName());
                preparedStatement.setString(5, dealerPersonnel.getCurrentlyWorking());
                preparedStatement.setInt(6, Integer.parseInt(edUtil.decrypt(String.valueOf(dealerPersonnel.getCreatedBy()))));
                preparedStatement.setTimestamp(7, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("personnel_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Failed to insert dealer personnel details.");
        }
    }

    @Override
    public Long createDealerReferences(DealerReferencesDTO dealerReferences) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".dealer_references(dealer_id, nameaddress, occupation, years_known, contact_person, phone_number, created_by, created_date )");
            sbQry.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(String.valueOf(dealerReferences.getDealerId()))));
                preparedStatement.setString(2, dealerReferences.getNameaddress());
                preparedStatement.setString(3, dealerReferences.getOccupation());
                preparedStatement.setString(4, dealerReferences.getYearsKown());
                preparedStatement.setString(5, dealerReferences.getContactPerson());
                preparedStatement.setString(6, dealerReferences.getPhoneNumber());
                preparedStatement.setInt(7, Integer.parseInt(edUtil.decrypt(String.valueOf(dealerReferences.getCreatedBy()))));
                preparedStatement.setTimestamp(8, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("dealer_references_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Failed to create dealer references.");
        }

    }

    @Override
    public DealerMasterDTO fetchDealerDetailsByUserId(Integer userId) {
        try {
            DealerMasterDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".dealer_master ");
            sbQry.append("WHERE user_id = ?");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{userId}, new DealerMasterRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DealerBankerDTO> fetchDealerBankerDetails(Integer dealerId) {
        try {
            List<DealerBankerDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".dealer_banker ");
            sbQry.append("WHERE dealer_id = ?");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{dealerId}, new DealerBankerRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DealerKeyPersonnelDTO> fetchDealerPersonnelDetails(Integer dealerId) {
        try {
            List<DealerKeyPersonnelDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".dealer_key_personnel ");
            sbQry.append("WHERE dealer_id = ?");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{dealerId}, new DealerKeyPersonnelRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DealerReferencesDTO> fetchDealerReferences(Integer dealerId) {
        try {
            List<DealerReferencesDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".dealer_references ");
            sbQry.append("WHERE dealer_id = ?");
            //System.out.println(sbQry.toString()+" "+dealerId);
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{dealerId}, new DealerReferencesRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean update(DealerMasterDTO data) {
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".dealer_master SET  ");
            sbQry.append("applicant_structure = ?, salutation = ?, applicant_name = ?, contact_person = ?, contact_desig = ?, app_add_line1 = ?, app_add_line2 = ?, ");
            sbQry.append("app_pin = ?, app_city = ?, app_state = ?, land_phone = ?, app_email = ?, app_ent_hgst_edu = ?, dob_doi = ?, app_photo_path = ?, entity_type = ?, entity_structure = ?, parent_entity = ?, ");
            sbQry.append("parent_number = ?, entity_name = ?, brand_name = ?, address1 = ?, address2 = ?, city = ?, state = ?, pin = ?, premises_area = ?, premises_type = ?, dealership_class = ?, construction_type = ?, ");
            sbQry.append("time_line = ?, premises_photo = ?, same_as_premises = ?, workshop_address1 = ?, workshop_address2 = ?, workshop_pin = ?, workshop_area = ?, workshop_type = ?, existing_relationship_with_boom = ?, ");
            sbQry.append("conflict_of_interest = ?, legal_disputes = ?, distributor_id = ?, kyc = ?, profile_director = ?, business_activity = ?, audit = ?, primiss_aggrement = ?, status = ?, onboard_date = ?, end_date = ?, ");
            sbQry.append("draft_final_save = ?, updated_by = ?, updated_date = ?, user_id = ?, dealership_number = ?, auto_dealership_exp = ?, why_boom_auto = ?, proposed_investment = ?, ");
            sbQry.append("contact_mobile = ?, contact_email = ?, signature = ?, sig_full_name = ?, sig_desig = ?, sig_place = ?, sig_date = ?, workshop_city = ?, workshop_state = ?");
            sbQry.append("WHERE dealer_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getApplicantStructure(), data.getSalutation(), data.getApplicantName(), data.getContactPerson(), data.getContactDesig(), data.getAppAddLine1(), data.getAppAddLine2(),
                        data.getAppPin(), data.getAppCity(), data.getAppState(), data.getLandPhone(), data.getAppEmail(), data.getAppEntHgstEdu(), data.getDobDoi(), data.getAppPhotoPath(),
                        data.getEntityType(), data.getEntityStructure(), data.getParentEntity(), data.getParentNumber(), data.getEntityName(), data.getBrandName(), data.getAddress1(), data.getAddress2(),
                        data.getCity(), data.getState(), data.getPin(), data.getPremisesArea(), data.getPremisesType(), data.getDealershipClass(), data.getConstructionType(), data.getTimeLine(), data.getPremisesPhoto(),
                        data.getSameAsPremises(), data.getWorkshopAddress1(), data.getWorkshopAddress2(), data.getWorkshopPin(), data.getWorkshopArea(), data.getWorkshopType(), data.getExistingRelationshipWithBoom(),
                        data.getConflictOfInterest(), data.getLegalDisputes(), data.getDistributorId(), data.getKyc(), data.getProfileDirector(), data.getBusinessActivity(), data.getAudit(), data.getPrimissAggrement(),
                        data.getStatus(), data.getOnboardDate(), data.getEndDate(), data.getDraftFinalSave(), (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy())))),
                        dateUtil.getTimestamp(), (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUserId())))),
                        data.getDealershipNumber(), data.getAutoDealershipExp(), data.getWhyBoomAuto(), data.getProposedInvestment(), data.getContactMobile(), data.getContactEmail(),
                        data.getSignature(), data.getSigFullName(), data.getSigDesig(), data.getSigPlace(), data.getSigDate(), data.getWorkshopCity(), data.getWorkshopState(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerId()))),});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            return false;
        }
    }

    @Override
    public Boolean updateDealerBanker(DealerBankerDTO data) {
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".dealer_banker SET  ");
            sbQry.append("dealer_id = ?, ");
            sbQry.append("nameofbank = ?, ");
            sbQry.append("branch = ?,  ");
            sbQry.append("duration_of_relationship = ?, ");
            sbQry.append("typeoffacility = ?, ");
            sbQry.append("current_limit = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE dealer_banker_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerId())))),
                        data.getNameofbank(),
                        data.getBranch(),
                        data.getDurationOfRelationship(),
                        data.getTypeoffacility(),
                        data.getCurrentLimit(),
                        (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy())))),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerBankerId()))),});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            return false;
        }

    }

    @Override
    public Boolean updateDealerKeyPersonnel(DealerKeyPersonnelDTO data) {
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".dealer_key_personnel SET  ");
            sbQry.append("dealer_id = ?, ");
            sbQry.append("designation = ?, ");
            sbQry.append("qualification = ?,  ");
            sbQry.append("personnel_name = ?, ");
            sbQry.append("currently_working = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE personnel_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerId())))),
                        data.getDesignation(),
                        data.getQualification(),
                        data.getPersonnelName(),
                        data.getCurrentlyWorking(),
                        (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy())))),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getPersonnelId()))),});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            return false;
        }
    }

    @Override
    public Boolean updateDealerReferences(DealerReferencesDTO data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".dealer_references SET  ");
            sbQry.append("dealer_id = ?, ");
            sbQry.append("nameaddress = ?, ");
            sbQry.append("occupation = ?,  ");
            sbQry.append("years_known = ?, ");
            sbQry.append("contact_person = ?, ");
            sbQry.append("phone_number = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE dealer_references_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerId())))),
                        data.getNameaddress(),
                        data.getOccupation(),
                        data.getYearsKown(),
                        data.getContactPerson(),
                        data.getPhoneNumber(),
                        (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy())))),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerReferencesId()))),});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            return false;
        }

    }

    @Override
    public DealerMasterDTO fetchByDealershipNo(String dealershipNumber) {
        try {
            DealerMasterDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".dealer_master ");
            sbQry.append("WHERE dealership_number = ?");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{dealershipNumber}, new DealerMasterRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DealerMasterDTO> fetchDealersData(Integer distributorId) {
        try {
            List<DealerMasterDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".dealer_master ");
            sbQry.append("WHERE distributor_id = ? ORDER BY dealer_id DESC ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{distributorId}, new DealerMasterRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer fetchDistributorId(Integer userId) {
        try {
            DealerMasterDTO result;

            String GET_RECORD_COUNT_SQL = "SELECT distributor_id FROM \"booma\".distributor_master WHERE user_id = ?";

            Integer id = getJdbcTemplate().queryForObject(GET_RECORD_COUNT_SQL, new Object[]{userId}, Integer.class);

            return id;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer fetchDistributorIdByState(String stateName) {
        try {
            DealerMasterDTO result;

            String GET_RECORD_COUNT_SQL = "SELECT distributor_id FROM \"booma\".distributor_master WHERE LOWER(dis_state) = LOWER(?)";

            Integer id = getJdbcTemplate().queryForObject(GET_RECORD_COUNT_SQL, new Object[]{stateName}, Integer.class);

            return id;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Boolean updateDealerAdditionalDocs(DealerMasterDTO data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".dealer_master SET  ");
            sbQry.append("kyc = ?, ");
            sbQry.append("audit = ?, ");
            sbQry.append("primiss_aggrement = ?, ");
            sbQry.append("status = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE dealer_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getKyc(),
                        data.getAudit(),
                        data.getPrimissAggrement(),
                        data.getStatus(),
                        (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy())))),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerId()))),});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Boolean updateDealerStatus(DealerMasterDTO data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".dealer_master SET  ");
            sbQry.append("status = ?, ");
            sbQry.append("onboard_date = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE dealer_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getStatus(),
                        data.getOnboardDate(),
                        (Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy())))),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerId()))),});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<StateDTO> fetchAllState() {
        try {
            List<StateDTO> result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".state ");

            result = getJdbcTemplate().query(sbQry.toString(), new StateRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<HeardFromWhereDTO> fetchHeardFromWhere() {
        try {
            List<HeardFromWhereDTO> result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".heard_from_where ");

            result = getJdbcTemplate().query(sbQry.toString(), new HeardFromWhereRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long createDealerEnquiry(DealershipEnquiryDTO data) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".dealership_enquiry_old(name, email, contact_no, is_registered, company_name, company_address, designation, preferred_state1, ");
            sbQry.append("preferred_state2, district_city, is_owned_leased, amount, is_experienced, heard_from_where, comments, created_date )");
            sbQry.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, data.getName());
                preparedStatement.setString(2, data.getEmail());
                preparedStatement.setString(3, data.getContactNo());
                preparedStatement.setBoolean(4, data.getIsRegistered());
                preparedStatement.setString(5, data.getCompanyName());
                preparedStatement.setString(6, data.getCompanyAddress());
                preparedStatement.setString(7, data.getDesignation());
                preparedStatement.setString(8, data.getPreferredState1());
                preparedStatement.setString(9, data.getPreferredState2());
                preparedStatement.setString(10, data.getDistrictCity());
                preparedStatement.setBoolean(11, data.getIsOwnedLeased());
                preparedStatement.setString(12, data.getAmount());
                preparedStatement.setBoolean(13, data.getIsExperienced());
                preparedStatement.setString(14, data.getHeardFromWhere());
                preparedStatement.setString(15, data.getComments());
                preparedStatement.setTimestamp(16, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Failed to insert dealership enquiry details.");
        }
    }

    @Override
    public Long createDealershipEnquiry(NewDealershipEnquiryDTO data) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".dealership_enquiry(is_experienced, brand, since_when, pincode1, pincode2, pincode3, amount, comments, created_by, created_date) ");
            sbQry.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setBoolean(1, data.getIsExperienced());
                preparedStatement.setString(2, data.getBrand());
                preparedStatement.setString(3, data.getSinceWhen());
                preparedStatement.setString(4, data.getPincode1());
                preparedStatement.setString(5, data.getPincode2());
                preparedStatement.setString(6, data.getPincode3());
                preparedStatement.setDouble(7, data.getAmount());
                preparedStatement.setString(8, data.getComments());
                preparedStatement.setInt(9, Integer.parseInt(edUtil.decrypt(String.valueOf(data.getCreatedBy()))));
                preparedStatement.setTimestamp(10, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("dealership_enquiry_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Failed to insert dealership enquiry details.");
        }
    }

}
