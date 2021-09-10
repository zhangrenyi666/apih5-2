package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectEvaluationApproval;

public interface ZjXmCqjxProjectEvaluationApprovalService {

    public ResponseEntity getZjXmCqjxProjectEvaluationApprovalListByCondition(ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval);

    public ResponseEntity getZjXmCqjxProjectEvaluationApprovalDetails(ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval);

    public ResponseEntity saveZjXmCqjxProjectEvaluationApproval(ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval);

    public ResponseEntity updateZjXmCqjxProjectEvaluationApproval(ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectEvaluationApproval(List<ZjXmCqjxProjectEvaluationApproval> zjXmCqjxProjectEvaluationApprovalList);

}

