package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzContractCondition;

public interface ZjTzContractConditionService {

    public ResponseEntity getZjTzContractConditionListByCondition(ZjTzContractCondition zjTzContractCondition);

    public ResponseEntity getZjTzContractConditionDetails(ZjTzContractCondition zjTzContractCondition);

    public ResponseEntity saveZjTzContractCondition(ZjTzContractCondition zjTzContractCondition);

    public ResponseEntity updateZjTzContractCondition(ZjTzContractCondition zjTzContractCondition);

    public ResponseEntity batchDeleteUpdateZjTzContractCondition(List<ZjTzContractCondition> zjTzContractConditionList);

}

