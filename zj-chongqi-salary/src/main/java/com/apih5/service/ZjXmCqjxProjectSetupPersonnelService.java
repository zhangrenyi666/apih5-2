package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupPersonnel;

public interface ZjXmCqjxProjectSetupPersonnelService {

    public ResponseEntity getZjXmCqjxProjectSetupPersonnelListByCondition(ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel);

    public ResponseEntity getZjXmCqjxProjectSetupPersonnelDetails(ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel);

    public ResponseEntity saveZjXmCqjxProjectSetupPersonnel(ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel);

    public ResponseEntity updateZjXmCqjxProjectSetupPersonnel(ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSetupPersonnel(List<ZjXmCqjxProjectSetupPersonnel> zjXmCqjxProjectSetupPersonnelList);

}

