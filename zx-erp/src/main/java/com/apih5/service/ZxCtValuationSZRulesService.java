package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtValuationSZRules;

public interface ZxCtValuationSZRulesService {

	public ResponseEntity getZxCtValuationSZRulesListByCondition(ZxCtValuationSZRules zxCtValuationSZRules);

	public ResponseEntity getZxCtValuationSZRulesDetails(ZxCtValuationSZRules zxCtValuationSZRules);

	public ResponseEntity saveZxCtValuationSZRules(ZxCtValuationSZRules zxCtValuationSZRules);

	public ResponseEntity updateZxCtValuationSZRules(ZxCtValuationSZRules zxCtValuationSZRules);

	public ResponseEntity batchDeleteUpdateZxCtValuationSZRules(List<ZxCtValuationSZRules> zxCtValuationSZRulesList);

	public ResponseEntity gcsgGetZxCtValuationSZRulesList(ZxCtValuationSZRules zxCtValuationSZRules);
}
