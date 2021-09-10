package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEemployee;

public interface ZxEqEemployeeService {

    public ResponseEntity getZxEqEemployeeListByCondition(ZxEqEemployee zxEqEemployee);

    public ResponseEntity getZxEqEemployeeDetails(ZxEqEemployee zxEqEemployee);

    public ResponseEntity saveZxEqEemployee(ZxEqEemployee zxEqEemployee);

    public ResponseEntity updateZxEqEemployee(ZxEqEemployee zxEqEemployee);

    public ResponseEntity batchDeleteUpdateZxEqEemployee(List<ZxEqEemployee> zxEqEemployeeList);

    public List<ZxEqEemployee> ureportZxEqEemployeeList(ZxEqEemployee zxEqEemployee);
    
    public ResponseEntity ureportZxEqEemployeeListIdle(ZxEqEemployee zxEqEemployee);
}

