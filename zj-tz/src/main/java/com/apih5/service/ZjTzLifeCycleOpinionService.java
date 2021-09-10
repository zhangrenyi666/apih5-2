package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzLifeCycleOpinion;

public interface ZjTzLifeCycleOpinionService {

    public ResponseEntity getZjTzLifeCycleOpinionListByCondition(ZjTzLifeCycleOpinion zjTzLifeCycleOpinion);

    public ResponseEntity getZjTzLifeCycleOpinionDetails(ZjTzLifeCycleOpinion zjTzLifeCycleOpinion);

    public ResponseEntity saveZjTzLifeCycleOpinion(ZjTzLifeCycleOpinion zjTzLifeCycleOpinion);

    public ResponseEntity updateZjTzLifeCycleOpinion(ZjTzLifeCycleOpinion zjTzLifeCycleOpinion);

    public ResponseEntity batchDeleteUpdateZjTzLifeCycleOpinion(List<ZjTzLifeCycleOpinion> zjTzLifeCycleOpinionList);

}

