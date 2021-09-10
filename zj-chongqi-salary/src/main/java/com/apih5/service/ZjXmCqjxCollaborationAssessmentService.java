package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;

public interface ZjXmCqjxCollaborationAssessmentService {

    public ResponseEntity getZjXmCqjxCollaborationAssessmentListByCondition(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment);
    
    public ResponseEntity selectZjXmCqjxCollaborationTodoCount(String token);

    public ResponseEntity getZjXmCqjxCollaborationAssessmentDetails(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment);

    public ResponseEntity saveZjXmCqjxCollaborationAssessment(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment);

    public ResponseEntity updateZjXmCqjxCollaborationAssessment(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment);
    
    public ResponseEntity zjXmCqjxCollaborationAssessmentTimeOutTask(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment);
    
    public ResponseEntity zjXmCqjxCollaborationAssistantReleaseLock(ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember);

    public ResponseEntity batchDeleteUpdateZjXmCqjxCollaborationAssessment(List<ZjXmCqjxCollaborationAssessment> zjXmCqjxCollaborationAssessmentList);
    
    public ResponseEntity zjXmCqjxCollaborationAssessmentSendMessage(List<ZjXmCqjxCollaborationAssessment> zjXmCqjxCollaborationAssessmentList);

}

