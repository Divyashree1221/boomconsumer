/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.service;

import com.boommotors.btp.exception.AppException;
import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.payload.MyTemplateLikesRequest;
import com.boommotors.btp.order.dao.ContestDAO;
import com.boommotors.btp.order.dao.OrderDAO;
import com.boommotors.btp.order.dto.ContestTemplatesDTO;
import com.boommotors.btp.order.dto.MytemplateLikesDTO;
import com.boommotors.btp.order.dto.OrderPartsImgUrlDTO;
import com.boommotors.btp.order.dto.OrderSummaryDTO;
import com.boommotors.btp.payload.OrderRequest;
import com.boommotors.btp.payload.PagedResponse;
import com.boommotors.btp.product.dto.VariantsDTO;
import com.boommotors.btp.product.service.ProductService;
import com.boommotors.btp.util.AppConstants;
import com.boommotors.btp.util.DateUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author NandiniC
 */
@Service
@Transactional
public class ContestServiceImpl implements ContestService {

    @Autowired
    ContestDAO contestDAO;

    @Autowired
    OrderDAO orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    EncryptDecrypt edUtil;

    @Autowired
    DateUtil dateUtil;

    @Override
    public String createContestTemplate(OrderRequest orderRequest) throws EtAuthException {

        try {
            Long newContestTemplatesID;

            ContestTemplatesDTO contestTemplates = new ContestTemplatesDTO();
            contestTemplates.setOrderSummaryId(orderRequest.getOrderSummaryId());
            contestTemplates.setTemplateName(orderRequest.getTemplateName());
            contestTemplates.setUserNickname(orderRequest.getUserNickname());
            contestTemplates.setLikesCount(1);
            contestTemplates.setTemplateImage(null);

            //Request from order flow to create contest template on sucessful payment
            if (orderRequest.getVariantId() != null && !"".equals(orderRequest.getVariantId())) {
                contestTemplates.setUserId(orderRequest.getUserId());
                contestTemplates.setVariantId(orderRequest.getVariantId());
                contestTemplates.setBaseImage(orderRequest.getBaseImage());
                contestTemplates.setFrameColorId(orderRequest.getChasisColourID());
                contestTemplates.setFrameImage(orderRequest.getFrameImage());
                contestTemplates.setBodyColorId(orderRequest.getBodyMudGuardColourID());
                contestTemplates.setBodyImage(orderRequest.getBodyImage());
                contestTemplates.setMaskColorId(orderRequest.getMaskHandleBarColourID());
                contestTemplates.setMaskImage(orderRequest.getMaskImage());
                contestTemplates.setShockColorId(orderRequest.getShocksColourID());
                contestTemplates.setShockImage(orderRequest.getShockImage());
                contestTemplates.setRimsColorId(orderRequest.getWheelRimsColourID());
                contestTemplates.setRimsImage(orderRequest.getRimsImage());

            } else {

                //Request from dashboard to create contest template.
                Integer summaryId = Integer.parseInt(edUtil.decrypt(orderRequest.getOrderSummaryId()));

                OrderSummaryDTO orderSummaryRes = orderRepository.fetchOrderDetailsBySummaryId(summaryId);
                if (orderSummaryRes != null) {
                    contestTemplates.setVariantId(orderSummaryRes.getVariantId());
                    contestTemplates.setUserId(orderSummaryRes.getUserId());

                    if (orderSummaryRes.getColourName() == null || "".equals(orderSummaryRes.getColourName())) {

                        List<OrderPartsImgUrlDTO> imageData = orderRepository.getOrderPartsImageUrls(summaryId);
                        if (!imageData.isEmpty()) {
                            contestTemplates.setBaseImage(imageData.get(0).getBaseImage());
                            contestTemplates.setFrameColorId(imageData.get(0).getVariantPartsColourId());
                            contestTemplates.setFrameImage(imageData.get(0).getImageUrl());
                            contestTemplates.setBodyColorId(imageData.get(1).getVariantPartsColourId());
                            contestTemplates.setBodyImage(imageData.get(1).getImageUrl());
                            contestTemplates.setMaskColorId(imageData.get(2).getVariantPartsColourId());
                            contestTemplates.setMaskImage(imageData.get(2).getImageUrl());
                            contestTemplates.setShockColorId(imageData.get(3).getVariantPartsColourId());
                            contestTemplates.setShockImage(imageData.get(3).getImageUrl());
                            contestTemplates.setRimsColorId(imageData.get(4).getVariantPartsColourId());
                            contestTemplates.setRimsImage(imageData.get(4).getImageUrl());
                        }

                    }
                }
            }
            // Inserting order summary data
            newContestTemplatesID = contestDAO.createContestTemplates(contestTemplates);
            System.out.println("newContestTemplatesID " + newContestTemplatesID);

            //if user has choosen for custom colours insert into order Parts Colour table
            if (newContestTemplatesID > 0) {

                MytemplateLikesDTO mytemplateLikes = new MytemplateLikesDTO();

                mytemplateLikes.setUserId(contestTemplates.getUserId());
                mytemplateLikes.setContestTemplatesId(edUtil.encrypt(String.valueOf(newContestTemplatesID)));
                mytemplateLikes.setCreatedBy(contestTemplates.getUserId());
                // Inserting My template Likes data
                Long res = contestDAO.createMytemplateLikes(mytemplateLikes);
                System.out.println("createMytemplateLikes " + res);

            }

            return edUtil.encrypt(String.valueOf(newContestTemplatesID));

        } catch (AppException | EtAuthException | NumberFormatException ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }

    }

    @Override
    public List<ContestTemplatesDTO> retreiveTempleteForUser(String encryptedUserId) throws EtAuthException {

        List<ContestTemplatesDTO> res = null;

        try {
            res = contestDAO.FetchTempleteForUser(encryptedUserId);

            if (res != null) {

                return res;
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public ContestTemplatesDTO retreiveTempleteForTheColourCombination(OrderRequest orderRequest) throws EtAuthException {

        ContestTemplatesDTO result = null;

        try {

            ContestTemplatesDTO contestTempletes = new ContestTemplatesDTO();

            contestTempletes.setBodyColorId(orderRequest.getBodyMudGuardColourID());
            contestTempletes.setFrameColorId(orderRequest.getChasisColourID());
            contestTempletes.setMaskColorId(orderRequest.getMaskHandleBarColourID());
            contestTempletes.setRimsColorId(orderRequest.getWheelRimsColourID());
            contestTempletes.setShockColorId(orderRequest.getShocksColourID());

            result = contestDAO.FetchTempleteForTheColourCombination(contestTempletes);

            if (result != null) {

                return result;
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public String createmyTemplateLikes(MyTemplateLikesRequest myTemplateLikesRequest) throws EtAuthException {
        try {
            List<MytemplateLikesDTO> UserTempleteData = contestDAO.FetchTempleteByUserandContestTemplateId(myTemplateLikesRequest.getContestTemplatesId(), myTemplateLikesRequest.getLoggedInUserId());
            if (UserTempleteData.size() == 0) {
                MytemplateLikesDTO data = new MytemplateLikesDTO();

                data.setUserId(myTemplateLikesRequest.getLoggedInUserId());
                data.setContestTemplatesId(myTemplateLikesRequest.getContestTemplatesId());
                data.setCreatedBy(myTemplateLikesRequest.getLoggedInUserId());
                // Inserting My template Likes data
                Long myTemplatelikesId = contestDAO.createMytemplateLikes(data);

                if (myTemplatelikesId > 0) {
                    ContestTemplatesDTO templateData = contestDAO.FetchTempleteByContestTemplateId(myTemplateLikesRequest.getContestTemplatesId());
                    // ContestTemplatesDTO templateData = retreiveTempleteForUser(myTemplateLikesRequest.getUserId());
                    Integer likeCount = templateData.getLikesCount();
                    likeCount++;
                    System.out.println("Like count : " + likeCount);
                    ContestTemplatesDTO contestTemplateData = new ContestTemplatesDTO();
                    contestTemplateData.setLikesCount(likeCount);
                    contestTemplateData.setUpdatedBy(myTemplateLikesRequest.getUserId());
                    contestTemplateData.setContestTemplatesId(myTemplateLikesRequest.getContestTemplatesId());
                    Boolean updateLikeCount = contestDAO.updateContestTemplateLikeCount(contestTemplateData);

//                ContestTemplatesDTO templateData =  new ContestTemplatesDTO();
//                templateData.setContestTemplatesId(myTemplateLikesRequest.getContestTemplatesId());
//                templateData.setUpdatedBy(myTemplateLikesRequest.getUserId());
//                templateData.setLikesCount(myTemplateLikesRequest.getLikeCount());
//
//                Boolean updateLikeCount = contestDAO.updateContestTemplateLikeCount(templateData);
                    System.out.println("Update Status : " + updateLikeCount);
                }

                String encryptedId = edUtil.encrypt(String.valueOf(myTemplatelikesId));
                return encryptedId;
            } else {
                return "";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public PagedResponse<ContestTemplatesDTO> fetchAllContestTemplete(int page, String encryptedUser_id) {
        int size = Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE);
        int currentPage = page;
        if (page == 1) {
        } else {
            page = (page - 1) * size;
        }
        int limit = size;
        int offset = (page == 1) ? 0 : page;

        int userID = Integer.parseInt(edUtil.decrypt(encryptedUser_id));
        //  if (limit != offset || page == 1) {
        List<ContestTemplatesDTO> bld = contestDAO.fetchAllContestTemplete(limit, offset, userID);
        if (bld.size() > 0) {
            int totalElements = bld.get(0).getTotalRecords();//exicomBatteryRepository.fetchExicomBateryDataLiveCountByType(batteryId);
            // int totalPages = Math.round((totalElements / size));
            double totalPages = Math.ceil(((float) totalElements / (float) size));
            System.out.println("totalPages1" + (int) totalPages);
            PagedResponse<ContestTemplatesDTO> pagedResponse = new PagedResponse<>();
            pagedResponse.setContent(bld);
            pagedResponse.setPage(currentPage);
            pagedResponse.setSize(bld.size());
            pagedResponse.setTotalElements(totalElements);
            pagedResponse.setTotalPages((int) totalPages);
            pagedResponse.setNext(currentPage + 1);

            return pagedResponse;
        } else {
            return null;
        }
//        } else {
//            return null;
//        }
    }

    @Override
    public ContestTemplatesDTO retrieveContestTemplatesById(String contestTemplatesId) {
        ContestTemplatesDTO res = null;

        try {
            res = contestDAO.FetchTempleteById(contestTemplatesId);

            if (res != null) {

                return res;
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

    @Override
    public List<MytemplateLikesDTO> retrieveOtherTempleteByUserId(String encryptedUserId) {
        List<MytemplateLikesDTO> res = null;

        try {
            res = contestDAO.FetchOtherTempleteByUserId(encryptedUserId);

            if (res != null) {

                return res;
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("Exception occured while processing the request!", ex);
        }
    }

}
