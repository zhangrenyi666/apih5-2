package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreRule;

public interface ZjXmJxPeripheryScoreRuleService {

	public ResponseEntity getZjXmJxPeripheryScoreRuleListByCondition(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule);

	public ResponseEntity getZjXmJxPeripheryScoreRuleDetails(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule);

	public ResponseEntity saveZjXmJxPeripheryScoreRule(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule);

	public ResponseEntity updateZjXmJxPeripheryScoreRule(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule);

	public ResponseEntity batchDeleteUpdateZjXmJxPeripheryScoreRule(
			List<ZjXmJxPeripheryScoreRule> zjXmJxPeripheryScoreRuleList);

	public ResponseEntity getValidZjXmJxPeripheryScoreRule(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule);

	public ResponseEntity setUpZjXmJxPeripheryScoreRule(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule);
}
