package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTeamScore;

public interface ZjLzehTeamScoreService {

    public ResponseEntity getZjLzehTeamScoreListByCondition(ZjLzehTeamScore zjLzehTeamScore);

    public ResponseEntity getZjLzehTeamScoreDetail(ZjLzehTeamScore zjLzehTeamScore);

    public ResponseEntity saveZjLzehTeamScore(ZjLzehTeamScore zjLzehTeamScore);

    public ResponseEntity updateZjLzehTeamScore(ZjLzehTeamScore zjLzehTeamScore);

    public ResponseEntity batchDeleteUpdateZjLzehTeamScore(List<ZjLzehTeamScore> zjLzehTeamScoreList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
