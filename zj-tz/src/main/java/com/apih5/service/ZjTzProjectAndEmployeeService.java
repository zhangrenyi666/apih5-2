package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProjectAndEmployee;

public interface ZjTzProjectAndEmployeeService {

    public ResponseEntity getZjTzProjectAndEmployeeListByCondition(ZjTzProjectAndEmployee zjTzProjectAndEmployee);

    public ResponseEntity getZjTzProjectAndEmployeeDetails(ZjTzProjectAndEmployee zjTzProjectAndEmployee);

    public ResponseEntity saveZjTzProjectAndEmployee(ZjTzProjectAndEmployee zjTzProjectAndEmployee);

    public ResponseEntity updateZjTzProjectAndEmployee(ZjTzProjectAndEmployee zjTzProjectAndEmployee);

    public ResponseEntity batchDeleteUpdateZjTzProjectAndEmployee(List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList);


}

