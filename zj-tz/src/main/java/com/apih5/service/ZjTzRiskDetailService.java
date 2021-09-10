package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRiskDetail;

public interface ZjTzRiskDetailService {

    public ResponseEntity getZjTzRiskDetailListByCondition(ZjTzRiskDetail zjTzRiskDetail);

    public ResponseEntity getZjTzRiskDetailDetails(ZjTzRiskDetail zjTzRiskDetail);

    public ResponseEntity saveZjTzRiskDetail(ZjTzRiskDetail zjTzRiskDetail);

    public ResponseEntity updateZjTzRiskDetail(ZjTzRiskDetail zjTzRiskDetail);

    public ResponseEntity batchDeleteUpdateZjTzRiskDetail(List<ZjTzRiskDetail> zjTzRiskDetailList);

	public ResponseEntity batchLockUpdateZjTzRiskDetail(List<ZjTzRiskDetail> zjTzRiskDetailList);

	public ResponseEntity batchClearUpdateZjTzRiskDetail(List<ZjTzRiskDetail> zjTzRiskDetailList);

	public List<ZjTzRiskDetail> ureportZjTzRiskDetailList(ZjTzRiskDetail zjTzRiskDetail);

}

