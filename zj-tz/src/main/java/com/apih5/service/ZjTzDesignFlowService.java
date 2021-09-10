package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzDesignFlow;

public interface ZjTzDesignFlowService {

    public ResponseEntity getZjTzDesignFlowListByCondition(ZjTzDesignFlow zjTzDesignFlow);

    public ResponseEntity getZjTzDesignFlowDetails(ZjTzDesignFlow zjTzDesignFlow);

    public ResponseEntity saveZjTzDesignFlow(ZjTzDesignFlow zjTzDesignFlow);

    public ResponseEntity updateZjTzDesignFlow(ZjTzDesignFlow zjTzDesignFlow);

    public ResponseEntity batchDeleteUpdateZjTzDesignFlow(List<ZjTzDesignFlow> zjTzDesignFlowList);

    public ResponseEntity batchReleaseZjTzDesignFlow(List<ZjTzDesignFlow> zjTzDesignFlowList);
	
    public ResponseEntity batchRecallZjTzDesignFlow(List<ZjTzDesignFlow> zjTzDesignFlowList);
    
    public ResponseEntity saveZjTzPartManageAllList(ZjTzDesignFlow zjTzDesignFlow);

    public ResponseEntity checkAndFinishZjTzDesignFlow(ZjTzDesignFlow zjTzDesignFlow);
}