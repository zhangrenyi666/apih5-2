package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxAnnualSummaryFlow;

public interface ZjXmJxAnnualSummaryFlowService {

    public ResponseEntity getZjXmJxAnnualSummaryFlowListByCondition(ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow);

    public ResponseEntity getZjXmJxAnnualSummaryFlowDetails(ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow);

    public ResponseEntity saveZjXmJxAnnualSummaryFlow(ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow);

    public ResponseEntity updateZjXmJxAnnualSummaryFlow(ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow);

    public ResponseEntity batchDeleteUpdateZjXmJxAnnualSummaryFlow(List<ZjXmJxAnnualSummaryFlow> zjXmJxAnnualSummaryFlowList);

}

