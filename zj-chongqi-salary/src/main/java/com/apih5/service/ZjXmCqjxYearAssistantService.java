package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxYearAssistant;

public interface ZjXmCqjxYearAssistantService {

    public ResponseEntity getZjXmCqjxYearAssistantListByCondition(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);
    
    public ResponseEntity getZjXmCqjxYearAssistantListByDeptLeader(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);
    
    public ResponseEntity getZjXmCqjxYearAssistantScoreDetail(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);

    public ResponseEntity getZjXmCqjxYearAssistantDetails(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);

    public ResponseEntity saveZjXmCqjxYearAssistant(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);
    
    public ResponseEntity saveZjXmCqjxYearAssistantByManagerId(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage);

    public ResponseEntity updateZjXmCqjxYearAssistant(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);
    
    public ResponseEntity zjXmCqjxSurroundAssistantApproval(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);
    
    public ResponseEntity jobZjXmCqjxYearAssistantLastScore(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);
    
    public ResponseEntity getZjXmCqjxYearAssistantTodoList(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);
    
    public ResponseEntity batchDeleteUpdateZjXmCqjxYearAssistant(List<ZjXmCqjxYearAssistant> zjXmCqjxYearAssistantList);

	public ResponseEntity getZjXmCqjxYearAssistantDoneList(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);

}

