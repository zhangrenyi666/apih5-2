package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTeamInformationTeam;

public interface ZjLzehTeamInformationTeamService {

    public ResponseEntity getZjLzehTeamInformationTeamListByCondition(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam);

    public ResponseEntity getZjLzehTeamInformationTeamDetails(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam);

    public ResponseEntity saveZjLzehTeamInformationTeam(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam);

    public ResponseEntity updateZjLzehTeamInformationTeam(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam);

    public ResponseEntity batchDeleteUpdateZjLzehTeamInformationTeam(List<ZjLzehTeamInformationTeam> zjLzehTeamInformationTeamList);

}

