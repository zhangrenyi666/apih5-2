package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzCash;

public interface ZjTzCashService {

    public ResponseEntity getZjTzCashListByCondition(ZjTzCash zjTzCash);

    public ResponseEntity getZjTzCashDetails(ZjTzCash zjTzCash);

    public ResponseEntity saveZjTzCash(ZjTzCash zjTzCash);

    public ResponseEntity updateZjTzCash(ZjTzCash zjTzCash);

    public ResponseEntity batchDeleteUpdateZjTzCash(List<ZjTzCash> zjTzCashList);

}

