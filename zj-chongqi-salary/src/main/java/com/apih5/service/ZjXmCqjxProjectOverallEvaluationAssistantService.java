package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOverallEvaluationAssistant;

public interface ZjXmCqjxProjectOverallEvaluationAssistantService {

    public ResponseEntity getZjXmCqjxProjectOverallEvaluationAssistantListByCondition(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);
    
    public ResponseEntity getZjXmCqjxProjectOverallEvaluationAssistantDetails(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);

    public ResponseEntity saveZjXmCqjxProjectOverallEvaluationAssistant(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);

    public ResponseEntity updateZjXmCqjxProjectOverallEvaluationAssistant(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectOverallEvaluationAssistant(List<ZjXmCqjxProjectOverallEvaluationAssistant> zjXmCqjxProjectOverallEvaluationAssistantList);

    public ResponseEntity getZjXmCqjxProjectEvaluationAssistantTodoList(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);
    
    public ResponseEntity zjXmCqjxProjectEvaluationAssistantApproval(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);
    
    public ResponseEntity zjXmCqjxProjectOfficeAssistantApproval(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);
    
    public ResponseEntity getZjXmCqjxProjectOverallAssistantListByPrimaryKey(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);
    
    public ResponseEntity selectZjXmCqjxOverallEvaluationAssistantTodoCount(String token);

	public ResponseEntity getZjXmCqjxProjectEvaluationAssistantDoneList(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);
}

