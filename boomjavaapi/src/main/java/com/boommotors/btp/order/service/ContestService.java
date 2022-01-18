/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.service;

import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.payload.MyTemplateLikesRequest;
import com.boommotors.btp.order.dto.ContestTemplatesDTO;
import com.boommotors.btp.order.dto.MytemplateLikesDTO;
import com.boommotors.btp.payload.OrderRequest;
import com.boommotors.btp.payload.PagedResponse;
import java.util.List;

/**
 *
 * @author NandiniC
 */
public interface ContestService {

    String createContestTemplate(OrderRequest orderRequest) throws EtAuthException;

    List<ContestTemplatesDTO> retreiveTempleteForUser(String encryptedUserId) throws EtAuthException;

    ContestTemplatesDTO retreiveTempleteForTheColourCombination(OrderRequest orderRequest) throws EtAuthException;

    String createmyTemplateLikes(MyTemplateLikesRequest myTemplateLikesRequest) throws EtAuthException;

    PagedResponse<ContestTemplatesDTO> fetchAllContestTemplete(int page, String user_id);

    ContestTemplatesDTO retrieveContestTemplatesById(String contestTemplatesId);

    List<MytemplateLikesDTO> retrieveOtherTempleteByUserId(String encryptedUserId);

}
