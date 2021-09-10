package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxUserScoringLeader;

public interface ZjXmJxUserScoringLeaderService {

	public ResponseEntity getZjXmJxUserScoringLeaderListByCondition(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader);

	public ResponseEntity getZjXmJxUserScoringLeaderDetails(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader);

	public ResponseEntity saveZjXmJxUserScoringLeader(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader);

	public ResponseEntity updateZjXmJxUserScoringLeader(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader);

	public ResponseEntity batchDeleteUpdateZjXmJxUserScoringLeader(
			List<ZjXmJxUserScoringLeader> zjXmJxUserScoringLeaderList);

	public ResponseEntity setUpZjXmJxUserScoringLeader(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader);

	public ResponseEntity manualAddZjXmJxUserScoringLeader(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader);
}
