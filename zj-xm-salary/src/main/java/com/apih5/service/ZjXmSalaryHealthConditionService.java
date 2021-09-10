package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryHealthCondition;

public interface ZjXmSalaryHealthConditionService {

    public ResponseEntity getZjXmSalaryHealthConditionListByCondition(ZjXmSalaryHealthCondition zjXmSalaryHealthCondition);

    public ResponseEntity getZjXmSalaryHealthConditionDetails(ZjXmSalaryHealthCondition zjXmSalaryHealthCondition);

    public ResponseEntity saveZjXmSalaryHealthCondition(ZjXmSalaryHealthCondition zjXmSalaryHealthCondition);

    public ResponseEntity updateZjXmSalaryHealthCondition(ZjXmSalaryHealthCondition zjXmSalaryHealthCondition);

    public ResponseEntity batchDeleteUpdateZjXmSalaryHealthCondition(List<ZjXmSalaryHealthCondition> zjXmSalaryHealthConditionList);

}

