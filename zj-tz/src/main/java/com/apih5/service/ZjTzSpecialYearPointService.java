package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSpecialYearPoint;

public interface ZjTzSpecialYearPointService {

    public ResponseEntity getZjTzSpecialYearPointListByCondition(ZjTzSpecialYearPoint zjTzSpecialYearPoint);

    public ResponseEntity getZjTzSpecialYearPointDetails(ZjTzSpecialYearPoint zjTzSpecialYearPoint);

    public ResponseEntity saveZjTzSpecialYearPoint(ZjTzSpecialYearPoint zjTzSpecialYearPoint);

    public ResponseEntity updateZjTzSpecialYearPoint(ZjTzSpecialYearPoint zjTzSpecialYearPoint);

    public ResponseEntity batchDeleteUpdateZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList);

	public ResponseEntity toHomeShowZjTzSpecialYearPoint(ZjTzSpecialYearPoint zjTzSpecialYearPoint);

	public ResponseEntity batchReleaseZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList);

	public ResponseEntity batchRecallZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList);

	public ResponseEntity batchExportZjTzSpecialYearPointFile(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList);

}

