package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzInvXmyyqk;

import cn.hutool.json.JSONObject;

public interface ZjTzInvXmyyqkService {

    public ResponseEntity getZjTzInvXmyyqkListByCondition(ZjTzInvXmyyqk zjTzInvXmyyqk);

    public ResponseEntity getZjTzInvXmyyqkDetails(ZjTzInvXmyyqk zjTzInvXmyyqk);

    public ResponseEntity saveZjTzInvXmyyqk(ZjTzInvXmyyqk zjTzInvXmyyqk);

    public ResponseEntity updateZjTzInvXmyyqk(ZjTzInvXmyyqk zjTzInvXmyyqk);

    public ResponseEntity batchDeleteUpdateZjTzInvXmyyqk(List<ZjTzInvXmyyqk> zjTzInvXmyyqkList);
    
    public ResponseEntity getZjTzInvXmyyqkMonthlyReportListBasicData(ZjTzInvXmyyqk zjTzInvXmyyqk);
    
    public ResponseEntity getZjTzInvXmyyqkMonthlyReportListBasicDataDetail(ZjTzInvXmyyqk zjTzInvXmyyqk);

	public ResponseEntity getHomeChartOperateData(ZjTzInvXmyyqk zjTzInvXmyyqk);

	public ResponseEntity getOperatePageCommentAndIncome(ZjTzInvXmyyqk zjTzInvXmyyqk);

	public ResponseEntity getOperatePageCurrentPeriod(ZjTzInvXmyyqk zjTzInvXmyyqk);
    

}

