package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTeamInformation;

public interface ZjLzehTeamInformationService {

    public ResponseEntity getZjLzehTeamInformationListByCondition(ZjLzehTeamInformation zjLzehTeamInformation);

    public ResponseEntity getZjLzehTeamInformationDetails(ZjLzehTeamInformation zjLzehTeamInformation);

    public ResponseEntity saveZjLzehTeamInformation(ZjLzehTeamInformation zjLzehTeamInformation);

    public ResponseEntity updateZjLzehTeamInformation(ZjLzehTeamInformation zjLzehTeamInformation);

    public ResponseEntity batchDeleteUpdateZjLzehTeamInformation(List<ZjLzehTeamInformation> zjLzehTeamInformationList);


}

