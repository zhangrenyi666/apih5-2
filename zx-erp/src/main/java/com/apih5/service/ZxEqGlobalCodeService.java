package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqGlobalCode;

public interface ZxEqGlobalCodeService {

    public ResponseEntity getZxEqGlobalCodeListByCondition(ZxEqGlobalCode zxEqGlobalCode);

    public ResponseEntity getZxEqGlobalCodeDetails(ZxEqGlobalCode zxEqGlobalCode);

    public ResponseEntity saveZxEqGlobalCode(ZxEqGlobalCode zxEqGlobalCode);

    public ResponseEntity updateZxEqGlobalCode(ZxEqGlobalCode zxEqGlobalCode);

    public ResponseEntity batchDeleteUpdateZxEqGlobalCode(List<ZxEqGlobalCode> zxEqGlobalCodeList);

}

