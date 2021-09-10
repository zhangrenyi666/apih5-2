package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;

public interface ZjXmCqjxProjectExecutiveAssistantService {

    public ResponseEntity getZjXmCqjxProjectExecutiveAssistantListByCondition(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant);

    public ResponseEntity getZjXmCqjxProjectExecutiveAssistantDetails(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant);

    public ResponseEntity saveZjXmCqjxProjectExecutiveAssistant(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant);

    public ResponseEntity updateZjXmCqjxProjectExecutiveAssistant(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectExecutiveAssistant(List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxProjectExecutiveAssistantList);
	
    public ResponseEntity getZjXmCqjxExecutiveAssistantTodoCount(String token,String type,String leaderFlag,String state);

    public ResponseEntity zjXmCqjxExecutiveAssistantFillIn(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxExecutiveAssistantTodoList(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxAssistantListByDeptLeader(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxAssistantOaLeaderListByExecutiveId(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity checkZjXmCqjxAssistantScoreQualified(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxAssistantChargeLeaderApproval(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxAssistantExecutiveLeaderScore(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxAssistantChargeLeaderScore(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxAssistantExecutiveLeaderApproval(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
//    public ResponseEntity getZjXmCqjxAssistantTodoByAssistantType(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxExecutiveAssistantSpecialLaunch(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxExecutiveScoreLaunch(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity checkZjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity batchCheckZjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxExecutiveAssistantReleaseLock(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity checkZjXmCqjxAssessmentManageState(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxProjectAssistantMonthScoreTask(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxProjectExecutiveAssistantReleaseTempSave(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxProjectLeaderApprovalAllList(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity checkZjXmCqjxProjectFinishPlanAssistant(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxProjectComprehensiveRanking(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant);

	public ResponseEntity getZjXmCqjxProjectAssistantDoneList(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant);
    
}

