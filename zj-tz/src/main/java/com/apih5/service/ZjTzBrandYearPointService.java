package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzBrandYearPoint;

public interface ZjTzBrandYearPointService {

    public ResponseEntity getZjTzBrandYearPointListByCondition(ZjTzBrandYearPoint zjTzBrandYearPoint);

    public ResponseEntity getZjTzBrandYearPointDetails(ZjTzBrandYearPoint zjTzBrandYearPoint);

    public ResponseEntity saveZjTzBrandYearPoint(ZjTzBrandYearPoint zjTzBrandYearPoint);

    public ResponseEntity updateZjTzBrandYearPoint(ZjTzBrandYearPoint zjTzBrandYearPoint);

    public ResponseEntity batchDeleteUpdateZjTzBrandYearPoint(List<ZjTzBrandYearPoint> zjTzBrandYearPointList);

	public ResponseEntity toHomeShowZjTzBrandYearPoint(ZjTzBrandYearPoint zjTzBrandYearPoint);

	public ResponseEntity batchReleaseZjTzBrandYearPoint(List<ZjTzBrandYearPoint> zjTzBrandYearPointList);

	public ResponseEntity batchRecallZjTzBrandYearPoint(List<ZjTzBrandYearPoint> zjTzBrandYearPointList);

	public ResponseEntity batchExportZjTzBrandYearPointFile(List<ZjTzBrandYearPoint> zjTzBrandYearPointList);

	public ResponseEntity getZjTzBrandYearPointHome(ZjTzBrandYearPoint zjTzBrandYearPoint);

}

