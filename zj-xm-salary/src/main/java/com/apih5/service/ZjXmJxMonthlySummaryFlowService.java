package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxMonthlySummaryFlow;

public interface ZjXmJxMonthlySummaryFlowService {

    public ResponseEntity getZjXmJxMonthlySummaryFlowListByCondition(ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow);

    public ResponseEntity getZjXmJxMonthlySummaryFlowDetails(ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow);

    public ResponseEntity saveZjXmJxMonthlySummaryFlow(ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow);

    public ResponseEntity updateZjXmJxMonthlySummaryFlow(ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow);

    public ResponseEntity batchDeleteUpdateZjXmJxMonthlySummaryFlow(List<ZjXmJxMonthlySummaryFlow> zjXmJxMonthlySummaryFlowList);

}

