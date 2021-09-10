package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqMoveUseOrg;

public interface ZxEqMoveUseOrgService {

    public ResponseEntity getZxEqMoveUseOrgListByCondition(ZxEqMoveUseOrg zxEqMoveUseOrg);

    public ResponseEntity getZxEqMoveUseOrgDetails(ZxEqMoveUseOrg zxEqMoveUseOrg);

    public ResponseEntity saveZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg);

    public ResponseEntity updateZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg);

    public ResponseEntity batchDeleteUpdateZxEqMoveUseOrg(List<ZxEqMoveUseOrg> zxEqMoveUseOrgList);

	public ResponseEntity outConfirmZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg);

	public ResponseEntity inConfirmZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg);

}

