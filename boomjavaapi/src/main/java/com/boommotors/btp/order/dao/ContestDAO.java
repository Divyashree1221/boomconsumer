/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.order.dao;

import com.boommotors.btp.exception.EtAuthException;
import com.boommotors.btp.order.dto.ContestTemplatesDTO;
import com.boommotors.btp.order.dto.MytemplateLikesDTO;
import java.util.List;

/**
 *
 * @author NandiniC
 */
public interface ContestDAO {

    Long createContestTemplates(ContestTemplatesDTO contestTemplates) throws EtAuthException;

    Long createMytemplateLikes(MytemplateLikesDTO mytemplateLikes) throws EtAuthException;

    List<ContestTemplatesDTO> FetchTempleteForUser(String encryptedUserId) throws EtAuthException;

    ContestTemplatesDTO FetchTempleteForTheColourCombination(ContestTemplatesDTO contestTempletes) throws EtAuthException;

    Boolean updateContestTemplateLikeCount(ContestTemplatesDTO contestTempletesDTO);

    List<ContestTemplatesDTO> fetchAllContestTemplete(int limit, int offset, int userid);

    ContestTemplatesDTO FetchTempleteById(String contestTemplatesId) throws EtAuthException;

    ContestTemplatesDTO FetchTempleteByContestTemplateId(String contestTemplateId) throws EtAuthException;

    List<MytemplateLikesDTO> FetchTempleteByUserandContestTemplateId(String contestTemplateId, String encryptedUserId);

    List<MytemplateLikesDTO> FetchOtherTempleteByUserId(String encryptedUserId);

    Boolean existsByOrderSummaryId(Integer summaryId);

}
