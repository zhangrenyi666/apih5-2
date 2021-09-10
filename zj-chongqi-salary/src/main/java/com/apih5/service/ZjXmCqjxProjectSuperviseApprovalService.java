package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSuperviseApproval;

public interface ZjXmCqjxProjectSuperviseApprovalService {

    public ResponseEntity getZjXmCqjxProjectSuperviseApprovalListByCondition(ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval);

    public ResponseEntity getZjXmCqjxProjectSuperviseApprovalDetails(ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval);

    public ResponseEntity saveZjXmCqjxProjectSuperviseApproval(ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval);

    public ResponseEntity updateZjXmCqjxProjectSuperviseApproval(ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSuperviseApproval(List<ZjXmCqjxProjectSuperviseApproval> zjXmCqjxProjectSuperviseApprovalList);

}

