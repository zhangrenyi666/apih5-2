package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzProjectEmployee;

public interface ZjTzProjectEmployeeService {

    public ResponseEntity getZjTzProjectEmployeeListByCondition(ZjTzProjectEmployee zjTzProjectEmployee);

    public ResponseEntity getZjTzProjectEmployeeDetails(ZjTzProjectEmployee zjTzProjectEmployee);

    public ResponseEntity saveZjTzProjectEmployee(ZjTzProjectEmployee zjTzProjectEmployee);

    public ResponseEntity updateZjTzProjectEmployee(ZjTzProjectEmployee zjTzProjectEmployee);

    public ResponseEntity batchDeleteUpdateZjTzProjectEmployee(List<ZjTzProjectEmployee> zjTzProjectEmployeeList);

    public ResponseEntity checkZjTzProjectEmployee(List<ZjTzProjectEmployee> zjTzProjectEmployeeList);

    public ResponseEntity recallZjTzProjectEmployee(List<ZjTzProjectEmployee> zjTzProjectEmployeeList);

    public ResponseEntity recallAddZjTzProjectEmployee(ZjTzProjectEmployee zjTzProjectEmployee);



//    public ResponseEntity getZjTzProjectAllList(ZjTzProManage zjTzProManage);


}

