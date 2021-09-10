package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzLifeCycle;

public interface ZjTzLifeCycleService {

    public ResponseEntity getZjTzLifeCycleListByCondition(ZjTzLifeCycle zjTzLifeCycle);

    public ResponseEntity getZjTzLifeCycleDetails(ZjTzLifeCycle zjTzLifeCycle);

    public ResponseEntity saveZjTzLifeCycle(ZjTzLifeCycle zjTzLifeCycle);

    public ResponseEntity updateZjTzLifeCycle(ZjTzLifeCycle zjTzLifeCycle);

    public ResponseEntity batchDeleteUpdateZjTzLifeCycle(List<ZjTzLifeCycle> zjTzLifeCycleList);

	public ResponseEntity updateZjTzLifeCycleFroFlow(ZjTzLifeCycle zjTzLifeCycle);

	public ResponseEntity zjTzLifeCycleMesRemind(ZjTzLifeCycle zjTzLifeCycle);
}

