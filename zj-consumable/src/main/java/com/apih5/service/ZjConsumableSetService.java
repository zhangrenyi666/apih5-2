package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjConsumableSet;

public interface ZjConsumableSetService {

    public ResponseEntity getZjConsumableSetListByCondition(ZjConsumableSet zjConsumableSet);

    public ResponseEntity getZjConsumableSetDetails(ZjConsumableSet zjConsumableSet);

    public ResponseEntity saveZjConsumableSet(ZjConsumableSet zjConsumableSet);

    public ResponseEntity updateZjConsumableSet(ZjConsumableSet zjConsumableSet);

    public ResponseEntity batchDeleteUpdateZjConsumableSet(List<ZjConsumableSet> zjConsumableSetList);

}

