/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.dao;

import com.boommotors.btp.consumer.dto.ConsumerDetailsDTO;
import com.boommotors.btp.consumer.dto.ConsumerVehicleMappingDTO;
import com.boommotors.btp.consumer.dto.MappedVehiclesDTO;
import com.boommotors.btp.consumer.dto.DashboardDataDTO;
import com.boommotors.btp.consumer.dto.MqttLiveDTO;
import com.boommotors.btp.consumer.dto.MqttLoginDTO;
import com.boommotors.btp.consumer.payload.ConsumerProfileUpdateRequest;
import com.boommotors.btp.consumer.payload.ConsumerchangePasswordRequest;
import com.boommotors.btp.consumer.rowmapper.ConsumerVehicleMappingRowMapper;
import com.boommotors.btp.consumer.rowmapper.ConsumerDetailsRowMapper;
import com.boommotors.btp.consumer.rowmapper.MappedVehiclesRowMapper;
import com.boommotors.btp.consumer.rowmapper.DashboardDataRowMapper;
import com.boommotors.btp.consumer.rowmapper.MqttLiveDataRowMapper;
import com.boommotors.btp.consumer.rowmapper.MqttLoginRowMapper;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dao.*;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ramya
 */
@Repository
public class ConsumerDAOImpl extends JdbcDaoSupport implements ConsumerDAO {

    private static final Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    PasswordEncoder passwordEncoder;

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
    public List<ConsumerVehicleMappingDTO> fetchIsFirstTimeUser(Integer userId) {
        try {
            List<ConsumerVehicleMappingDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE user_id = ?");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{userId}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String validateOTP(Integer userId, Integer otp) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT otp ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE user_id = ?");

            List<Integer> result = getJdbcTemplate().queryForList(sbQry.toString(), Integer.class, new Object[]{userId});
            if (result != null && result.size() > 0) {
                if (Objects.equals(result.get(0), otp)) {
                    return "valid";
                } else {
                    return "invalid";
                }
            } else {
                return "not_found";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "exception";
        }
    }

    @Override
    public Boolean updateOtpVerified(ConsumerVehicleMappingDTO data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            System.out.println("otp verified : " + data.getOtpVerified());

            sbQry.append("UPDATE \"booma\".consumer_vehicle_mapping SET  ");
            sbQry.append("otp_verified = ?  ");
            sbQry.append("WHERE consumer_vehicle_mapping_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        true,
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getConsumerVehicleMappingId()))),});

            System.out.println("updated");

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            return false;
        }
    }

    @Override
    public Boolean updatePassword(ConsumerchangePasswordRequest data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            System.out.println("otp verified : " + data.getNewPassword());

            sbQry.append("UPDATE \"booma\".user SET  ");
            sbQry.append("password = ?  ");
            sbQry.append("WHERE user_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        passwordEncoder.encode(data.getNewPassword()),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUserId()))),});

            System.out.println("updated");

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            return false;
        }
    }

    @Override
    public List<ConsumerDetailsDTO> fetchConsumerDetailsByUserId(Integer userId) {
        List<ConsumerDetailsDTO> result = new ArrayList<>();
        try {
            StringBuilder sbQry = new StringBuilder();
            Boolean countRes = this.existsByUserId(userId);
            if (countRes) {
                sbQry.append("SELECT u.firstname, u.lastname, u.mobile_number, u.email_id, os.*, ot.refund_status, cvm.otp_verified, cvm.consumer_vehicle_mapping_id, cvm.final_save FROM booma.user u ");
                sbQry.append("LEFT JOIN booma.order_summary os ON os.user_id = u.user_id ");
                sbQry.append("LEFT JOIN booma.order_transactions ot ON ot.order_summary_id = os.order_summary_id ");
                sbQry.append("LEFT JOIN booma.consumer_vehicle_mapping cvm ON cvm.order_summary_id = os.order_summary_id ");
                sbQry.append("WHERE u.user_id = ? AND ot.refund_status is NULL ORDER BY os.order_summary_id DESC ");
            } else {
                sbQry.append("SELECT u.firstname, u.lastname, u.mobile_number, u.email_id, os.*, ot.refund_status, cvm.otp_verified, cvm.consumer_vehicle_mapping_id, cvm.final_save FROM booma.user u ");
                sbQry.append("LEFT JOIN booma.order_summary os ON os.user_id = u.user_id ");
                sbQry.append("LEFT JOIN booma.order_transactions ot ON ot.order_summary_id = os.order_summary_id ");
                sbQry.append("LEFT JOIN booma.consumer_vehicle_mapping cvm ON cvm.order_summary_id = os.order_summary_id ");
                sbQry.append("WHERE u.user_id = ? AND ot.refund_status is NULL ORDER BY os.order_summary_id DESC ");
            }

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{userId}, new ConsumerDetailsRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Responding with error. Message - {}");
            return null;

        }
    }

    @Override
    public Boolean existsByUserId(Integer userId) {
        String GET_RECORD_COUNT_SQL = "SELECT COUNT(*) FROM \"booma\".order_summary WHERE user_id = ?";

        int count = getJdbcTemplate().queryForObject(GET_RECORD_COUNT_SQL, new Object[]{userId}, Integer.class);

        return count > 0;
    }

    @Override
    public ConsumerVehicleMappingDTO fetchMappedDataByOrderSummaryId(Integer summaryId) {
        try {
            ConsumerVehicleMappingDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE order_summary_id = ?");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{summaryId}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long createConsumerVehicleMapping(ConsumerVehicleMappingDTO data) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".consumer_vehicle_mapping(user_id, dealer_id, variant_id, variant_name, colour_name, imei_number, otp, booking_date, created_by, created_date, order_summary_id, cluster_id, chassis_number, registration_number, final_save) ");
            sbQry.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUserId()))));
                preparedStatement.setInt(2, Integer.parseInt(edUtil.decrypt(String.valueOf(data.getDealerId()))));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(String.valueOf(data.getVariantId()))));
                preparedStatement.setString(4, data.getVariantName());
                preparedStatement.setString(5, data.getColourName());
                preparedStatement.setString(6, data.getImeiNumber());
                preparedStatement.setInt(7, data.getOtp());
                preparedStatement.setTimestamp(8, data.getBookingDate());
                preparedStatement.setInt(9, Integer.parseInt(edUtil.decrypt(String.valueOf(data.getCreatedBy()))));
                preparedStatement.setTimestamp(10, dateUtil.getTimestamp());
                preparedStatement.setInt(11, Integer.parseInt(edUtil.decrypt(String.valueOf(data.getOrderSummaryId()))));
                preparedStatement.setString(12, data.getClusterId());
                preparedStatement.setString(13, data.getChassisNumber());
                preparedStatement.setString(14, data.getRegistrationNumber());
                preparedStatement.setBoolean(15, data.getFinalSave());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("consumer_vehicle_mapping_id").toString());
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
    public ConsumerVehicleMappingDTO fetchMappedDataByUserId(Integer userId) {
        try {
            ConsumerVehicleMappingDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE user_id = ?");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{userId}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean updateConsumerVehicleMapping(ConsumerVehicleMappingDTO data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".consumer_vehicle_mapping SET ");
            sbQry.append("imei_number = ?, ");
            sbQry.append("OTP = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ?, ");
            sbQry.append("cluster_id = ?, ");
            sbQry.append("chassis_number = ?, ");
            sbQry.append("registration_number = ?, ");
            sbQry.append("final_save = ? ");
            sbQry.append("WHERE order_summary_id = ?");
            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getImeiNumber(),
                        data.getOtp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getCreatedBy()))),
                        dateUtil.getTimestamp(),
                        data.getClusterId(),
                        data.getChassisNumber(),
                        data.getRegistrationNumber(),
                        data.getFinalSave(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getOrderSummaryId())))});
//            System.out.println("sbQry2 : " + sbQry);
            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO clusterIdExistsForAdd(String clusterId) {
        try {
            ConsumerVehicleMappingDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE LOWER(cluster_id) = LOWER(?)");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{clusterId}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO clusterIdExistsForEdit(String clusterId, Integer summaryId) {
        try {
            ConsumerVehicleMappingDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE LOWER(cluster_id) = LOWER(?) AND order_summary_id != ? ");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{clusterId, summaryId}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO chassisNumberExistsForAdd(String chassisNo) {
        try {
            ConsumerVehicleMappingDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE LOWER(chassis_number) = LOWER(?)");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{chassisNo}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO chassisNumberExistsForEdit(String chassisNo, Integer summaryId) {
        try {
            ConsumerVehicleMappingDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE LOWER(chassis_number) = LOWER(?) AND order_summary_id != ? ");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{chassisNo, summaryId}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO registrationNumberExistsForAdd(String registrationNo) {
        try {
            ConsumerVehicleMappingDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE LOWER(registration_number) = LOWER(?)");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{registrationNo}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO registrationNumberExistsForEdit(String registrationNo, Integer summaryId) {
        try {
            ConsumerVehicleMappingDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE LOWER(registration_number) = LOWER(?) AND order_summary_id != ? ");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{registrationNo, summaryId}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO imeiNumberExistsForEdit(String imeiNo, Integer summaryId) {
        try {
            ConsumerVehicleMappingDTO result;
            String action;
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE LOWER(imei_number) = LOWER(?) AND order_summary_id != ? ");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{imeiNo, summaryId}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ConsumerVehicleMappingDTO imeiNumberExistsForAdd(String imeiNo) {
        try {
            ConsumerVehicleMappingDTO result;
            String action;
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE LOWER(imei_number) = LOWER(?) ");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{imeiNo}, new ConsumerVehicleMappingRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DashboardDataDTO fetchMqttLiveData(Long imei) {
        try {
            DashboardDataDTO result;
            System.out.println("here 2");

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT mlive.*, mlogin.odo ");
            sbQry.append("FROM \"booma\".mqtt_live mlive ");
            sbQry.append("join \"booma\".mqtt_login mlogin on mlive.im = mlive.im ");
            sbQry.append("WHERE mlive.im = ?");

            System.out.println("here 3");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{imei}, new DashboardDataRowMapper());
            System.out.println("here 4");
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MqttLoginDTO> fetchMqttLoginData(Long imei) {
        try {
            List<MqttLoginDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".mqtt_login ");
            sbQry.append("WHERE im = ?");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{imei}, new MqttLoginRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MappedVehiclesDTO> fetchAllMappedVehiclesByUserId(Integer userId) {
        try {
            List<MappedVehiclesDTO> result = new ArrayList<>();
            
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".consumer_vehicle_mapping ");
            sbQry.append("WHERE user_id = ?");
            
            
            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{userId}, new MappedVehiclesRowMapper());
            return result;
            
            
        }catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }

    @Override
    public MqttLiveDTO fetchSOC(Long imei) {
        try {
            MqttLiveDTO result;
                        
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".mqtt_live ");
            sbQry.append("WHERE im = ?");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{imei}, new MqttLiveDataRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean updateProfile(ConsumerProfileUpdateRequest data) {
        try {
            StringBuilder sbQry = new StringBuilder();
            System.out.println("userid: " + Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUserId()))));

            sbQry.append("UPDATE \"booma\".user SET  ");
            sbQry.append("firstname = ?,  ");
            sbQry.append("lastname = ?  ");
            sbQry.append("WHERE user_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getFirstName(),
                        data.getLastName(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUserId()))),});

            System.out.println("updated");

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
