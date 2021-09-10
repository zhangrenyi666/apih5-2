package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRisk;

public interface ZjTzRiskService {

    public ResponseEntity getZjTzRiskListByCondition(ZjTzRisk zjTzRisk);

    public ResponseEntity getZjTzRiskDetails(ZjTzRisk zjTzRisk);

    public ResponseEntity saveZjTzRisk(ZjTzRisk zjTzRisk);

    public ResponseEntity updateZjTzRisk(ZjTzRisk zjTzRisk);

    public ResponseEntity batchDeleteUpdateZjTzRisk(List<ZjTzRisk> zjTzRiskList);

	public ResponseEntity saveZjTzRiskAllDetail(ZjTzRisk zjTzRisk);

	public ResponseEntity batchReleaseZjTzRisk(List<ZjTzRisk> zjTzRiskList);

	public ResponseEntity batchRecallZjTzRisk(List<ZjTzRisk> zjTzRiskList);

}

