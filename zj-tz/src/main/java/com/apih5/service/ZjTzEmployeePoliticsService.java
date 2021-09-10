package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeePolitics;

public interface ZjTzEmployeePoliticsService {

    public ResponseEntity getZjTzEmployeePoliticsListByCondition(ZjTzEmployeePolitics zjTzEmployeePolitics);

    public ResponseEntity getZjTzEmployeePoliticsDetails(ZjTzEmployeePolitics zjTzEmployeePolitics);

    public ResponseEntity saveZjTzEmployeePolitics(ZjTzEmployeePolitics zjTzEmployeePolitics);

    public ResponseEntity updateZjTzEmployeePolitics(ZjTzEmployeePolitics zjTzEmployeePolitics);

    public ResponseEntity batchDeleteUpdateZjTzEmployeePolitics(List<ZjTzEmployeePolitics> zjTzEmployeePoliticsList);

    public ZjTzEmployeePolitics printZjTzEmployeePolitics(ZjTzEmployeePolitics zjTzEmployeePolitics);
}

