package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;

public interface ZjXmCqjxAssessmentManageService {

    public ResponseEntity getZjXmCqjxAssessmentManageListByCondition(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage);

    public ResponseEntity getZjXmCqjxAssessmentManageDetails(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage);

    public ResponseEntity saveZjXmCqjxAssessmentManage(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage);

    public ResponseEntity updateZjXmCqjxAssessmentManage(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage);
    
    public ResponseEntity zjXmCqjxAssessmentManageDetailByQuarter(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage);
    
    public ResponseEntity zjXmCqjxAssessmentManageSendMessage(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage);
    
    public ResponseEntity getZjXmCqjxAssessmentManageListByDeptHeader(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage);
    
    public ResponseEntity batchZjXmCqjxAssessmentManageSendMessage(List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList);

    public ResponseEntity batchDeleteUpdateZjXmCqjxAssessmentManage(List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList);

}

