package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;

public interface ZjXmCqjxExecutiveAssistantService {


	public ResponseEntity getZjXmCqjxExecutiveAssistantListByCondition(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
	
	public ResponseEntity getZjXmCqjxExecutiveAssistantListByYear(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
	
    public ResponseEntity getZjXmCqjxExecutiveAssistantTodoCount(String token,String type,String leaderFlag,String state);
    
    public ResponseEntity getZjXmCqjxExecutiveAssistantHasTodoCount(String token,String type,String leaderFlag,String state);
    
    public ResponseEntity getZjXmCqjxYearLeaderTodoCount(String token,String type,String leaderFlag,String state);
    
    public ResponseEntity getZjXmCqjxYearLeaderHasTodoCount(String token);

    public ResponseEntity zjXmCqjxExecutiveAssistantFillIn(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxExecutiveAssistantTodoList(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxAssistantListByDeptLeader(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxAssistantOaLeaderListByExecutiveId(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity checkZjXmCqjxAssistantScoreQualified(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxAssistantChargeLeaderApproval(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxAssistantExecutiveLeaderScore(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxAssistantChargeLeaderScore(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxAssistantExecutiveLeaderApproval(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
//    public ResponseEntity getZjXmCqjxAssistantTodoByAssistantType(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity batchDeleteUpdateZjXmCqjxExecutiveAssistant(List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList);
    
    public ResponseEntity zjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxExecutiveScoreLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity checkZjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity batchCheckZjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxExecutiveAssistantReleaseLock(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity checkZjXmCqjxAssessmentManageState(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxExecutiveAssistantReleaseTempSave(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity zjXmCqjxStaffAssistantQuarterScoreTask(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity saveZjXmCqjxExecutiveAssistant(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);
    
    public ResponseEntity getZjXmCqjxExecutiveAssistantDetails(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);

    public ResponseEntity updateZjXmCqjxExecutiveAssistant(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);

	public ResponseEntity getZjXmCqjxAssistantDoneList(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant);

}

