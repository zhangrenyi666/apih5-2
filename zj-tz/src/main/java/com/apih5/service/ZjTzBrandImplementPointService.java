package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzBrandImplementPoint;

public interface ZjTzBrandImplementPointService {

    public ResponseEntity getZjTzBrandImplementPointListByCondition(ZjTzBrandImplementPoint zjTzBrandImplementPoint);

    public ResponseEntity getZjTzBrandImplementPointDetails(ZjTzBrandImplementPoint zjTzBrandImplementPoint);

    public ResponseEntity saveZjTzBrandImplementPoint(ZjTzBrandImplementPoint zjTzBrandImplementPoint);

    public ResponseEntity updateZjTzBrandImplementPoint(ZjTzBrandImplementPoint zjTzBrandImplementPoint);

    public ResponseEntity batchDeleteUpdateZjTzBrandImplementPoint(List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList);

	public ResponseEntity toHomeShowZjTzBrandImplementPoint(ZjTzBrandImplementPoint zjTzBrandImplementPoint);

	public ResponseEntity batchReleaseZjTzBrandImplementPoint(List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList);

	public ResponseEntity batchRecallZjTzBrandImplementPoint(List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList);

	public ResponseEntity batchExportZjTzBrandImplementPointFile(
			List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList);

}

