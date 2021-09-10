package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssessmentManage;

public interface ZjXmCqjxProjectAssessmentManageService {

    public ResponseEntity getZjXmCqjxProjectAssessmentManageListByCondition(ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage);

    public ResponseEntity getZjXmCqjxProjectAssessmentManageDetails(ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage);

    public ResponseEntity saveZjXmCqjxProjectAssessmentManage(ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage);

    public ResponseEntity updateZjXmCqjxProjectAssessmentManage(ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectAssessmentManage(List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxProjectAssessmentManageList);
    
    public ResponseEntity zjXmCqjxProjectAssessmentManageDetailByQuarter(ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage);
    
    public ResponseEntity zjXmCqjxProjectAssessmentManageSendMessage(ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage);
    
    public ResponseEntity getZjXmCqjxProjectAssessmentManageListByDeptHeader(ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage);
    
    public ResponseEntity batchZjXmCqjxProjectAssessmentManageSendMessage(List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxProjectAssessmentManage);
}

