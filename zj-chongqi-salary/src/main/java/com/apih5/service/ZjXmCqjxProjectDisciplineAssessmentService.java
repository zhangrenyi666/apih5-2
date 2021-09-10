package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessment;

public interface ZjXmCqjxProjectDisciplineAssessmentService {

    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentListByCondition(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);

    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentDetails(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);

    public ResponseEntity saveZjXmCqjxProjectDisciplineAssessment(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);

    public ResponseEntity updateZjXmCqjxProjectDisciplineAssessment(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssessment(List<ZjXmCqjxProjectDisciplineAssessment> zjXmCqjxProjectDisciplineAssessmentList);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentLaunch(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentDeptLaunch(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentToDoList(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);
    
    public ResponseEntity getZjXmCqjxDisciplineLeaderTodoCount(String token, String leaderFlag);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentExecutiveApproval(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);

	public ResponseEntity zjXmCqjxProjectDisciplineAssessmentDoneList(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);

}

