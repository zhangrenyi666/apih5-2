package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRiskListBase;

public interface ZjTzRiskListBaseService {

    public ResponseEntity getZjTzRiskListBaseListByCondition(ZjTzRiskListBase zjTzRiskListBase);

    public ResponseEntity getZjTzRiskListBaseDetails(ZjTzRiskListBase zjTzRiskListBase);

    public ResponseEntity saveZjTzRiskListBase(ZjTzRiskListBase zjTzRiskListBase);

    public ResponseEntity updateZjTzRiskListBase(ZjTzRiskListBase zjTzRiskListBase);

    public ResponseEntity batchDeleteUpdateZjTzRiskListBase(List<ZjTzRiskListBase> zjTzRiskListBaseList);

	public ResponseEntity batchLockUpdateZjTzRiskListBase(List<ZjTzRiskListBase> zjTzRiskListBaseList);

	public ResponseEntity batchClearUpdateZjTzRiskListBase(List<ZjTzRiskListBase> zjTzRiskListBaseList);

}

