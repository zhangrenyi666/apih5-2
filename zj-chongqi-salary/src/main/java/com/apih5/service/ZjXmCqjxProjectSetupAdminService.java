package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupAdmin;

public interface ZjXmCqjxProjectSetupAdminService {

    public ResponseEntity getZjXmCqjxProjectSetupAdminListByCondition(ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin);

    public ResponseEntity getZjXmCqjxProjectSetupAdminDetails(ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin);

    public ResponseEntity saveZjXmCqjxProjectSetupAdmin(ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin);

    public ResponseEntity updateZjXmCqjxProjectSetupAdmin(ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSetupAdmin(List<ZjXmCqjxProjectSetupAdmin> zjXmCqjxProjectSetupAdminList);

}

