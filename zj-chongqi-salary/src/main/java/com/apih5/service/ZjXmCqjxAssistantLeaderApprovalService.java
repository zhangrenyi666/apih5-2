package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;

public interface ZjXmCqjxAssistantLeaderApprovalService {

    public ResponseEntity getZjXmCqjxAssistantLeaderApprovalListByCondition(ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval);

    public ResponseEntity getZjXmCqjxAssistantLeaderApprovalDetails(ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval);

    public ResponseEntity saveZjXmCqjxAssistantLeaderApproval(ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval);

    public ResponseEntity updateZjXmCqjxAssistantLeaderApproval(ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval);

    public ResponseEntity batchDeleteUpdateZjXmCqjxAssistantLeaderApproval(List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList);

}

