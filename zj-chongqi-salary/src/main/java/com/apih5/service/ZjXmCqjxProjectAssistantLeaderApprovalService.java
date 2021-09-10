package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssistantLeaderApproval;

public interface ZjXmCqjxProjectAssistantLeaderApprovalService {

    public ResponseEntity getZjXmCqjxProjectAssistantLeaderApprovalListByCondition(ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval);

    public ResponseEntity getZjXmCqjxProjectAssistantLeaderApprovalDetails(ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval);

    public ResponseEntity saveZjXmCqjxProjectAssistantLeaderApproval(ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval);

    public ResponseEntity updateZjXmCqjxProjectAssistantLeaderApproval(ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectAssistantLeaderApproval(List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxProjectAssistantLeaderApprovalList);

}

