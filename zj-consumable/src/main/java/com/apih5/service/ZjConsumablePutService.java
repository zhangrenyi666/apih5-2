package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjConsumablePut;

public interface ZjConsumablePutService {

    public ResponseEntity getZjConsumablePutListByCondition(ZjConsumablePut zjConsumablePut);

    public ResponseEntity getZjConsumablePutDetails(ZjConsumablePut zjConsumablePut);

    public ResponseEntity saveZjConsumablePut(ZjConsumablePut zjConsumablePut);

    public ResponseEntity updateZjConsumablePut(ZjConsumablePut zjConsumablePut);

    public ResponseEntity batchDeleteUpdateZjConsumablePut(List<ZjConsumablePut> zjConsumablePutList);

}

