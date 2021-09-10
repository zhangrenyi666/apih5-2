package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChange;

public interface ZjTzLifeCycleChangeService {

    public ResponseEntity getZjTzLifeCycleChangeListByCondition(ZjTzLifeCycleChange zjTzLifeCycleChange);

    public ResponseEntity getZjTzLifeCycleChangeDetails(ZjTzLifeCycleChange zjTzLifeCycleChange);

    public ResponseEntity saveZjTzLifeCycleChange(ZjTzLifeCycleChange zjTzLifeCycleChange);

    public ResponseEntity updateZjTzLifeCycleChange(ZjTzLifeCycleChange zjTzLifeCycleChange);

    public ResponseEntity batchDeleteUpdateZjTzLifeCycleChange(List<ZjTzLifeCycleChange> zjTzLifeCycleChangeList);

	public ResponseEntity updateZjTzLifeCycleChangeForFlow(ZjTzLifeCycleChange zjTzLifeCycleChange);

	public ResponseEntity getZjTzFlowTitle(ZjTzLifeCycleChange zjTzLifeCycleChange);

}

