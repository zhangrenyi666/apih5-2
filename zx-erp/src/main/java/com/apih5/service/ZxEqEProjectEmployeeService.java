package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEProjectEmployee;

public interface ZxEqEProjectEmployeeService {

    public ResponseEntity getZxEqEProjectEmployeeListByCondition(ZxEqEProjectEmployee zxEqEProjectEmployee);

    public ResponseEntity getZxEqEProjectEmployeeDetails(ZxEqEProjectEmployee zxEqEProjectEmployee);

    public ResponseEntity saveZxEqEProjectEmployee(ZxEqEProjectEmployee zxEqEProjectEmployee);

    public ResponseEntity updateZxEqEProjectEmployee(ZxEqEProjectEmployee zxEqEProjectEmployee);

    public ResponseEntity batchDeleteUpdateZxEqEProjectEmployee(List<ZxEqEProjectEmployee> zxEqEProjectEmployeeList);

}

