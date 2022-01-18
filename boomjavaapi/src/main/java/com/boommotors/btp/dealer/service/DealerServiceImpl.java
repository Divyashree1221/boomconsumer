/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.dealer.service;

import com.boommotors.btp.dealer.dao.DealerDAO;
import com.boommotors.btp.dealer.dto.DealerBankerDTO;
import com.boommotors.btp.dealer.dto.DealerKeyPersonnelDTO;
import com.boommotors.btp.dealer.dto.DealerMasterDTO;
import com.boommotors.btp.dealer.dto.DealerReferencesDTO;
import com.boommotors.btp.dealer.dto.DealershipEnquiryDTO;
import com.boommotors.btp.dealer.dto.HeardFromWhereDTO;
import com.boommotors.btp.dealer.dto.NewDealershipEnquiryDTO;
import com.boommotors.btp.dealer.dto.StateDTO;
import com.boommotors.btp.dealer.payload.DealerMasterRequest;
import com.boommotors.btp.dealer.payload.DealershipEnquiryRequest;
import com.boommotors.btp.dealer.payload.NewDealershipEnquiryRequest;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.user.dao.UserDAO;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.service.EmailService;
import com.boommotors.btp.user.service.UserService;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import com.boommotors.btp.util.RandomStringGenerator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ramya
 */
@Service
@Transactional
public class DealerServiceImpl implements DealerService {

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    DealerDAO dealerRepository;

    @Autowired
    UserDAO userRepository;

    @Autowired
    RandomStringGenerator stringGenerator;
    
    @Autowired
    UserService userService;
    
    @Autowired
    EmailService emailService;
    
    //============================
    //EMAIL values

    @Value("${email.url}")
    private String emailURL;

    @Value("${email.FromEmailId}")
    private String emailFromMailID;

    @Value("${email.Otptemplate}")
    private String emailOtptemplate;

    @Value("${email.auth.key}")
    private String emailAuthKey;

    @Value("${email.FromName}")
    private String emailFromName;

    @Value("${email.ConsumerEmailTemplate}")
    private String consumerEmailTemplate;

    @Value("${email.DealerEmailTemplate}")
    private String dealerEmailTemplate;

    @Override
    public String createDealerMaster(DealerMasterRequest dealerRequest) {
        //Auto generating dealership number
        String dealerShipNo = "";
        if (dealerRequest.getApplicantName() != null && !"".equals(dealerRequest.getApplicantName())) {
            char userLet = dealerRequest.getApplicantName().toUpperCase().charAt(0);
            dealerShipNo = "B" + userLet + stringGenerator.getRandomNumber();
        } else {
            dealerShipNo = "BM" + stringGenerator.getRandomNumber();
        }
        System.out.println("dealerShipNo " + dealerShipNo);

        Integer distributorId = null;
        try {

            //fetching Distributor id based on state name
            if (dealerRequest.getState() != null && dealerRequest.getState() != "") {
                distributorId = dealerRepository.fetchDistributorIdByState(dealerRequest.getState());
            }

            DealerMasterDTO data = new DealerMasterDTO();

            data.setApplicantStructure(dealerRequest.getApplicantStructure());
            data.setSalutation(dealerRequest.getSalutation());
            data.setApplicantName(dealerRequest.getApplicantName());
            data.setContactPerson(dealerRequest.getContactPerson());
            data.setContactDesig(dealerRequest.getContactDesig());
            data.setAppAddLine1(dealerRequest.getAppAddLine1());
            data.setAppAddLine2(dealerRequest.getAppAddLine2());
            data.setAppPin(dealerRequest.getAppPin());
            data.setAppCity(dealerRequest.getAppCity());
            data.setAppState(dealerRequest.getAppState());
            data.setLandPhone(dealerRequest.getLandPhone());
            data.setAppEmail(dealerRequest.getAppEmail());
            data.setAppEntHgstEdu(dealerRequest.getAppEntHgstEdu());
            data.setDobDoi(dealerRequest.getDobDoi());
            data.setAppPhotoPath(dealerRequest.getAppPhotoPath());
            data.setEntityType(dealerRequest.getEntityType());
            data.setEntityStructure(dealerRequest.getEntityStructure());
            data.setParentEntity(dealerRequest.getParentEntity());
            data.setParentNumber(dealerRequest.getParentNumber());
            data.setEntityName(dealerRequest.getEntityName());
            data.setBrandName(dealerRequest.getBrandName());
            data.setAddress1(dealerRequest.getAddress1());
            data.setAddress2(dealerRequest.getAddress2());
            data.setCity(dealerRequest.getCity());
            data.setState(dealerRequest.getState());
            data.setPin(dealerRequest.getPin());
            data.setPremisesArea(dealerRequest.getPremisesArea());
            data.setPremisesType(dealerRequest.getPremisesType());
            data.setDealershipClass(dealerRequest.getDealershipClass());
            data.setConstructionType(dealerRequest.getConstructionType());
            data.setTimeLine(dealerRequest.getTimeLine());
            data.setPremisesPhoto(dealerRequest.getPremisesPhoto());
            data.setSameAsPremises(dealerRequest.getSameAsPremises());
            data.setWorkshopAddress1(dealerRequest.getWorkshopAddress1());
            data.setWorkshopAddress2(dealerRequest.getWorkshopAddress2());
            data.setWorkshopPin(dealerRequest.getWorkshopPin());
            data.setWorkshopArea(dealerRequest.getWorkshopArea());
            data.setWorkshopType(dealerRequest.getWorkshopType());
            data.setExistingRelationshipWithBoom(dealerRequest.getExistingRelationshipWithBoom());
            data.setConflictOfInterest(dealerRequest.getConflictOfInterest());
            data.setLegalDisputes(dealerRequest.getLegalDisputes());
            data.setConstructionType(dealerRequest.getConstructionType());
            data.setDistributorId(distributorId); /// should get it from distributor table based on state
            data.setKyc(dealerRequest.getKyc());
            data.setProfileDirector(dealerRequest.getProfileDirector());
            data.setBusinessActivity(dealerRequest.getBusinessActivity());
            data.setAudit(dealerRequest.getAudit());
            data.setPrimissAggrement(dealerRequest.getPrimissAggrement());
            data.setStatus(dealerRequest.getStatus());
            data.setOnboardDate(null);
            data.setEndDate(dealerRequest.getEndDate());
            data.setDraftFinalSave(dealerRequest.getDraftFinalSave());
            data.setCreatedBy(dealerRequest.getUserId());
            data.setUserId(dealerRequest.getUserId());
            data.setDealershipNumber(dealerShipNo); /// should be an autogenerated unique number
            data.setAutoDealershipExp(dealerRequest.getAutoDealershipExp());
            data.setWhyBoomAuto(dealerRequest.getWhyBoomAuto());
            data.setProposedInvestment(dealerRequest.getProposedInvestment());
            data.setContactMobile(dealerRequest.getContactMobile());
            data.setContactEmail(dealerRequest.getContactEmail());
            data.setSignature("");
            data.setSigFullName(dealerRequest.getSigFullName());
            data.setSigDesig(dealerRequest.getSigDesig());
            data.setSigPlace(dealerRequest.getSigPlace());
            data.setSigDate(dealerRequest.getSigDate());
            data.setWorkshopCity(dealerRequest.getWorkshopCity());
            data.setWorkshopState(dealerRequest.getWorkshopState());

            Long id = dealerRepository.createDealerMaster(data);
            String dealerId = edUtil.encrypt(String.valueOf(id));
            dealerRequest.setDealerId(dealerId);
            //inserting dealer bank info to dealer_banker table
            if (id > 0 && (dealerRequest.getDealerBankerInfo().size() > 0)) {
                Long res = createDealerBanker(dealerRequest);
            }

            //inserting dealer personal info to dealer_key_personnel table
            if (id > 0 && (dealerRequest.getDealerPersonnelInfo().size() > 0)) {
                Long res = createDealerKeyPersonnel(dealerRequest);
            }

            //inserting dealer references to dealer_references table
            if (id > 0 && (dealerRequest.getDealeReferenceInfo().size() > 0)) {
                Long res = createDealerRef(dealerRequest);
            }

            return dealerId;

        } catch (AppException | EtAuthException | NumberFormatException ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    public Long createDealerBanker(DealerMasterRequest dealerRequest) {
        Long bankerId = 0L;
        try {
            DealerBankerDTO data = new DealerBankerDTO();
            data.setDealerId(dealerRequest.getDealerId());
            data.setCreatedBy(dealerRequest.getUserId());
            for (int i = 0; i < dealerRequest.getDealerBankerInfo().size(); i++) {
                data.setNameofbank(dealerRequest.getDealerBankerInfo().get(i).getNameofbank());
                data.setTypeoffacility(dealerRequest.getDealerBankerInfo().get(i).getTypeoffacility());
                data.setBranch(dealerRequest.getDealerBankerInfo().get(i).getBranch());
                data.setDurationOfRelationship(dealerRequest.getDealerBankerInfo().get(i).getDurationOfRelationship());
                data.setCurrentLimit(dealerRequest.getDealerBankerInfo().get(i).getCurrentLimit());

                bankerId = dealerRepository.createDealerBanker(data);
            }
            return bankerId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long createDealerKeyPersonnel(DealerMasterRequest dealerRequest) {
        Long keyPersonnelId = 0L;
        try {
            DealerKeyPersonnelDTO data = new DealerKeyPersonnelDTO();
            data.setDealerId(dealerRequest.getDealerId());
            data.setCreatedBy(dealerRequest.getUserId());

            for (int i = 0; i < dealerRequest.getDealerPersonnelInfo().size(); i++) {
                data.setPersonnelName(dealerRequest.getDealerPersonnelInfo().get(i).getPersonnelName());
                data.setDesignation(dealerRequest.getDealerPersonnelInfo().get(i).getDesignation());
                data.setQualification(dealerRequest.getDealerPersonnelInfo().get(i).getQualification());
                data.setCurrentlyWorking(dealerRequest.getDealerPersonnelInfo().get(i).getCurrentlyWorking());

                keyPersonnelId = dealerRepository.createDealerKeyPersonnel(data);
            }

            return keyPersonnelId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long createDealerRef(DealerMasterRequest dealerRequest) {
        Long refId = 0L;
        try {
            DealerReferencesDTO data = new DealerReferencesDTO();
            data.setDealerId(dealerRequest.getDealerId());
            data.setCreatedBy(dealerRequest.getUserId());

            for (int i = 0; i < dealerRequest.getDealeReferenceInfo().size(); i++) {
                data.setNameaddress(dealerRequest.getDealeReferenceInfo().get(i).getNameaddress());
                data.setOccupation(dealerRequest.getDealeReferenceInfo().get(i).getOccupation());
                data.setPhoneNumber(dealerRequest.getDealeReferenceInfo().get(i).getPhoneNumber());
                data.setContactPerson(dealerRequest.getDealeReferenceInfo().get(i).getContactPerson());
                data.setYearsKown(dealerRequest.getDealeReferenceInfo().get(i).getYearsKown());

                refId = dealerRepository.createDealerReferences(data);
            }

            return refId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DealerMasterDTO retrieveDealerDetails(String encryptedUserId) {
        int userId = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedUserId)));

        DealerMasterDTO dealerData;
        try {
            dealerData = dealerRepository.fetchDealerDetailsByUserId(userId);
            if (dealerData != null) {
                Integer dealerId = Integer.parseInt(edUtil.decrypt(String.valueOf(dealerData.getDealerId())));
                List<DealerBankerDTO> bankerData = dealerRepository.fetchDealerBankerDetails(dealerId);
                List<DealerKeyPersonnelDTO> personnelData = dealerRepository.fetchDealerPersonnelDetails(dealerId);
                List<DealerReferencesDTO> refData = dealerRepository.fetchDealerReferences(dealerId);

                dealerData.setDealerBankerInfo(bankerData);
                dealerData.setDealerPersonnelInfo(personnelData);
                dealerData.setDealeReferenceInfo(refData);

                return dealerData;
            }

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public Boolean update(DealerMasterRequest dealerRequest) {
//        System.out.println("dealer_id " + edUtil.decrypt("_dCQhFpCGONiX_uaaDsB-A\\r\\n"));
        Boolean res = false;
        Integer distributorId = null;
        try {
            //fetching Distributor id based on state name
            if (dealerRequest.getDistributorId() == null || dealerRequest.getDistributorId() == 0) {

                if (dealerRequest.getState() != null && !"".equals(dealerRequest.getState())) {
                    distributorId = dealerRepository.fetchDistributorIdByState(dealerRequest.getState());
                }
            } else {
                distributorId = dealerRequest.getDistributorId();
            }
            DealerMasterDTO data = new DealerMasterDTO();

            data.setDealerId(dealerRequest.getDealerId());
            data.setApplicantStructure(dealerRequest.getApplicantStructure());
            data.setSalutation(dealerRequest.getSalutation());
            data.setApplicantName(dealerRequest.getApplicantName());
            data.setContactPerson(dealerRequest.getContactPerson());
            data.setContactDesig(dealerRequest.getContactDesig());
            data.setAppAddLine1(dealerRequest.getAppAddLine1());
            data.setAppAddLine2(dealerRequest.getAppAddLine2());
            data.setAppPin(dealerRequest.getAppPin());
            data.setAppCity(dealerRequest.getAppCity());
            data.setAppState(dealerRequest.getAppState());
            data.setLandPhone(dealerRequest.getLandPhone());
            data.setAppEmail(dealerRequest.getAppEmail());
            data.setAppEntHgstEdu(dealerRequest.getAppEntHgstEdu());
            data.setDobDoi(dealerRequest.getDobDoi());
            data.setAppPhotoPath(dealerRequest.getAppPhotoPath());
            data.setEntityType(dealerRequest.getEntityType());
            data.setEntityStructure(dealerRequest.getEntityStructure());
            data.setParentEntity(dealerRequest.getParentEntity());
            data.setParentNumber(dealerRequest.getParentNumber());
            data.setEntityName(dealerRequest.getEntityName());
            data.setBrandName(dealerRequest.getBrandName());
            data.setAddress1(dealerRequest.getAddress1());
            data.setAddress2(dealerRequest.getAddress2());
            data.setCity(dealerRequest.getCity());
            data.setState(dealerRequest.getState());
            data.setPin(dealerRequest.getPin());
            data.setPremisesArea(dealerRequest.getPremisesArea());
            data.setPremisesType(dealerRequest.getPremisesType());
            data.setDealershipClass(dealerRequest.getDealershipClass());
            data.setConstructionType(dealerRequest.getConstructionType());
            data.setTimeLine(dealerRequest.getTimeLine());
            data.setPremisesPhoto(dealerRequest.getPremisesPhoto());
            data.setSameAsPremises(dealerRequest.getSameAsPremises());
            data.setWorkshopAddress1(dealerRequest.getWorkshopAddress1());
            data.setWorkshopAddress2(dealerRequest.getWorkshopAddress2());
            data.setWorkshopPin(dealerRequest.getWorkshopPin());
            data.setWorkshopArea(dealerRequest.getWorkshopArea());
            data.setWorkshopType(dealerRequest.getWorkshopType());
            data.setExistingRelationshipWithBoom(dealerRequest.getExistingRelationshipWithBoom());
            data.setConflictOfInterest(dealerRequest.getConflictOfInterest());
            data.setLegalDisputes(dealerRequest.getLegalDisputes());
            data.setConstructionType(dealerRequest.getConstructionType());
            data.setDistributorId(distributorId);
            data.setKyc(dealerRequest.getKyc());
            data.setProfileDirector(dealerRequest.getProfileDirector());
            data.setBusinessActivity(dealerRequest.getBusinessActivity());
            data.setAudit(dealerRequest.getAudit());
            data.setPrimissAggrement(dealerRequest.getPrimissAggrement());
            data.setStatus(dealerRequest.getStatus());
            data.setOnboardDate(dealerRequest.getOnboardDate());
            data.setEndDate(dealerRequest.getEndDate());
            data.setDraftFinalSave(dealerRequest.getDraftFinalSave());
            data.setUpdatedDate(dateUtil.getTimestamp());
            data.setUpdatedBy(dealerRequest.getUserId());
            data.setUserId(dealerRequest.getUserId());
            data.setDealershipNumber(dealerRequest.getDealershipNumber());
            data.setAutoDealershipExp(dealerRequest.getAutoDealershipExp());
            data.setWhyBoomAuto(dealerRequest.getWhyBoomAuto());
            data.setProposedInvestment(dealerRequest.getProposedInvestment());
            data.setContactMobile(dealerRequest.getContactMobile());
            data.setContactEmail(dealerRequest.getContactEmail());
            data.setSignature("");
            data.setSigFullName(dealerRequest.getSigFullName());
            data.setSigDesig(dealerRequest.getSigDesig());
            data.setSigPlace(dealerRequest.getSigPlace());
            data.setSigDate(dealerRequest.getSigDate());
            data.setWorkshopCity(dealerRequest.getWorkshopCity());
            data.setWorkshopState(dealerRequest.getWorkshopState());

            res = dealerRepository.update(data);
            if (res && (dealerRequest.getDealerBankerInfo().size() > 0)) {
                this.updateDealerBanker(dealerRequest);
            }

            if (res && (dealerRequest.getDealerPersonnelInfo().size() > 0)) {
                this.updateDealerKeyPersonnel(dealerRequest);
            }

            if (res && (dealerRequest.getDealeReferenceInfo().size() > 0)) {
                this.updateDealerReferences(dealerRequest);
            }
            return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    public Boolean updateDealerBanker(DealerMasterRequest dealerRequest) {
        Boolean res = false;
        try {
            int dealerId = Integer.parseInt(edUtil.decrypt(String.valueOf(dealerRequest.getDealerId())));
//            List<DealerBankerDTO> bankerData = dealerRepository.fetchDealerBankerDetails(dealerId);
//
//            if (bankerData.size() > 0) {
            for (int i = 0; i < dealerRequest.getDealerBankerInfo().size(); i++) {
                DealerBankerDTO data = new DealerBankerDTO();
                String bId = dealerRequest.getDealerBankerInfo().get(i).getDealerBankerId();
                System.out.println("bId : " + bId);

                if (dealerRequest.getDealerBankerInfo().get(i).getDealerBankerId() != null
                        && !"".equals(dealerRequest.getDealerBankerInfo().get(i).getDealerBankerId())) {
                    data.setDealerBankerId(dealerRequest.getDealerBankerInfo().get(i).getDealerBankerId());
                    data.setDealerId(dealerRequest.getDealerBankerInfo().get(i).getDealerId());
                    data.setNameofbank(dealerRequest.getDealerBankerInfo().get(i).getNameofbank());
                    data.setTypeoffacility(dealerRequest.getDealerBankerInfo().get(i).getTypeoffacility());
                    data.setBranch(dealerRequest.getDealerBankerInfo().get(i).getBranch());
                    data.setDurationOfRelationship(dealerRequest.getDealerBankerInfo().get(i).getDurationOfRelationship());
                    data.setCurrentLimit(dealerRequest.getDealerBankerInfo().get(i).getCurrentLimit());
                    data.setUpdatedBy(dealerRequest.getUserId());
                    data.setUpdatedDate(dateUtil.getTimestamp());

                    Boolean updtRes = dealerRepository.updateDealerBanker(data);
                    res = updtRes;
                    System.out.println("updtRes : " + i + " " + updtRes);
                } else {
                    System.out.println("create references called : ");
                    data.setDealerId(dealerRequest.getDealerId());
                    data.setNameofbank(dealerRequest.getDealerBankerInfo().get(i).getNameofbank());
                    data.setTypeoffacility(dealerRequest.getDealerBankerInfo().get(i).getTypeoffacility());
                    data.setBranch(dealerRequest.getDealerBankerInfo().get(i).getBranch());
                    data.setDurationOfRelationship(dealerRequest.getDealerBankerInfo().get(i).getDurationOfRelationship());
                    data.setCurrentLimit(dealerRequest.getDealerBankerInfo().get(i).getCurrentLimit());
                    data.setCreatedBy(dealerRequest.getUserId());
                    data.setCreatedDate(dateUtil.getTimestamp());

                    Long createRes = dealerRepository.createDealerBanker(data);
                    System.out.println("createRes : " + i + " " + createRes);
                    if (createRes > 0) {
                        res = true;
                    } else {
                        res = false;
                    }

                }
            }
            return res;
//            } else {
//                Long BankerId = this.createDealerBanker(dealerRequest);
//                return BankerId > 0;
//            }

        } catch (AppException | NumberFormatException ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    public Boolean updateDealerKeyPersonnel(DealerMasterRequest dealerRequest) {
        Boolean res = false;
        try {
            int dealerId = Integer.parseInt(edUtil.decrypt(String.valueOf(dealerRequest.getDealerId())));

            for (int i = 0; i < dealerRequest.getDealerPersonnelInfo().size(); i++) {
                DealerKeyPersonnelDTO data = new DealerKeyPersonnelDTO();
                String pId = dealerRequest.getDealerPersonnelInfo().get(i).getPersonnelId();
                System.out.println("pId : " + pId);

                if (dealerRequest.getDealerPersonnelInfo().get(i).getPersonnelId() != null
                        && !"".equals(dealerRequest.getDealerPersonnelInfo().get(i).getPersonnelId())) {
                    data.setPersonnelId(dealerRequest.getDealerPersonnelInfo().get(i).getPersonnelId());
                    data.setDealerId(dealerRequest.getDealerPersonnelInfo().get(i).getDealerId());
                    data.setPersonnelName(dealerRequest.getDealerPersonnelInfo().get(i).getPersonnelName());
                    data.setQualification(dealerRequest.getDealerPersonnelInfo().get(i).getQualification());
                    data.setDesignation(dealerRequest.getDealerPersonnelInfo().get(i).getDesignation());
                    data.setCurrentlyWorking(dealerRequest.getDealerPersonnelInfo().get(i).getCurrentlyWorking());
                    data.setUpdatedBy(dealerRequest.getUserId());
                    data.setUpdatedDate(dateUtil.getTimestamp());

                    Boolean updtRes = dealerRepository.updateDealerKeyPersonnel(data);
                    res = updtRes;
                    System.out.println("updtRes : " + i + " " + updtRes);
                } else {
                    System.out.println("create personnel called : ");
                    data.setDealerId(dealerRequest.getDealerId());
                    data.setPersonnelName(dealerRequest.getDealerPersonnelInfo().get(i).getPersonnelName());
                    data.setQualification(dealerRequest.getDealerPersonnelInfo().get(i).getQualification());
                    data.setDesignation(dealerRequest.getDealerPersonnelInfo().get(i).getDesignation());
                    data.setCurrentlyWorking(dealerRequest.getDealerPersonnelInfo().get(i).getCurrentlyWorking());
                    data.setCreatedBy(dealerRequest.getUserId());
                    data.setCreatedDate(dateUtil.getTimestamp());

                    Long createRes = dealerRepository.createDealerKeyPersonnel(data);
                    System.out.println("createRes : " + i + " " + createRes);
                    if (createRes > 0) {
                        res = true;
                    } else {
                        res = false;
                    }

                }
            }
            return res;

        } catch (AppException | NumberFormatException ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    public Boolean updateDealerReferences(DealerMasterRequest dealerRequest) {
        Boolean res = false;
        try {
            int dealerId = Integer.parseInt(edUtil.decrypt(String.valueOf(dealerRequest.getDealerId())));
            for (int i = 0; i < dealerRequest.getDealeReferenceInfo().size(); i++) {
                DealerReferencesDTO data = new DealerReferencesDTO();

                if (dealerRequest.getDealeReferenceInfo().get(i).getDealerReferencesId() != null
                        && !"".equals(dealerRequest.getDealeReferenceInfo().get(i).getDealerReferencesId())) {
                    data.setDealerReferencesId(dealerRequest.getDealeReferenceInfo().get(i).getDealerReferencesId());
                    data.setDealerId(dealerRequest.getDealeReferenceInfo().get(i).getDealerId());
                    data.setNameaddress(dealerRequest.getDealeReferenceInfo().get(i).getNameaddress());
                    data.setOccupation(dealerRequest.getDealeReferenceInfo().get(i).getOccupation());
                    data.setContactPerson(dealerRequest.getDealeReferenceInfo().get(i).getContactPerson());
                    data.setPhoneNumber(dealerRequest.getDealeReferenceInfo().get(i).getPhoneNumber());
                    data.setYearsKown(dealerRequest.getDealeReferenceInfo().get(i).getYearsKown());
                    data.setUpdatedBy(dealerRequest.getUserId());
                    data.setUpdatedDate(dateUtil.getTimestamp());

                    Boolean updtRes = dealerRepository.updateDealerReferences(data);
                    res = updtRes;
                    System.out.println("updtRes : " + i + " " + updtRes);
                } else {
                    System.out.println("create references called : ");
                    data.setDealerId(dealerRequest.getDealerId());
                    data.setNameaddress(dealerRequest.getDealeReferenceInfo().get(i).getNameaddress());
                    data.setOccupation(dealerRequest.getDealeReferenceInfo().get(i).getOccupation());
                    data.setContactPerson(dealerRequest.getDealeReferenceInfo().get(i).getContactPerson());
                    data.setPhoneNumber(dealerRequest.getDealeReferenceInfo().get(i).getPhoneNumber());
                    data.setYearsKown(dealerRequest.getDealeReferenceInfo().get(i).getYearsKown());
                    data.setCreatedBy(dealerRequest.getUserId());
                    data.setCreatedDate(dateUtil.getTimestamp());

                    Long createRes = dealerRepository.createDealerReferences(data);
                    System.out.println("createRes : " + i + " " + createRes);
                    if (createRes > 0) {
                        res = true;
                    } else {
                        res = false;
                    }
                }
            }
            return res;
        } catch (AppException | NumberFormatException ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public DealerMasterDTO retrieveByDealershipNo(String dealershipNumber) {
        DealerMasterDTO res;
        try {
            res = dealerRepository.fetchByDealershipNo(dealershipNumber);
            if (res != null) {
                Integer dealerId = Integer.parseInt(edUtil.decrypt(String.valueOf(res.getDealerId())));
                List<DealerBankerDTO> bankerData = dealerRepository.fetchDealerBankerDetails(dealerId);
                List<DealerKeyPersonnelDTO> personnelData = dealerRepository.fetchDealerPersonnelDetails(dealerId);
                List<DealerReferencesDTO> refData = dealerRepository.fetchDealerReferences(dealerId);

                res.setDealerBankerInfo(bankerData);
                res.setDealerPersonnelInfo(personnelData);
                res.setDealeReferenceInfo(refData);

                return res;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    @Override
    public List<DealerMasterDTO> retrieveDealersData(String encryptedUserId) {
        List<DealerMasterDTO> dealersData;
        Integer userId = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedUserId)));
        try {

            Integer distributorId = dealerRepository.fetchDistributorId(userId);
            if (distributorId != 0) {
                dealersData = dealerRepository.fetchDealersData(distributorId);
                if (dealersData != null) {
                    for (int i = 0; i < dealersData.size(); i++) {
                        Integer dealerId = Integer.parseInt(edUtil.decrypt(String.valueOf(dealersData.get(i).getDealerId())));
                        List<DealerBankerDTO> bankerData = dealerRepository.fetchDealerBankerDetails(dealerId);
                        List<DealerKeyPersonnelDTO> personnelData = dealerRepository.fetchDealerPersonnelDetails(dealerId);
                        List<DealerReferencesDTO> refData = dealerRepository.fetchDealerReferences(dealerId);

                        //fetching applicant name and mobilenumber from user table
                        Integer id = Integer.parseInt(edUtil.decrypt(String.valueOf(dealersData.get(i).getUserId())));
                        UsersDTO userRes = userRepository.retrieveUserById(id);

                        if (userRes != null) {
                            String name;
                            if (!"".equals(userRes.getLastName()) && userRes.getLastName() != null) {
                                name = userRes.getFirstName() + " " + userRes.getLastName();
                            } else {
                                name = userRes.getFirstName();
                            }
                            dealersData.get(i).setApplicantName(name);
                            dealersData.get(i).setAppMobile(userRes.getMobileNumber());
                        }
                        dealersData.get(i).setSlNo(i + 1);
                        dealersData.get(i).setDealerBankerInfo(bankerData);
                        dealersData.get(i).setDealerPersonnelInfo(personnelData);
                        dealersData.get(i).setDealeReferenceInfo(refData);
                    }
                    return dealersData;
                }
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public Boolean updateDealerMaster(DealerMasterRequest dealerRequest) {
        Boolean res = false;
        try {

            DealerMasterDTO data = new DealerMasterDTO();
            data.setDealerId(dealerRequest.getDealerId());
            data.setUpdatedBy(dealerRequest.getUserId());
            data.setOnboardDate(dealerRequest.getOnboardDate());
            if (!"".equals(dealerRequest.getStatus()) && dealerRequest.getStatus() != null) {
                data.setStatus(dealerRequest.getStatus());

                // updating onboard date when status is Approved
                if ("Approved".equals(dealerRequest.getStatus())) {
                    data.setOnboardDate(dateUtil.getTimestamp());
                }
                res = dealerRepository.updateDealerStatus(data);

            } else {
                data.setStatus("Additional Documents Submitted");
                data.setKyc(dealerRequest.getKyc());
                data.setAudit(dealerRequest.getAudit());
                data.setPrimissAggrement(dealerRequest.getPrimissAggrement());

                res = dealerRepository.updateDealerAdditionalDocs(data);
            }
            return res;

        } catch (AppException | NumberFormatException ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<StateDTO> retrieveAllState() {
        try {
            List<StateDTO> result = new ArrayList<>();
            result = dealerRepository.fetchAllState();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<HeardFromWhereDTO> retrieveHeardFromWhere() {
        try {
            List<HeardFromWhereDTO> result = new ArrayList<>();
            result = dealerRepository.fetchHeardFromWhere();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String createDealerEnquiry(DealershipEnquiryRequest enquiryRequest) {

        String encriptedId;
        try {
//            UsersDTO res = userRepository.fetchUserByMobNo(enquiryRequest.getContactNo());
            DealershipEnquiryDTO data = new DealershipEnquiryDTO();
            data.setName(enquiryRequest.getName());
            data.setEmail(enquiryRequest.getEmail());
            data.setContactNo(enquiryRequest.getContactNo());
            data.setIsRegistered(enquiryRequest.getIsRegistered());
            data.setCompanyName(enquiryRequest.getCompanyName());
            data.setCompanyAddress(enquiryRequest.getCompanyAddress());
            data.setDesignation(enquiryRequest.getDesignation());
            data.setPreferredState1(enquiryRequest.getPreferredState1());
            data.setPreferredState2(enquiryRequest.getPreferredState2());
            data.setDistrictCity(enquiryRequest.getDistrictCity());
            data.setIsOwnedLeased(enquiryRequest.getIsOwnedLeased());
            data.setAmount(enquiryRequest.getAmount());
            data.setIsExperienced(enquiryRequest.getIsExperienced());
            data.setHeardFromWhere(enquiryRequest.getHeardFromWhere());
            data.setComments(enquiryRequest.getComments());
//            if(res!= null){
//                data.setCreatedBy(res.getUserId());
//            }else{
//                data.setCreatedBy(edUtil.encrypt(String.valueOf(1)));
//            }
            Long id = dealerRepository.createDealerEnquiry(data);
            encriptedId = edUtil.encrypt(String.valueOf(id));
            return encriptedId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String createDealershipEnquiry(NewDealershipEnquiryRequest enquiryRequest) {

        String encriptedId;
        try {

            NewDealershipEnquiryDTO data = new NewDealershipEnquiryDTO();
            data.setIsExperienced(enquiryRequest.getIsExperienced());
            data.setBrand(enquiryRequest.getBrand());
            data.setSinceWhen(enquiryRequest.getSinceWhen());
            data.setPincode1(enquiryRequest.getPincode1());
            data.setPincode2(enquiryRequest.getPincode2());
            data.setPincode3(enquiryRequest.getPincode3());
            data.setAmount(enquiryRequest.getAmount());
            data.setComments(enquiryRequest.getComments());
            data.setCreatedBy(enquiryRequest.getUserId());


            Long id = dealerRepository.createDealershipEnquiry(data);
            if(id != 0 && id != null){
                 UsersDTO res = userService.retrieveUserById(enquiryRequest.getUserId());
                 Boolean emailSent = this.sendEmailToDealer(res);
                 System.out.println("emailSent "+emailSent);
            }
            encriptedId = edUtil.encrypt(String.valueOf(id));
            return encriptedId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
      public Boolean sendEmailToDealer(UsersDTO data) {
        String isEmailSent;

        Timestamp dateNow = dateUtil.getTimestamptoSendSMS();
        String istDate = dateNow.toString().trim().substring(0, 19);
        System.out.println("date String : " + istDate);
        System.out.println("Lastname : " + data.getLastName());
        String lastname = "";
        String fullName = "";
        if (data.getLastName() != null) {
            lastname = data.getLastName();
            fullName = data.getFirstName() + " " + lastname;
        } else {
            fullName = data.getFirstName();
        }

        System.out.println("lastname : " + lastname);

        String jsonInputString = "{\n"
                + "  \"to\": [\n"
                + "    {\n"
                + "      \"name\": \"" + data.getFirstName() + "\",\n"
                + "      \"email\": \"" + data.getEmailId() + "\"\n"
                + "    }]\n"
                + "  ,\n"
                + "  \"from\": {\n"
                + "    \"name\": \"" + emailFromName + "\",\n"
                + "    \"email\": \"" + emailFromMailID + "\"\n"
                + "  },\n"
                + "  \"mail_type_id\": \"1\",\n"
                + "  \"template_id\": \"" + dealerEmailTemplate + "\",\n"
                + "  \"variables\": {\n"
                + "	\"VAR7\":\"" + fullName + "\"\n"
                + "  },\n"
                + "  \"authkey\": \"" + emailAuthKey + "\"\n"
                + "}";

        isEmailSent = emailService.sendEmail(jsonInputString);

        if (isEmailSent.equalsIgnoreCase("success")) {
            return true;
        } else {
            return false;
        }

    }

}
