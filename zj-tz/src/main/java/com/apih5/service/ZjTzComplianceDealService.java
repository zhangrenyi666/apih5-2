package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzComplianceDeal;

public interface ZjTzComplianceDealService {

    public ResponseEntity getZjTzComplianceDealListByCondition(ZjTzComplianceDeal zjTzComplianceDeal);

    public ResponseEntity getZjTzComplianceDealDetails(ZjTzComplianceDeal zjTzComplianceDeal);

    public ResponseEntity saveZjTzComplianceDeal(ZjTzComplianceDeal zjTzComplianceDeal);

    public ResponseEntity updateZjTzComplianceDeal(ZjTzComplianceDeal zjTzComplianceDeal);

    public ResponseEntity batchDeleteUpdateZjTzComplianceDeal(List<ZjTzComplianceDeal> zjTzComplianceDealList);

	public ResponseEntity saveZjTzComplianceDealAllDetail(ZjTzComplianceDeal zjTzComplianceDeal);

	public List<ZjTzComplianceDeal> uReportZjTzComplianceDealList(ZjTzComplianceDeal zjTzComplianceDeal);

}

