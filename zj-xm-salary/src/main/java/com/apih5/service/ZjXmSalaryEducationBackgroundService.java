package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryEducationBackground;

public interface ZjXmSalaryEducationBackgroundService {

    public ResponseEntity getZjXmSalaryEducationBackgroundListByCondition(ZjXmSalaryEducationBackground zjXmSalaryEducationBackground);

    public ResponseEntity getZjXmSalaryEducationBackgroundDetails(ZjXmSalaryEducationBackground zjXmSalaryEducationBackground);

    public ResponseEntity saveZjXmSalaryEducationBackground(ZjXmSalaryEducationBackground zjXmSalaryEducationBackground);

    public ResponseEntity updateZjXmSalaryEducationBackground(ZjXmSalaryEducationBackground zjXmSalaryEducationBackground);

    public ResponseEntity batchDeleteUpdateZjXmSalaryEducationBackground(List<ZjXmSalaryEducationBackground> zjXmSalaryEducationBackgroundList);

}

