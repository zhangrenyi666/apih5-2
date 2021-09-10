package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxYearScoreApproval;

public interface ZjXmCqjxYearScoreApprovalService {

    public ResponseEntity getZjXmCqjxYearScoreApprovalListByCondition(ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval);

    public ResponseEntity getZjXmCqjxYearScoreApprovalDetails(ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval);

    public ResponseEntity saveZjXmCqjxYearScoreApproval(ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval);
    
    public ResponseEntity updateZjXmCqjxYearScoreApproval(ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval);

    public ResponseEntity batchDeleteUpdateZjXmCqjxYearScoreApproval(List<ZjXmCqjxYearScoreApproval> zjXmCqjxYearScoreApprovalList);

}

