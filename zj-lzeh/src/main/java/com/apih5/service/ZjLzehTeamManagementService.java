package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTeamManagement;

public interface ZjLzehTeamManagementService {

    public ResponseEntity getZjLzehTeamManagementListByCondition(ZjLzehTeamManagement zjLzehTeamManagement);

    public ResponseEntity getZjLzehTeamManagementDetail(ZjLzehTeamManagement zjLzehTeamManagement);

    public ResponseEntity saveZjLzehTeamManagement(ZjLzehTeamManagement zjLzehTeamManagement);

    public ResponseEntity updateZjLzehTeamManagement(ZjLzehTeamManagement zjLzehTeamManagement);

    public ResponseEntity batchDeleteUpdateZjLzehTeamManagement(List<ZjLzehTeamManagement> zjLzehTeamManagementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZjLzehTeamManagementisScoreCount();
}
