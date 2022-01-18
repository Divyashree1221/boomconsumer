/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payment.dao;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.order.rowmapper.OrderDetailRowMapper;
import com.boommotors.btp.order.rowmapper.OrderSummaryRowMapper;
import com.boommotors.btp.payment.dto.PaymentDetailsDTO;
import com.boommotors.btp.payment.rowmapper.PaymentDetailsRowMapper;
import com.boommotors.btp.order.rowmapper.OrderTransactionsRowMapper;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
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
public class PaymentDAOImpl extends JdbcDaoSupport implements PaymentDAO {

    private static final Logger logger = LoggerFactory.getLogger(PaymentDAOImpl.class);

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
    public OrderSummaryDTO fetchOrderSummary(Integer orderSummaryId) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".order_summary ");
            sbQry.append("WHERE order_summary_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{orderSummaryId}, new OrderSummaryRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Long saveOrderDtail(OrderDetailDTO data) {
        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".order_detail( order_summary_id, razorpay_order_id, payment_type, amount ,is_paid, created_by, created_date ) ");
            sbQry.append("VALUES (?,?,?,?,?,?,?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, (Integer.parseInt(edUtil.decrypt(data.getOrderSummaryId()))));
                preparedStatement.setString(2, data.getRazorpayOrderId());
                preparedStatement.setString(3, data.getPaymentType());
                preparedStatement.setDouble(4, (Double.valueOf(data.getAmount())));
                preparedStatement.setBoolean(5, data.getIsPaid());
                preparedStatement.setInt(6, (Integer.parseInt(edUtil.decrypt(data.getCreatedBy()))));
                preparedStatement.setTimestamp(7, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("order_detail_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. ");
        }
    }

    @Override
    public Long saveOrderTransaction(OrderTransactionDTO data) {
        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();
            System.out.println("Auth Code : " + data.getAuthCode());

            sbQry.append("INSERT INTO \"booma\".order_transactions");
            sbQry.append("(order_summary_id, razorpay_payment_id, razorpay_order_id, payment_success, ");
            sbQry.append("status, payment_method, card_id, captured, entity, currency, ");
            sbQry.append("description, bank, wallet, vpa, fee, refund_status, auth_code, ");
            sbQry.append("amount_refunded, error_reason, error_description, contact, invoice_id, ");
            sbQry.append("international, amount, error_source, error_step, tax, error_code, ");
            sbQry.append("email_id, created_by, created_date) ");
            sbQry.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, (Integer.parseInt(edUtil.decrypt(data.getOrderSummaryId()))));
                preparedStatement.setString(2, data.getRazorpayPaymentId());
                preparedStatement.setString(3, data.getRazorpayOrderId());
                preparedStatement.setInt(4, data.getPaymentSuccess());
                preparedStatement.setString(5, data.getStatus());
                preparedStatement.setString(6, data.getPaymentMethod());
                preparedStatement.setString(7, data.getCardId());
                preparedStatement.setBoolean(8, data.getCaptured());
                preparedStatement.setString(9, data.getEntity());
                preparedStatement.setString(10, data.getCurrency());
                preparedStatement.setString(11, data.getDescription());
                preparedStatement.setString(12, data.getBank());
                preparedStatement.setString(13, data.getWallet());
                preparedStatement.setString(14, data.getVpa());
                preparedStatement.setDouble(15, data.getFee());
                preparedStatement.setString(16, data.getRefundStatus());
                preparedStatement.setString(17, data.getAuthCode());
                preparedStatement.setDouble(18, data.getAmountRefunded());
                preparedStatement.setString(19, data.getErrorReason());
                preparedStatement.setString(20, data.getErrorDescription());
                preparedStatement.setString(21, data.getContact());
                preparedStatement.setString(22, data.getInvoiceId());
                preparedStatement.setBoolean(23, data.getInternational());
                preparedStatement.setDouble(24, data.getAmount());
                preparedStatement.setString(25, data.getErrorSource());
                preparedStatement.setString(26, data.getErrorStep());
                preparedStatement.setDouble(27, data.getTax());
                preparedStatement.setString(28, data.getErrorCode());
                preparedStatement.setString(29, data.getEmail());
                preparedStatement.setInt(30, (Integer.parseInt(edUtil.decrypt(data.getCreatedBy()))));
                preparedStatement.setTimestamp(31, data.getCreatedDate());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("order_transactions_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. ");
        }
    }

    @Override
    public OrderDetailDTO fetchOrderDetailByRazorPayOrderId(String razorpayOrderId) {
        try {
            System.out.println("ID : " + razorpayOrderId);
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".order_detail ");
            sbQry.append("WHERE razorpay_order_id = ?");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{razorpayOrderId}, new OrderDetailRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean updateOrderDetailIsPaid(OrderDetailDTO data) {

        try {

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".order_detail SET ");
            sbQry.append(" is_paid  = ?, ");
            sbQry.append(" updated_by  = ?, ");
            sbQry.append(" updated_date  = ? ");
            sbQry.append(" WHERE order_detail_id = ?");

            int[] types = new int[]{Types.BOOLEAN, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER};

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getIsPaid(),
                        Integer.parseInt(edUtil.decrypt(data.getUpdatedBy())),
                        data.getUpdatedDate(),
                        Integer.parseInt(edUtil.decrypt(data.getOrderDetailId()))}, types);

            return isRecordUpdated > 0;
        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PaymentDetailsDTO fetchPaymentDetails(String paymentId) {
        try {

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT ot.razorpay_payment_id, ot.razorpay_order_id, os.order_summary_id, os.variant_name, m.model_name, os.advance_amount, os.amount_paid ");
            sbQry.append("FROM \"booma\".order_transactions ot ");
            sbQry.append("JOIN \"booma\".order_summary os ON os.order_summary_id = ot.order_summary_id ");
            sbQry.append("JOIN \"booma\".variant v ON v.variant_id = os.variant_id ");
            sbQry.append("JOIN \"booma\".model m ON v.model_id = m.model_id ");
            sbQry.append("WHERE razorpay_payment_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{paymentId}, new PaymentDetailsRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public OrderTransactionDTO fetchOrderTransaction(String razorpayPaymentId) {
        try {

            System.out.println("Razoy PaymentId : " + razorpayPaymentId);
            
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT OT.*, OS.quantity ");
            sbQry.append("FROM \"booma\".order_transactions OT ");
            sbQry.append("JOIN \"booma\".order_summary OS ON OT.order_summary_id = OS.order_summary_id ");
            sbQry.append("WHERE OT.razorpay_payment_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{razorpayPaymentId}, new OrderTransactionsRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public boolean updateOrderSummaryAmount(OrderSummaryDTO data) {

        try {

            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".order_summary SET ");
            sbQry.append(" amount_paid  = ?, ");
            sbQry.append(" updated_by  = ?, ");
            sbQry.append(" updated_date  = ? ");
            sbQry.append(" WHERE order_summary_id = ?");

            int[] types = new int[]{Types.DOUBLE, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER};

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        data.getAmountPaid(),
                        Integer.parseInt(edUtil.decrypt(data.getUpdatedBy())),
                        data.getUpdatedDate(),
                        Integer.parseInt(edUtil.decrypt(data.getOrderSummaryId()))}, types);

            return isRecordUpdated > 0;
        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
