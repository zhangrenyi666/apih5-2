package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessment;

public interface ZjXmCqjxDisciplineAssessmentService {

    public ResponseEntity getZjXmCqjxDisciplineAssessmentListByCondition(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);

    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetails(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);

    public ResponseEntity saveZjXmCqjxDisciplineAssessment(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentLaunch(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentDeptLaunch(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentToDoList(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);
    
    public ResponseEntity getZjXmCqjxDisciplineLeaderTodoCount(String token, String leaderFlag);
    
    public ResponseEntity getZjXmCqjxDisciplineLeaderHasTodoCount(String token);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentExecutiveApproval(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);
    
    public ResponseEntity updateZjXmCqjxDisciplineAssessment(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);

    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessment(List<ZjXmCqjxDisciplineAssessment> zjXmCqjxDisciplineAssessmentList);

	public ResponseEntity getZjXmCqjxDisciplineAssessmentDoneList(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);

}

