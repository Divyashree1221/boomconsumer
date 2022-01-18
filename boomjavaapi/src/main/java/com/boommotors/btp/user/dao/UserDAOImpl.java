/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.user.dao;

import com.boommotors.btp.dto.User;
import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.rowmapper.UserRowMapper;
import com.boommotors.btp.user.dto.UserOrderDetailsDTO;
import com.boommotors.btp.user.dto.UserRoleDTO;
import com.boommotors.btp.user.rowmapper.UsersRowMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.boommotors.btp.user.dto.UsersDTO;
import com.boommotors.btp.user.rowmapper.UserOrderDetailsRowMapper;
import com.boommotors.btp.user.rowmapper.UserRoleRowMapper;
import java.util.Objects;

/**
 *
 * @author divyashree
 */
@Repository
public class UserDAOImpl extends JdbcDaoSupport implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Long create(User user) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".user( firstname, lastname, email_id, mobile_number ,created_date, social_id, provider, user_role_id, password, account_locked, failed_attempt, lock_time) ");
            sbQry.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getMobileNumber());
                preparedStatement.setTimestamp(5, dateUtil.getTimestamp());
                preparedStatement.setString(6, user.getSocialId());
                preparedStatement.setString(7, user.getProvider());
                preparedStatement.setInt(8, user.getUserRoleId());
                preparedStatement.setString(9, user.getPassword());
                preparedStatement.setBoolean(10, true);
                preparedStatement.setInt(11, 0);
                preparedStatement.setTimestamp(12, null);

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("user_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create user");
        }
    }

//    @Override
//    public Boolean updatePassword(UsersDTO data) {
//        try {
//            StringBuilder sbQry = new StringBuilder();
//
//            sbQry.append("UPDATE user SET  ");
//            sbQry.append("password = ?, ");
//            sbQry.append("updated_at = ? ");
//            sbQry.append("WHERE ");
//            sbQry.append("user_id = ? ");
//
//            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
//                    new Object[]{
//                        passwordEncoder.encode(data.getPassword()),
//                        data.getUpdatedBy(),
//                        data.getUserId()
//                    }
//            );
//
//            return isRecordUpdated > 0;
//
//        } catch (DataAccessException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    @Override
    public Integer getCountByEmail(String email) {
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT count(*) ");
            sbQry.append("FROM \"booma\".user WHERE LOWER(email_id) = LOWER(?) ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{email}, Integer.class);

        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public User findById(Integer userId) {

        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user WHERE user_id = ?");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new UserRowMapper(), new Object[]{userId});

        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public UsersDTO retrieveUserById(Integer userId) {

        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * FROM \"booma\".user WHERE user_id = ?");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new UsersRowMapper(), new Object[]{userId});

        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public UsersDTO fetchUserByEmail(String email) {
        UsersDTO result;
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user ");
            sbQry.append("WHERE LOWER(email_id) = LOWER(?)");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new UsersRowMapper(), new Object[]{email});
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
    public UsersDTO fetchUsersByEmailOrMobno(String email, String mobno) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user ");
            sbQry.append("WHERE LOWER(email_id) = LOWER(?) OR mobile_number = ? LIMIT 1");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new UsersRowMapper(), new Object[]{email, mobno});

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UsersDTO fetchUserByEmailOrMobno(String mobileNoOrEmail) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user ");
            sbQry.append("WHERE LOWER(email_id) = LOWER(?) OR mobile_number = ?");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new UsersRowMapper(), new Object[]{mobileNoOrEmail, mobileNoOrEmail});

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User fetchByEmail(String mobNo) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user ");
            sbQry.append("WHERE mobile_number = ?");
            //sbQry.append("WHERE LOWER(email_id) = LOWER(?)");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new UserRowMapper(), new Object[]{mobNo});

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UsersDTO fetchUserByMobNo(String mobno) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user ");
            sbQry.append("WHERE mobile_number = ?");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new UsersRowMapper(), new Object[]{mobno});

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UsersDTO> retrieveAllUsers() {
        List<UsersDTO> result = new ArrayList<>();
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT * FROM \"booma\".user");

            result = getJdbcTemplate().query(sbQry.toString(), new UsersRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Responding with error. Message - {}");
            return null;

        }

    }

    @Override
    public Boolean updateUserOtp(UsersDTO userdto) {
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("UPDATE \"booma\".user SET  ");
            sbQry.append("loginotp = ?, ");
            sbQry.append("mobile_otp_issue_time = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE ");
            sbQry.append("mobile_number = ?  ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        userdto.getLoginOtp(),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(userdto.getUserId())),
                        dateUtil.getTimestamp(),
                        userdto.getMobileNumber(),}
            );

            return isRecordUpdated > 0;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Responding with error. Message - {}");
            return null;

        }
    }

    @Override
    public List<UsersDTO> findAllByMobileNo(String mobileNo) {
//        System.out.println("mobno : " + mobileNo);
        List<UsersDTO> resultData = new ArrayList<>();
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user ");
            sbQry.append("WHERE mobile_number = ? OR email_id = ?");

            resultData = getJdbcTemplate().query(sbQry.toString(), new UsersRowMapper(), new Object[]{mobileNo, mobileNo});
//            System.out.println("resultData : " + resultData.toString());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return resultData;
    }

    @Override
    public String validateOTP(String mobileNoOrEmail, Integer otp) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT loginotp ");
            sbQry.append("FROM \"booma\".\"user\" ");
            sbQry.append("WHERE mobile_number = ? OR email_id = ?");

            List<Integer> result = getJdbcTemplate().queryForList(sbQry.toString(), Integer.class, new Object[]{mobileNoOrEmail, mobileNoOrEmail});
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
            return "exception";
        }
    }

    @Override
    public Boolean deleteOtp(Long id, UsersDTO data) {
        try {

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".user ");
            sbQry.append("SET loginotp = null, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE  user_id = ? ");

            int isRecordStatusUpdated = getJdbcTemplate().update(sbQry.toString(), new Object[]{
                Integer.parseInt(edUtil.decrypt(data.getUserId())),
                dateUtil.getTimestamp(),
                Integer.parseInt(edUtil.decrypt(data.getUserId()))

            });

            return isRecordStatusUpdated > 0;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Responding with error. Message - {}");
            return false;

        }
    }

    @Override
    public Boolean updateOTPVerificationStatus(UsersDTO data) {
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("UPDATE \"booma\".user SET ");
            sbQry.append("mobile_verified = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE ");
            sbQry.append("user_id = ? ");

            int isRecordStatusUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getMobile_verified(),
                        Integer.parseInt(edUtil.decrypt(data.getUserId())), dateUtil.getTimestamp(), Integer.parseInt(edUtil.decrypt(data.getUserId()))});

            return isRecordStatusUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            //e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateProfile(UsersDTO data) {
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".\"user\" SET  ");
            sbQry.append("firstname = ?, ");
            sbQry.append("lastname = ? ");
            sbQry.append("WHERE ");
            sbQry.append("\"user_id\" = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getFirstName(),
                        data.getLastName(),
                        Integer.parseInt(edUtil.decrypt(data.getUserId())),});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateUserEmailOtp(UsersDTO userdto) {
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("UPDATE \"booma\".user SET  ");
            sbQry.append("email_id = ?, ");
            sbQry.append("email_verified = ?, ");
            sbQry.append("email_otp = ?, ");
            sbQry.append("email_otp_issue_time = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE ");
            sbQry.append("mobile_number = ?  ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        userdto.getEmailId(),
                        userdto.getEmailVerified(),
                        userdto.getEmailOtp(),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(userdto.getUserId())),
                        dateUtil.getTimestamp(),
                        userdto.getMobileNumber(),}
            );

            return isRecordUpdated > 0;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Responding with error. Message - {}");
            return null;

        }
    }

    @Override
    public String verify_Email_Otp(String email, Integer emailOtp) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT email_otp ");
            sbQry.append("FROM \"booma\".\"user\" ");
            sbQry.append("WHERE email_id = ?");

            List<Integer> result = getJdbcTemplate().queryForList(sbQry.toString(), Integer.class, new Object[]{email});
            if (result != null && result.size() > 0) {
                if (Objects.equals(result.get(0), emailOtp)) {
                    return "valid";
                } else {
                    return "invalid";
                }
            } else {
                return "not_found";
            }

        } catch (Exception e) {
            return "exception";
        }
    }

    @Override
    public Boolean updateEmailVerificationStatus(UsersDTO data) {
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("UPDATE \"booma\".user SET ");
            sbQry.append("email_verified = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE ");
            sbQry.append("user_id = ? ");

            int isRecordStatusUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getEmailVerified(),
                        Integer.parseInt(edUtil.decrypt(data.getUserId())), dateUtil.getTimestamp(), Integer.parseInt(edUtil.decrypt(data.getUserId()))});

            return isRecordStatusUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            //e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserRoleDTO> fetchUserRoleByUserType(String roleType) {
        try {
            List<UserRoleDTO> res = null;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user_role ");
            sbQry.append("WHERE role_type = ?");

            res = getJdbcTemplate().query(sbQry.toString(), new UserRoleRowMapper(), new Object[]{roleType});
            return res;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User fetchUserByEmailForConsumerApp(String email) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".user ");
            sbQry.append("WHERE email_id = ? OR mobile_number = ?");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new UserRowMapper(), new Object[]{email, email});

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UsersDTO> fetchAllConsumers() {
        List<UsersDTO> result = new ArrayList<>();
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT * FROM \"booma\".user WHERE user_role_id = 3 ORDER BY user_id DESC");

            result = getJdbcTemplate().query(sbQry.toString(), new UsersRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Responding with error. Message - {}");
            return null;

        }

    }

    @Override
    public List<UserOrderDetailsDTO> fetchUserOrderedDataByUserId(Integer userId) {
        List<UserOrderDetailsDTO> result = new ArrayList<>();
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT u.user_id, u.firstname, u.lastname, u.mobile_number, u.email_id, os.order_summary_id, os.created_date, os.variant_id, os.variant_name, ");
            sbQry.append("os.colour_name, os.quantity, ot.refund_status AS order_status, ot.razorpay_payment_id, ot.razorpay_order_id, od.is_paid as payment_status ");
            sbQry.append("FROM \"booma\".user u JOIN \"booma\".order_summary os ON os.user_id = u.user_id ");
            sbQry.append("JOIN \"booma\".order_transactions ot ON os.order_summary_id = ot.order_summary_id ");
            sbQry.append("JOIN \"booma\".order_detail od ON os.order_summary_id = od.order_summary_id WHERE u.user_id = ? ORDER BY os.order_summary_id DESC");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{userId}, new UserOrderDetailsRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Responding with error. Message - {}");
            return null;

        }
    }

    @Override
    public Boolean updateUserInfo(UsersDTO data) {
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".user SET ");
            sbQry.append("firstname = ?, ");
            sbQry.append("lastname = ?, ");
            sbQry.append("email_id = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE ");
            sbQry.append("user_id = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getFirstName(),
                        data.getLastName(),
                        data.getEmailId(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy()))),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUserId())))});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateFailedAttempts(UsersDTO data) {
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".user SET ");
            sbQry.append("account_locked = ?, ");
            sbQry.append("failed_attempt = ?, ");
            sbQry.append("lock_time = ? ");
            sbQry.append("WHERE ");
            sbQry.append("mobile_number = ? ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getAccountLocked(),
                        data.getFailedAttempt(),
                        data.getLockTime(),
                        data.getMobileNumber()});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

}
