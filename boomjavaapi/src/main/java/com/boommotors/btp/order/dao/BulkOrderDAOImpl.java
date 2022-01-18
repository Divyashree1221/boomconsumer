/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dao;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.BulkEnquiryAccessoriesDTO;
import com.boommotors.btp.order.dto.BulkEnquiryDTO;
import com.boommotors.btp.order.dto.BulkEnquiryDetailsDTO;
import com.boommotors.btp.order.dto.BulkEnquiryPartsColourDTO;
import com.boommotors.btp.order.dto.BulkEnquirySuccessDTO;
import com.boommotors.btp.order.dto.ModelAccessoriesDetailsDTO;
import com.boommotors.btp.order.dto.VariantPartsColourDetailsDTO;
import com.boommotors.btp.order.rowmapper.BulkEnquiryDetailsRowmapper;
import com.boommotors.btp.order.rowmapper.BulkEnquirySuccessRowMapper;
import com.boommotors.btp.order.rowmapper.ModelAccessoriesDetailsRowMapper;
import com.boommotors.btp.order.rowmapper.VariantPartsColourDetailsRowMapper;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
 * @author divyashree
 */
@Repository
public class BulkOrderDAOImpl extends JdbcDaoSupport implements BulkOrderDAO {

    private static final Logger logger = LoggerFactory.getLogger(BulkOrderDAOImpl.class);

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
    public Long createBulkEnquiry(BulkEnquiryDTO bulkEnquiry) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".bulk_enquiry( user_id, variant_id, variant_name, colour_name, finance_emi, insurance_type, insurance_amount, ");
            sbQry.append("warranty_amount, delivery_type, pincode, city, state, country, is_out_of_coverage, quantity, rfp_mail_sent, rfp_sms_sent, mail_to_sales_sent, total_amount, advance_amount, ");
            sbQry.append("amount_paid, package_name, colour_amount, ex_showroom, package_amt, sub_total, gst_amt, gross_total, subsidy_amt, created_by, created_date, tenure, exchange) ");
            sbQry.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(String.valueOf(bulkEnquiry.getUserId()))));
                preparedStatement.setInt(2, Integer.parseInt(edUtil.decrypt(String.valueOf(bulkEnquiry.getVariantId()))));
                preparedStatement.setString(3, bulkEnquiry.getVariantName());
                preparedStatement.setString(4, bulkEnquiry.getColourName());
                preparedStatement.setDouble(5, bulkEnquiry.getFinanceEmi());
                preparedStatement.setString(6, bulkEnquiry.getInsuranceType());
                preparedStatement.setDouble(7, bulkEnquiry.getInsuranceAmount());
                preparedStatement.setDouble(8, bulkEnquiry.getWarrantyAmount());
                preparedStatement.setString(9, bulkEnquiry.getDelivery_type());
                preparedStatement.setString(10, bulkEnquiry.getPincode());
                preparedStatement.setString(11, bulkEnquiry.getCity());
                preparedStatement.setString(12, bulkEnquiry.getState());
                preparedStatement.setString(13, bulkEnquiry.getCountry());
                preparedStatement.setBoolean(14, bulkEnquiry.getIsOutOfCoverage());
                preparedStatement.setInt(15, bulkEnquiry.getQuantity());
                preparedStatement.setBoolean(16, bulkEnquiry.getRfpMailSent());
                preparedStatement.setBoolean(17, bulkEnquiry.getRfpSmsSent());
                preparedStatement.setBoolean(18, bulkEnquiry.getMailToSalesSent());
                preparedStatement.setDouble(19, bulkEnquiry.getTotalAmount());
                preparedStatement.setDouble(20, bulkEnquiry.getAdvanceAmount());
                preparedStatement.setDouble(21, bulkEnquiry.getAmountPaid());
                preparedStatement.setString(22, bulkEnquiry.getPackageName());
                preparedStatement.setDouble(23, bulkEnquiry.getColourAmount());
                preparedStatement.setDouble(24, bulkEnquiry.getExShowroom());
                preparedStatement.setDouble(25, bulkEnquiry.getPackageAmt());
                preparedStatement.setDouble(26, bulkEnquiry.getSubTotal());
                preparedStatement.setDouble(27, bulkEnquiry.getGstAmt());
                preparedStatement.setDouble(28, bulkEnquiry.getGrossTotal());
                preparedStatement.setDouble(29, bulkEnquiry.getSubsidyAmt());
                preparedStatement.setInt(30, Integer.parseInt(edUtil.decrypt(String.valueOf(bulkEnquiry.getCreatedBy()))));
                preparedStatement.setTimestamp(31, bulkEnquiry.getCreatedDate());
                preparedStatement.setInt(32, bulkEnquiry.getTenure());
                preparedStatement.setString(33, bulkEnquiry.getExchange());
                
                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("bulk_enquiry_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create bulk enquiry");
        }
    }

    @Override
    public Long createBulkEnquiryPartColour(BulkEnquiryPartsColourDTO enquiryPartsColour) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".bulk_enquiry_parts_colour(bulk_enquiry_id, variant_parts_colour_id, created_by, created_date) ");
            sbQry.append("VALUES (?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(enquiryPartsColour.getBulkEnquiryId()));
                preparedStatement.setInt(2, Integer.parseInt(edUtil.decrypt(String.valueOf(enquiryPartsColour.getVariantPartsColourId()))));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(enquiryPartsColour.getCreatedBy())));
                preparedStatement.setTimestamp(4, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("bulk_enquiry_parts_colour_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create bulk enquiry parts colour");
        }
    }

    @Override
    public Long createBulkEnquiryAccessories(BulkEnquiryAccessoriesDTO enquiryAccessories) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".bulk_enquiry_accessories(bulk_enquiry_id, model_accessories_id, created_by, created_date) ");
            sbQry.append("VALUES (?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(enquiryAccessories.getBulkEnquiryId()));
                preparedStatement.setInt(2, Integer.parseInt(edUtil.decrypt(String.valueOf(enquiryAccessories.getModelAccessoriesId()))));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(enquiryAccessories.getCreatedBy())));
                preparedStatement.setTimestamp(4, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("bulk_enquiry_accessories_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create bulk enquiry accessories ");
        }
    }

    @Override
    public boolean updateBulkEnquiry(BulkEnquiryDTO bulkEnquiry) {
        try {

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".bulk_enquiry SET ");
            sbQry.append("rfp_mail_sent  = ? , rfp_sms_sent = ?, mail_to_sales_sent = ?, updated_by = ?, updated_date = ? ");
            sbQry.append("WHERE bulk_enquiry_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        bulkEnquiry.getRfpMailSent(),
                        bulkEnquiry.getRfpSmsSent(),
                        bulkEnquiry.getMailToSalesSent(),
                        Integer.parseInt((edUtil.decrypt(bulkEnquiry.getUpdatedBy()))),
                        bulkEnquiry.getUpdatedDate(),
                        Integer.parseInt(edUtil.decrypt(bulkEnquiry.getBulkEnquiryId())),});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BulkEnquirySuccessDTO FetchBulkEnquiryByID(String encryptedBulkEnquiryID) throws EtAuthException {

        try {
            BulkEnquirySuccessDTO result;
            long BulkEnquiryId = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedBulkEnquiryID)));

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT u.firstname,u.lastname,be.quantity, be.variant_name,m.model_name FROM \"booma\".bulk_enquiry be ");
            sbQry.append("join \"booma\".user u on u.user_id = be.user_id ");
            sbQry.append("join \"booma\".variant v on v.variant_id = be.variant_id ");
            sbQry.append("join \"booma\".model m on m.model_id = v.model_id ");
            sbQry.append("WHERE bulk_enquiry_id = ?");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{BulkEnquiryId}, new BulkEnquirySuccessRowMapper());
            System.out.println("RESULT :: " + result);

            if (result != null) {

                return result;
            } else {
                return null;
            }

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BulkEnquiryDetailsDTO> fetchBulkEnquiryByUserID(int userId) throws EtAuthException {

        try {
            List<BulkEnquiryDetailsDTO> result;

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT BE.*, V.battery_type ");
            sbQry.append("FROM \"booma\".bulk_enquiry BE ");
            sbQry.append("JOIN booma.variant V ON V.variant_id = BE.variant_id ");
            sbQry.append("WHERE BE.user_id = ? ");
            sbQry.append("ORDER BY BE.bulk_enquiry_id DESC ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{userId}, new BulkEnquiryDetailsRowmapper());

            if (result != null && !result.isEmpty()) {

                return result;
            } else {
                return null;
            }

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<ModelAccessoriesDetailsDTO> fetchModelAccessories(int bulkEnquiryId){
        
         try {
            List<ModelAccessoriesDetailsDTO> result;
   
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT ma.model_accessories_id, ma.model_accessories_details, ma.price ");
            sbQry.append("FROM \"booma\".bulk_enquiry_accessories bea ");
            sbQry.append("JOIN booma.model_accessories ma ");
            sbQry.append("ON ma.model_accessories_id = bea.model_accessories_id ");
            sbQry.append("WHERE bea.bulk_enquiry_id = ? ");
            sbQry.append("ORDER BY bea.bulk_enquiry_accessories_id");
            
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{bulkEnquiryId}, new ModelAccessoriesDetailsRowMapper());
            if (result != null) {
                return result;
            } else {
                return null;
            }

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<VariantPartsColourDetailsDTO> fetchPartsColourDetails(int bulkEnquiryId){      
         try {
            List<VariantPartsColourDetailsDTO> result;
   
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT vpc.variant_parts_colour_id, vpc.variant_part_name, vpc.colour_name ");
            sbQry.append("FROM \"booma\".bulk_enquiry_parts_colour bepc ");
            sbQry.append("JOIN \"booma\".variant_parts_colour vpc ");
            sbQry.append("ON vpc.variant_parts_colour_id = bepc.variant_parts_colour_id ");
            sbQry.append("WHERE bepc.bulk_enquiry_id = ? ");
            sbQry.append("ORDER BY bepc.bulk_enquiry_parts_colour_id");
            
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{bulkEnquiryId}, new VariantPartsColourDetailsRowMapper());

            if (result != null) {
                return result;
            } else {
                return null;
            }

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
