/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dao;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.OrderAccessoriesDTO;
import com.boommotors.btp.order.dto.OrderDetailDTO;
import com.boommotors.btp.order.dto.OrderEVStatusDTO;
import com.boommotors.btp.order.dto.OrderFinanceStatusDTO;
import com.boommotors.btp.order.dto.OrderPartsColourDTO;
import com.boommotors.btp.order.dto.OrderPartsImgUrlDTO;
import com.boommotors.btp.order.dto.OrderSummaryAndRazorpayDTO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.order.dto.OrderTransactionDTO;
import com.boommotors.btp.order.dto.OrderTransactionStatusDTO;
import com.boommotors.btp.order.dto.TransactionDetailsDTO;
import com.boommotors.btp.order.rowmapper.OrderDetailRowMapper;
import com.boommotors.btp.order.rowmapper.OrderEVStatusRowmapper;
import com.boommotors.btp.order.rowmapper.OrderFinanceStatusRowmapper;
import com.boommotors.btp.order.rowmapper.OrderPartsImgUrlRowMapper;
import com.boommotors.btp.order.rowmapper.OrderSummaryAndRazorpayRowMapper;
import com.boommotors.btp.order.rowmapper.OrderSummaryRowMapper;
import com.boommotors.btp.order.rowmapper.OrderTransactionStatusRowMapper;
import com.boommotors.btp.order.rowmapper.OrderTransactionsRowMapper;
import com.boommotors.btp.order.rowmapper.TransactionDetailsRowMapper;
import com.boommotors.btp.util.DateUtil;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author NandiniC
 */
@Repository
public class OrderDAOImpl extends JdbcDaoSupport implements OrderDAO {

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
    public Long createOrderSummary(OrderSummaryDTO orderSummary) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".order_summary( user_id, variant_id, variant_name, colour_name, finance_emi, insurance_type, insurance_amount, ");
            sbQry.append("warranty_amount, delivery_type, pincode, city, state, country, is_out_of_coverage, quantity, rfp_sent, total_amount, advance_amount, ");
            sbQry.append("amount_paid, created_by, created_date, package_name, colour_amount, ex_showroom, package_amt, sub_total, gst_amt, gross_total,subsidy_amt, tenure, exchange) ");
            sbQry.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(String.valueOf(orderSummary.getUserId()))));
                preparedStatement.setInt(2, Integer.parseInt(edUtil.decrypt(String.valueOf(orderSummary.getVariantId()))));
                preparedStatement.setString(3, orderSummary.getVariantName());
                preparedStatement.setString(4, orderSummary.getColourName());
                preparedStatement.setDouble(5, orderSummary.getFinanceEmi());
                preparedStatement.setString(6, orderSummary.getInsuranceType());
                preparedStatement.setDouble(7, orderSummary.getInsuranceAmount());
                preparedStatement.setDouble(8, orderSummary.getWarrantyAmount());
                preparedStatement.setString(9, orderSummary.getDelivery_type());
                preparedStatement.setString(10, orderSummary.getPincode());
                preparedStatement.setString(11, orderSummary.getCity());
                preparedStatement.setString(12, orderSummary.getState());
                preparedStatement.setString(13, orderSummary.getCountry());
                preparedStatement.setBoolean(14, orderSummary.getIsOutOfCoverage());
                preparedStatement.setInt(15, orderSummary.getQuantity());
                preparedStatement.setBoolean(16, orderSummary.getRfpSent());
                preparedStatement.setDouble(17, orderSummary.getTotalAmount());
                preparedStatement.setDouble(18, orderSummary.getAdvanceAmount());
                preparedStatement.setDouble(19, orderSummary.getAmountPaid());
                preparedStatement.setInt(20, Integer.parseInt(edUtil.decrypt(String.valueOf(orderSummary.getUserId()))));
                preparedStatement.setTimestamp(21, dateUtil.getTimestamp());
                preparedStatement.setString(22, orderSummary.getPackageName());
                preparedStatement.setDouble(23, orderSummary.getColourAmount());
                preparedStatement.setDouble(24, orderSummary.getExShowroom());
                preparedStatement.setDouble(25, orderSummary.getPackageAmt());
                preparedStatement.setDouble(26, orderSummary.getSubTotal());
                preparedStatement.setDouble(27, orderSummary.getGstAmt());
                preparedStatement.setDouble(28, orderSummary.getGrossTotal());
                preparedStatement.setDouble(29, orderSummary.getSubsidyAmt());
                preparedStatement.setInt(30, orderSummary.getTenure());
                preparedStatement.setString(31, orderSummary.getExchange());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("order_summary_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create order");
        }

    }

    @Override
    public Long createOrderPartColour(OrderPartsColourDTO orderPartsColour) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".order_parts_colour(order_summary_id, variant_parts_colour_id, created_by, created_date) ");
            sbQry.append("VALUES (?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(orderPartsColour.getOrderSummaryId()));
                preparedStatement.setInt(2, Integer.parseInt(edUtil.decrypt(String.valueOf(orderPartsColour.getVariantPartsColourId()))));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(orderPartsColour.getCreatedBy())));
                preparedStatement.setTimestamp(4, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("order_parts_colour_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create order parts colour");
        }
    }

    @Override
    public Long createOrderAccessories(OrderAccessoriesDTO orderAccessories) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".order_accessories(order_summary_id, model_accessories_id, created_by, created_date) ");
            sbQry.append("VALUES (?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(orderAccessories.getOrderSummaryId()));
                preparedStatement.setInt(2, Integer.parseInt(edUtil.decrypt(String.valueOf(orderAccessories.getModelAccessoriesId()))));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(orderAccessories.getCreatedBy())));
                preparedStatement.setTimestamp(4, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("order_accessories_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create order accessories ");
        }
    }

    @Override
    public List<OrderSummaryAndRazorpayDTO> fetchOrderSummaryByUserId(Integer userId) {
        try {

            List<OrderSummaryAndRazorpayDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT OS.*, ");
            sbQry.append("(SELECT (sum(amount)+OS.subsidy_amt) AS total_paid FROM \"booma\".order_detail WHERE order_summary_id = OS.order_summary_id AND is_paid = true ) AS total_paid, ");
            sbQry.append("(SELECT razorpay_order_id FROM \"booma\".order_detail WHERE order_summary_id = OS.order_summary_id AND is_paid = true limit 1 ) AS razorpay_order_id ");
            sbQry.append("FROM \"booma\".order_summary OS ");
            sbQry.append("WHERE OS.user_id = ? ");
            sbQry.append("ORDER BY OS.order_summary_id DESC");
            // System.out.println("query "+sbQry.toString());
            result = jdbcTemplate.query(sbQry.toString(), new Object[]{userId}, new OrderSummaryAndRazorpayRowMapper());

            return result;

        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    @Override
    public List<OrderSummaryDTO> fetchOrderSummaryByUserIdForStatus(Integer userId) {
        try {

            List<OrderSummaryDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".order_summary ");
            sbQry.append("WHERE user_id = ?");

            result = jdbcTemplate.query(sbQry.toString(), new Object[]{userId}, new OrderSummaryRowMapper());

            return result;

        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    @Override
    public List<OrderEVStatusDTO> fetchOrderEVStatus(Integer orderSummaryId) {

        try {

            List<OrderEVStatusDTO> result = new ArrayList<>();
            StringBuilder sbQry = new StringBuilder();
            Integer evRes = this.fetchOrderEvStatusBySummaryId(orderSummaryId);
            if (evRes != null) {
//            System.out.println("status Id : "+edUtil.decrypt(String.valueOf(evRes.getEvStatusId())));
//            Integer statusId = Integer.parseInt(edUtil.decrypt(String.valueOf(evRes.getEvStatusId())));
                if (evRes == 0) {
                    sbQry.append("SELECT DISTINCT es.*,oes.*,os.variant_name,oes.ev_status_id AS order_EVStatusId ");
                    sbQry.append("FROM \"booma\".ev_status es ");
                    sbQry.append("left join booma.order_ev_status oes ");
                    sbQry.append("on es.ev_status_id = oes.ev_status_id and oes.order_summary_id = ? ");
                    sbQry.append("left join booma.order_summary os ");
                    sbQry.append("on os.order_summary_id = oes.order_summary_id ");
                    sbQry.append("WHERE es.ev_status_id IN (1,2,3,4)");
                    sbQry.append("order by es.ev_status_id");
                } else {
                    sbQry.append("SELECT DISTINCT es.*,oes.*,os.variant_name,oes.ev_status_id AS order_EVStatusId ");
                    sbQry.append("FROM \"booma\".ev_status es ");
                    sbQry.append("join booma.order_ev_status oes ");
                    sbQry.append("on es.ev_status_id = oes.ev_status_id and oes.order_summary_id = ? ");
                    sbQry.append("left join booma.order_summary os ");
                    sbQry.append("on os.order_summary_id = oes.order_summary_id ");
                    sbQry.append("order by es.ev_status_id");
                }
            }

            //return jdbcTemplate.queryForObject(sbQry.toString(), new Object[]{orderSummaryId}, new OrderEVStatusRowmapper());
            return getJdbcTemplate().query(sbQry.toString(), new Object[]{orderSummaryId}, new OrderEVStatusRowmapper());

            //return result;
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return null;

        }

    }

    @Override
    public List<OrderFinanceStatusDTO> fetchOrderFinanceStatus(Integer orderSummaryId) {

        try {

            List<OrderFinanceStatusDTO> result = new ArrayList<>();
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT DISTINCT fs.*,ofs.*,os.variant_name ,ofs.finance_status_id AS order_financeStatusId ");
            sbQry.append("FROM \"booma\".finance_status fs ");
            sbQry.append("left join booma.order_finance_status ofs ");
            sbQry.append("on fs.finance_status_id = ofs.finance_status_id and ofs.order_summary_id = ? ");
            sbQry.append("left join booma.order_summary os ");
            sbQry.append("on os.order_summary_id = ofs.order_summary_id ");
            sbQry.append("order by fs.finance_status_id");

            //return jdbcTemplate.queryForObject(sbQry.toString(), new Object[]{orderSummaryId}, new OrderEVStatusRowmapper());
            return getJdbcTemplate().query(sbQry.toString(), new Object[]{orderSummaryId}, new OrderFinanceStatusRowmapper());

            //return result;
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return null;

        }

    }

    @Override
    public List<OrderTransactionStatusDTO> fetchOrderTransactionStatus(Integer orderSummaryId) {

        try {

            List<OrderTransactionStatusDTO> result = new ArrayList<>();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT DISTINCT ts.*,ots.*,os.variant_name ,ots.transaction_status_id AS order_transactionStatusId ");
            sbQry.append("FROM \"booma\".transaction_status ts ");
            sbQry.append("left join booma.order_transaction_status ots ");
            sbQry.append("on ts.transaction_status_id = ots.transaction_status_id and ots.order_summary_id = ? ");
            sbQry.append("left join booma.order_summary os ");
            sbQry.append("on os.order_summary_id = ots.order_summary_id ");
            sbQry.append("order by ts.transaction_status_id");

            //return jdbcTemplate.queryForObject(sbQry.toString(), new Object[]{orderSummaryId}, new OrderEVStatusRowmapper());
            return getJdbcTemplate().query(sbQry.toString(), new Object[]{orderSummaryId}, new OrderTransactionStatusRowMapper());

            //return result;
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return null;

        }

    }

    @Override
    public Long createOrderEvStatus(OrderEVStatusDTO orderEVStatus) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".order_ev_status(order_summary_id, ev_status_id, created_by, created_date) ");
            sbQry.append("VALUES (?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(orderEVStatus.getOrderSummaryId())));
                preparedStatement.setInt(2, Integer.parseInt(orderEVStatus.getEvStatusId()));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(orderEVStatus.getCreatedBy())));
                preparedStatement.setTimestamp(4, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("order_ev_status_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create order EV status");
        }
    }

    @Override
    public Long createOrderFinanceStatus(OrderFinanceStatusDTO orderFinanceStatus) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".order_finance_status(order_summary_id, finance_status_id, created_by, created_date) ");
            sbQry.append("VALUES (?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(orderFinanceStatus.getOrderSummaryId())));
                preparedStatement.setInt(2, Integer.parseInt(orderFinanceStatus.getFinanceStatusId()));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(orderFinanceStatus.getCreatedBy())));
                preparedStatement.setTimestamp(4, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("order_finance_status_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create order Finance status");
        }
    }

    @Override
    public Long createOrderTransactionStatus(OrderTransactionStatusDTO orderTransactionStatus) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".order_transaction_status(order_summary_id, transaction_status_id, created_by, created_date) ");
            sbQry.append("VALUES (?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(orderTransactionStatus.getOrderSummaryId())));
                preparedStatement.setInt(2, Integer.parseInt(orderTransactionStatus.getTransactionStatusId()));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(orderTransactionStatus.getCreatedBy())));
                preparedStatement.setTimestamp(4, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("order_transaction_status_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to create order Transaction status");
        }
    }

    @Override
    public OrderSummaryDTO fetchOrderDetailsBySummaryId(Integer orderSummaryId) {
        try {

            OrderSummaryDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".order_summary ");
            sbQry.append("WHERE order_summary_id = ?");

            result = jdbcTemplate.queryForObject(sbQry.toString(), new Object[]{orderSummaryId}, new OrderSummaryRowMapper());

            return result;

        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    @Override
    public List<OrderPartsImgUrlDTO> getOrderPartsImageUrls(Integer summaryId) {
        try {
            List<OrderPartsImgUrlDTO> result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT os.order_summary_id, v.variant_id, v.base_image, opc.order_parts_colour_id, vpc.variant_parts_colour_id, vpc.image_url ");
            sbQry.append("FROM \"booma\".order_summary os ");
            sbQry.append("JOIN \"booma\".variant v ON v.variant_id = os.variant_id ");
            sbQry.append("JOIN \"booma\".order_parts_colour opc ON opc.order_summary_id = os.order_summary_id ");
            sbQry.append("JOIN \"booma\".variant_parts_colour vpc ON vpc.variant_parts_colour_id = opc.variant_parts_colour_id ");
            sbQry.append("WHERE opc.order_summary_id = ? ORDER BY opc.order_parts_colour_id");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{summaryId}, new OrderPartsImgUrlRowMapper());
            return result;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TransactionDetailsDTO> fetchPaymentDetails(Integer summaryId) {
        try {
            List<TransactionDetailsDTO> result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * from ( ");
            sbQry.append("SELECT ex_showroom AS amount, 'Ex-showroom Price' AS particulars, 0 AS payments, 1 AS seq FROM \"booma\".order_summary WHERE order_summary_id = ? ");
            sbQry.append("UNION ");
            sbQry.append("SELECT package_amt AS amount, 'Package Amount' AS particulars, 0 AS payments, 2 AS seq FROM \"booma\".order_summary WHERE order_summary_id = ? ");
            sbQry.append("UNION ");
            sbQry.append("SELECT colour_amount AS amount, CASE WHEN(colour_amount = 0) THEN 'Standard Colour' ELSE 'Custom Colour' END AS particulars, 0 AS payments, 3 AS seq FROM \"booma\".order_summary WHERE order_summary_id = ? ");
            sbQry.append("UNION ");
            sbQry.append("SELECT insurance_amount AS amount, CASE WHEN(insurance_amount = 0) THEN 'By Party Insurance' ELSE 'Comprehensive Insurance' END AS particulars, 0 AS payments, 4 AS seq FROM \"booma\".order_summary WHERE order_summary_id = ? ");
            sbQry.append("UNION ");
            sbQry.append("SELECT warranty_amount AS amount, 'Extended Warranty' AS particulars, 0 AS payments, 5 AS seq FROM \"booma\".order_summary WHERE order_summary_id = ? ");
            sbQry.append("UNION ");
            sbQry.append("SELECT gst_amt AS amount, 'GST' AS particulars, 0 AS payments, 6 AS seq FROM \"booma\".order_summary WHERE order_summary_id = ? ");
            sbQry.append("UNION ");
            sbQry.append("SELECT 0 AS amount, 'Subsidy Amount' AS particulars, subsidy_amt AS payments, 7 AS seq FROM \"booma\".order_summary WHERE order_summary_id = ? ");
            sbQry.append("UNION ");
            sbQry.append("SELECT 0 AS amount, payment_type AS particulars, amount AS payments, order_detail_id AS seq FROM \"booma\".order_detail WHERE order_summary_id = ? AND is_paid = true ");
            sbQry.append(") tdt ORDER BY seq ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{summaryId, summaryId, summaryId, summaryId, summaryId, summaryId, summaryId, summaryId}, new TransactionDetailsRowMapper());
            return result;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer fetchOrderEvStatusBySummaryId(Integer orderSummaryId) {
        try {
            Integer result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT count(*)");
            sbQry.append("FROM \"booma\".order_ev_status ");
            sbQry.append("WHERE order_summary_id = ? AND ev_status_id = 5 ");
            result = jdbcTemplate.queryForObject(sbQry.toString(), new Object[]{orderSummaryId}, Integer.class);

            return result;

        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    @Override
    public Boolean updateOrderSummary(OrderSummaryDTO data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".order_summary SET  ");
            sbQry.append("variant_id = ?,  ");
            sbQry.append("variant_name = ?,  ");
            sbQry.append("colour_name = ?,  ");
            sbQry.append("total_amount = ?,  ");
            sbQry.append("updated_by = ?,  ");
            sbQry.append("updated_date = ?, ");
            sbQry.append("ex_showroom = ?,  ");
            sbQry.append("sub_total = ?,  ");
            sbQry.append("gross_total = ?  ");
            sbQry.append("WHERE order_summary_id = ?");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getVariantId()))),
                        data.getVariantName(),
                        data.getColourName(),
                        data.getTotalAmount(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy()))),
                        dateUtil.getTimestamp(),
                        data.getExShowroom(),
                        data.getSubTotal(),
                        data.getGrossTotal(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getOrderSummaryId())))});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public OrderTransactionDTO fetchOrderTransaction(Integer orderSummaryId) {
        try {
            
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT OT.*, OS.quantity ");
            sbQry.append("FROM \"booma\".order_transactions OT ");
            sbQry.append("JOIN \"booma\".order_summary OS ON OT.order_summary_id = OS.order_summary_id ");
            sbQry.append("WHERE OT.order_summary_id = ? ");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{orderSummaryId}, new OrderTransactionsRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public Boolean updateOrderTransaction(OrderTransactionDTO data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".order_transactions SET  ");
            sbQry.append("refund_status = ?, ");
            sbQry.append("amount_refunded = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE order_summary_id = ?");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                       
                        data.getRefundStatus(),
                        data.getAmountRefunded(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy()))),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getOrderSummaryId())))});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Boolean updateConsumerOrderDetails(OrderSummaryDTO data) {

        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("UPDATE \"booma\".order_summary SET  ");
            sbQry.append("city = ?, ");
            sbQry.append("state = ?, ");
            sbQry.append("country = ?, ");
            sbQry.append("pincode = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE order_summary_id = ?");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{                      
                        data.getCity(),
                        data.getState(),
                        data.getCountry(),
                        data.getPincode(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getUpdatedBy()))),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(data.getOrderSummaryId())))});

            return isRecordUpdated > 0;

        } catch (AppException | NumberFormatException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<OrderDetailDTO> fetchFailedOrderDetails(Timestamp fromDate, Timestamp toDate) {
        try {
            List<OrderDetailDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".order_detail ");
            sbQry.append("WHERE is_paid = false AND created_date BETWEEN ? AND ? ");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{fromDate, toDate} , new OrderDetailRowMapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
