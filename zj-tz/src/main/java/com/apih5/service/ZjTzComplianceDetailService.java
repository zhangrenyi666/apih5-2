package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzComplianceDetail;

public interface ZjTzComplianceDetailService {

    public ResponseEntity getZjTzComplianceDetailListByCondition(ZjTzComplianceDetail zjTzComplianceDetail);

    public ResponseEntity getZjTzComplianceDetailDetails(ZjTzComplianceDetail zjTzComplianceDetail);

    public ResponseEntity saveZjTzComplianceDetail(ZjTzComplianceDetail zjTzComplianceDetail);

    public ResponseEntity updateZjTzComplianceDetail(ZjTzComplianceDetail zjTzComplianceDetail);

    public ResponseEntity batchDeleteUpdateZjTzComplianceDetail(List<ZjTzComplianceDetail> zjTzComplianceDetailList);

	public List<ZjTzComplianceDetail> uReportZjTzComplianceDetailList(ZjTzComplianceDetail zjTzComplianceDetail);

	public ResponseEntity getZjTzComplianceDetailListForReport(ZjTzComplianceDetail zjTzComplianceDetail);

	public ResponseEntity exportZjTzComplianceDetailList(ZjTzComplianceDetail zjTzComplianceDetail, HttpServletResponse response);

}

