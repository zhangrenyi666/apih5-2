package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTeam;

public interface ZjLzehTeamService {

    public ResponseEntity getZjLzehTeamListByCondition(ZjLzehTeam zjLzehTeam);

    public ResponseEntity getZjLzehTeamDetails(ZjLzehTeam zjLzehTeam);

    public ResponseEntity saveZjLzehTeam(ZjLzehTeam zjLzehTeam);

    public ResponseEntity updateZjLzehTeam(ZjLzehTeam zjLzehTeam);

    public ResponseEntity batchDeleteUpdateZjLzehTeam(List<ZjLzehTeam> zjLzehTeamList);

}

