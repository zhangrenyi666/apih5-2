package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRules;

public interface ZjTzRulesService {

    public ResponseEntity getZjTzRulesListByCondition(ZjTzRules zjTzRules);

    public ResponseEntity getZjTzRulesDetails(ZjTzRules zjTzRules);

    public ResponseEntity saveZjTzRules(ZjTzRules zjTzRules);

    public ResponseEntity updateZjTzRules(ZjTzRules zjTzRules);

    public ResponseEntity batchDeleteUpdateZjTzRules(List<ZjTzRules> zjTzRulesList);

	public ResponseEntity toHomeShowZjTzRules(ZjTzRules zjTzRules);

	public ResponseEntity batchDeleteReleaseZjTzRules(List<ZjTzRules> zjTzRulesList);

	public ResponseEntity batchDeleteRecallZjTzRules(List<ZjTzRules> zjTzRulesList);

	public ResponseEntity batchExportZjTzRulesFile(List<ZjTzRules> zjTzRulesList);

	public ResponseEntity getZjTzRulesHome(ZjTzRules zjTzRules);

}

