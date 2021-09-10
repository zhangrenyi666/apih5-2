package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtValuationRules;

public interface ZxCtValuationRulesService {

    public ResponseEntity getZxCtValuationRulesListByCondition(ZxCtValuationRules zxCtValuationRules);

    public ResponseEntity getZxCtValuationRulesDetails(ZxCtValuationRules zxCtValuationRules);

    public ResponseEntity saveZxCtValuationRules(ZxCtValuationRules zxCtValuationRules);

    public ResponseEntity updateZxCtValuationRules(ZxCtValuationRules zxCtValuationRules);

    public ResponseEntity batchDeleteUpdateZxCtValuationRules(List<ZxCtValuationRules> zxCtValuationRulesList);

}

