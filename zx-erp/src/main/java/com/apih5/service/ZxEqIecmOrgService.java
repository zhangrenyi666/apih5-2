package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqIecmOrg;

public interface ZxEqIecmOrgService {

    public ResponseEntity getZxEqIecmOrgListByCondition(ZxEqIecmOrg zxEqIecmOrg);

    public ResponseEntity getZxEqIecmOrgDetails(ZxEqIecmOrg zxEqIecmOrg);

    public ResponseEntity saveZxEqIecmOrg(ZxEqIecmOrg zxEqIecmOrg);

    public ResponseEntity updateZxEqIecmOrg(ZxEqIecmOrg zxEqIecmOrg);

    public ResponseEntity batchDeleteUpdateZxEqIecmOrg(List<ZxEqIecmOrg> zxEqIecmOrgList);

}

