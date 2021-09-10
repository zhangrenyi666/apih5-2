package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzInvXmhgqk;

public interface ZjTzInvXmhgqkService {

    public ResponseEntity getZjTzInvXmhgqkListByCondition(ZjTzInvXmhgqk zjTzInvXmhgqk);

    public ResponseEntity getZjTzInvXmhgqkDetails(ZjTzInvXmhgqk zjTzInvXmhgqk);

    public ResponseEntity saveZjTzInvXmhgqk(ZjTzInvXmhgqk zjTzInvXmhgqk);

    public ResponseEntity updateZjTzInvXmhgqk(ZjTzInvXmhgqk zjTzInvXmhgqk);

    public ResponseEntity batchDeleteUpdateZjTzInvXmhgqk(List<ZjTzInvXmhgqk> zjTzInvXmhgqkList);
    
    public ResponseEntity getZjTzInvXmhgqkMonthlyReportList(ZjTzInvXmhgqk zjTzInvXmhgqk);
    
    public ResponseEntity getZjTzInvXmhgqkMonthlyReportListBasicData(ZjTzInvXmhgqk zjTzInvXmhgqk);

    public List<ZjTzInvXmhgqk> exportZjTzInvXmhgqkMonthlyReportList(ZjTzInvXmhgqk zjTzInvXmhgqk);

	public ResponseEntity getHomeChartHgData(ZjTzInvXmhgqk zjTzInvXmhgqk);

	public ResponseEntity getHgPageHgStatus(ZjTzInvXmhgqk zjTzInvXmhgqk);

	public ResponseEntity getHgPageCurrentPeriod(ZjTzInvXmhgqk zjTzInvXmhgqk);
}

