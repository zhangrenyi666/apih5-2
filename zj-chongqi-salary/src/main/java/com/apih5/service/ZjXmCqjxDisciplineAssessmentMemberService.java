package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentMember;

public interface ZjXmCqjxDisciplineAssessmentMemberService {

    public ResponseEntity getZjXmCqjxDisciplineAssessmentMemberListByCondition(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember);

    public ResponseEntity getZjXmCqjxDisciplineAssessmentMemberDetails(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember);

    public ResponseEntity saveZjXmCqjxDisciplineAssessmentMember(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember);

    public ResponseEntity updateZjXmCqjxDisciplineAssessmentMember(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember);

    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessmentMember(List<ZjXmCqjxDisciplineAssessmentMember> zjXmCqjxDisciplineAssessmentMemberList);
    
    public ResponseEntity batchAddZjXmCqjxDisciplineAssessmentMember(List<ZjXmCqjxDisciplineAssessmentMember> zjXmCqjxDisciplineAssessmentMemberList);
    
    public ResponseEntity zjXmCqjxDisciplineAssessmentSubmit(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember);

}

