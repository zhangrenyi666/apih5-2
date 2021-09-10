package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzInvXmzjqk;

public interface ZjTzInvXmzjqkService {

    public ResponseEntity getZjTzInvXmzjqkListByCondition(ZjTzInvXmzjqk zjTzInvXmzjqk);

    public ResponseEntity getZjTzInvXmzjqkDetails(ZjTzInvXmzjqk zjTzInvXmzjqk);

    public ResponseEntity saveZjTzInvXmzjqk(ZjTzInvXmzjqk zjTzInvXmzjqk);

    public ResponseEntity updateZjTzInvXmzjqk(ZjTzInvXmzjqk zjTzInvXmzjqk);

    public ResponseEntity batchDeleteUpdateZjTzInvXmzjqk(List<ZjTzInvXmzjqk> zjTzInvXmzjqkList);
    
    public ResponseEntity getZjTzInvXmzjqkMonthlyReportList(ZjTzInvXmzjqk zjTzInvXmzjqk);
    
    public ResponseEntity getZjTzInvXmzjqkMonthlyReportListBasicData(ZjTzInvXmzjqk zjTzInvXmzjqk);
    
    public List<ZjTzInvXmzjqk> exportZjTzInvXmzjqkMonthlyReportList(ZjTzInvXmzjqk zjTzInvXmzjqk);

	public ResponseEntity getHomeChartCapitalStatus(ZjTzInvXmzjqk zjTzInvXmzjqk);

	public ResponseEntity getConstructPageCapital(ZjTzInvXmzjqk zjTzInvXmzjqk);
}

