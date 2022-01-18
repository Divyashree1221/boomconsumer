/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dao;

import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.ContestTemplatesDTO;
import com.boommotors.btp.order.dto.MytemplateLikesDTO;
import com.boommotors.btp.order.rowmapper.ContestTemplatesRowmapper;
import com.boommotors.btp.order.rowmapper.MytemplateLikesRowmapper;
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
 * @author NandiniC
 */
@Repository
public class ContestDAOImpl extends JdbcDaoSupport implements ContestDAO {

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
    public Long createContestTemplates(ContestTemplatesDTO contestTemplates) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".contest_templates( user_id, template_name, user_nickname, variant_id, base_image, body_color_id, body_image, frame_color_id, ");
            sbQry.append("frame_image, mask_color_id, mask_image, rims_color_id, rims_image, shock_color_id, shock_image, ");
            sbQry.append("likes_count, template_image, created_by, created_date, order_summary_id) ");
            sbQry.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getUserId()))));
                preparedStatement.setString(2, contestTemplates.getTemplateName());
                preparedStatement.setString(3, contestTemplates.getUserNickname());
                preparedStatement.setInt(4, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getVariantId()))));
                preparedStatement.setString(5, contestTemplates.getBaseImage());
                preparedStatement.setInt(6, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getBodyColorId()))));
                preparedStatement.setString(7, contestTemplates.getBodyImage());
                preparedStatement.setInt(8, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getFrameColorId()))));
                preparedStatement.setString(9, contestTemplates.getFrameImage());
                preparedStatement.setInt(10, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getMaskColorId()))));
                preparedStatement.setString(11, contestTemplates.getMaskImage());
                preparedStatement.setInt(12, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getRimsColorId()))));
                preparedStatement.setString(13, contestTemplates.getRimsImage());
                preparedStatement.setInt(14, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getShockColorId()))));
                preparedStatement.setString(15, contestTemplates.getShockImage());
                preparedStatement.setInt(16, contestTemplates.getLikesCount());
                preparedStatement.setString(17, contestTemplates.getTemplateImage());
                preparedStatement.setInt(18, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getUserId()))));
                preparedStatement.setTimestamp(19, dateUtil.getTimestamp());
                preparedStatement.setInt(20, Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplates.getOrderSummaryId()))));

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("contest_templates_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to Contest Templates");
        }
    }

    @Override
    public Long createMytemplateLikes(MytemplateLikesDTO mytemplateLikes) throws EtAuthException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("INSERT INTO \"booma\".my_template_likes( contest_templates_id, user_id, created_by, created_date)");
            sbQry.append("VALUES ( ?, ?, ?, ?) ");

            getJdbcTemplate().update((Connection connection) -> {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(edUtil.decrypt(String.valueOf(mytemplateLikes.getContestTemplatesId()))));
                preparedStatement.setInt(2, Integer.parseInt(edUtil.decrypt(String.valueOf(mytemplateLikes.getUserId()))));
                preparedStatement.setInt(3, Integer.parseInt(edUtil.decrypt(String.valueOf(mytemplateLikes.getUserId()))));
                preparedStatement.setTimestamp(4, dateUtil.getTimestamp());

                return preparedStatement;

            }, keyHolder);

            long pkId = 0;

            if (keyHolder.getKeys().size() > 1) {

                pkId = Long.parseLong(keyHolder.getKeys().get("my_template_likes_id").toString());
            } else {
                pkId = keyHolder.getKey().longValue();
            }

            return pkId;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new EtAuthException("Invalid details. failed to my_template_likes");
        }

    }

    @Override
    public List<ContestTemplatesDTO> FetchTempleteForUser(String encryptedUserId) throws EtAuthException {
        try {
            List<ContestTemplatesDTO> result;
            long user_id = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedUserId)));

            System.out.println("1 : " + edUtil.encrypt(String.valueOf(1)));
            System.out.println("52 : " + edUtil.encrypt(String.valueOf(52)));

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".contest_templates ");
            sbQry.append("WHERE user_id = ?");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{user_id}, new ContestTemplatesRowmapper());
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
    public ContestTemplatesDTO FetchTempleteForTheColourCombination(ContestTemplatesDTO contestTempletes) throws EtAuthException {
        try {
            ContestTemplatesDTO result;

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".contest_templates ");
            sbQry.append("WHERE body_color_id = ? and frame_color_id = ? and mask_color_id = ? and rims_color_id = ? and shock_color_id = ? ");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new ContestTemplatesRowmapper(),
                    new Object[]{Integer.parseInt(edUtil.decrypt(String.valueOf(contestTempletes.getBodyColorId()))),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(contestTempletes.getFrameColorId()))),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(contestTempletes.getMaskColorId()))),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(contestTempletes.getRimsColorId()))),
                        Integer.parseInt(edUtil.decrypt(String.valueOf(contestTempletes.getShockColorId())))});
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
    public Boolean updateContestTemplateLikeCount(ContestTemplatesDTO contestTempletesDTO) {
        try {
            System.out.println("Count : " + contestTempletesDTO.getLikesCount());
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("UPDATE \"booma\".contest_templates SET  ");
            sbQry.append("likes_count = ?, ");
            sbQry.append("updated_by = ?, ");
            sbQry.append("updated_date = ? ");
            sbQry.append("WHERE ");
            sbQry.append("contest_templates_id = ?  ");

            int isRecordUpdated = getJdbcTemplate().update(sbQry.toString(),
                    new Object[]{
                        contestTempletesDTO.getLikesCount(),
                        Integer.parseInt(edUtil.decrypt(contestTempletesDTO.getUpdatedBy())),
                        dateUtil.getTimestamp(),
                        Integer.parseInt(edUtil.decrypt(contestTempletesDTO.getContestTemplatesId())),}
            );

            return isRecordUpdated > 0;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Responding with error. Message - {}");
            return null;

        }
    }

    @Override
    public List<ContestTemplatesDTO> fetchAllContestTemplete(int limit, int offset, int userid) {
        try {
            List<ContestTemplatesDTO> result = new ArrayList<>();

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT BASEINFO.*, COUNT(1) OVER() total_records FROM (");
            sbQry.append("SELECT * FROM \"booma\".contest_templates WHERE user_id <> ?");
//            sbQry.append("else 0 END as userLikecliked from booma.contest_templates as ct ");
//            sbQry.append("JOIN  booma.my_template_likes mtl on mtl.contest_templates_id = ct.contest_templates_id ");
//            sbQry.append(" WHERE ct.user_id <> ? order by ct.contest_templates_id  ");
            sbQry.append(") BASEINFO ");
            sbQry.append("ORDER BY BASEINFO.likes_count DESC ");

            if (limit > 0) {
                sbQry.append("LIMIT ");
                sbQry.append(limit).append(" ");
                if (offset > 0) {
                    sbQry.append("OFFSET ").append(offset);
                }
            }
            System.out.println("Query:: " + sbQry.toString());
            return result = getJdbcTemplate().query(sbQry.toString(), new Object[]{userid}, new ContestTemplatesRowmapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public ContestTemplatesDTO FetchTempleteById(String contestTemplatesId) throws EtAuthException {
        try {
            ContestTemplatesDTO result;
            long user_id = Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplatesId)));

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".contest_templates ");
            sbQry.append("WHERE contest_templates_id = ?");

            result = getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{user_id}, new ContestTemplatesRowmapper());
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
    public ContestTemplatesDTO FetchTempleteByContestTemplateId(String contestTemplateId) throws EtAuthException {
        try {
            List<ContestTemplatesDTO> result;
            long user_id = Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplateId)));

            System.out.println("1 : " + edUtil.encrypt(String.valueOf(1)));
            System.out.println("52 : " + edUtil.encrypt(String.valueOf(52)));

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".contest_templates ");
            sbQry.append("WHERE contest_templates_id = ?");

            return getJdbcTemplate().queryForObject(sbQry.toString(), new Object[]{user_id}, new ContestTemplatesRowmapper());
//            System.out.println("RESULT :: " + result);

//            if (result != null) {
//
//                return result;
//            } else {
//                return null;
//            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MytemplateLikesDTO> FetchTempleteByUserandContestTemplateId(String contestTemplateId, String encryptedUserId) throws EtAuthException {
        try {
            List<MytemplateLikesDTO> result;
            long user_id = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedUserId)));
            long contest_templates_id = Integer.parseInt(edUtil.decrypt(String.valueOf(contestTemplateId)));

            System.out.println("user_id : " + user_id);
            System.out.println("contest_templates_id : " + contest_templates_id);

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT * ");
            sbQry.append("FROM \"booma\".my_template_likes ");
            sbQry.append("WHERE contest_templates_id = ? AND user_id = ?");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{contest_templates_id, user_id}, new MytemplateLikesRowmapper());
//            System.out.println("RESULT :: " + result);
            return result;

//            if (result != null) {
//
//                return result;
//            } else {
//                return null;
//            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MytemplateLikesDTO> FetchOtherTempleteByUserId(String encryptedUserId) throws EtAuthException {
        try {
            List<MytemplateLikesDTO> result;
            long user_id = Integer.parseInt(edUtil.decrypt(String.valueOf(encryptedUserId)));

            System.out.println("user_id : " + user_id);

            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT DISTINCT * FROM \"booma\".my_template_likes where user_id = ? and contest_templates_id IN ");
            sbQry.append("(SELECT contest_templates_id FROM \"booma\".my_template_likes WHERE user_id = ? and contest_templates_id NOT IN ");
            sbQry.append("(SELECT contest_templates_id FROM \"booma\".contest_templates WHERE user_id = ? ))");

            result = getJdbcTemplate().query(sbQry.toString(), new Object[]{user_id, user_id, user_id}, new MytemplateLikesRowmapper());
            return result;

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean existsByOrderSummaryId(Integer summaryId) {
        try {
            String GET_RECORD_COUNT_SQL = "SELECT COUNT(*) FROM \"booma\".contest_templates WHERE order_summary_id = ?";

            int count = getJdbcTemplate().queryForObject(GET_RECORD_COUNT_SQL, new Object[]{summaryId}, Integer.class);

            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
