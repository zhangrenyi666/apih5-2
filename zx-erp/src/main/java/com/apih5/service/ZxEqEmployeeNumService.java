package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEmployeeNum;

public interface ZxEqEmployeeNumService {

    public ResponseEntity getZxEqEmployeeNumListByCondition(ZxEqEmployeeNum zxEqEmployeeNum);

    public ResponseEntity getZxEqEmployeeNumDetails(ZxEqEmployeeNum zxEqEmployeeNum);

    public ResponseEntity saveZxEqEmployeeNum(ZxEqEmployeeNum zxEqEmployeeNum);

    public ResponseEntity updateZxEqEmployeeNum(ZxEqEmployeeNum zxEqEmployeeNum);

    public ResponseEntity batchDeleteUpdateZxEqEmployeeNum(List<ZxEqEmployeeNum> zxEqEmployeeNumList);

}

