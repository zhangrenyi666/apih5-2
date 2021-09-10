package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeeWork;

public interface ZjTzEmployeeWorkService {

    public ResponseEntity getZjTzEmployeeWorkListByCondition(ZjTzEmployeeWork zjTzEmployeeWork);

    public ResponseEntity getZjTzEmployeeWorkDetails(ZjTzEmployeeWork zjTzEmployeeWork);

    public ResponseEntity saveZjTzEmployeeWork(ZjTzEmployeeWork zjTzEmployeeWork);

    public ResponseEntity updateZjTzEmployeeWork(ZjTzEmployeeWork zjTzEmployeeWork);

    public ResponseEntity batchDeleteUpdateZjTzEmployeeWork(List<ZjTzEmployeeWork> zjTzEmployeeWorkList);

    public ZjTzEmployeeWork printZjTzEmployeeWork(ZjTzEmployeeWork zjTzEmployeeWork);

}

