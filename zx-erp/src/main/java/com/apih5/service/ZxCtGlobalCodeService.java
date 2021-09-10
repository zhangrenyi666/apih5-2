package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtGlobalCode;

public interface ZxCtGlobalCodeService {

    public ResponseEntity getZxCtGlobalCodeListByCondition(ZxCtGlobalCode zxCtGlobalCode);

    public ResponseEntity getZxCtGlobalCodeDetails(ZxCtGlobalCode zxCtGlobalCode);

    public ResponseEntity saveZxCtGlobalCode(ZxCtGlobalCode zxCtGlobalCode);

    public ResponseEntity updateZxCtGlobalCode(ZxCtGlobalCode zxCtGlobalCode);

    public ResponseEntity batchDeleteUpdateZxCtGlobalCode(List<ZxCtGlobalCode> zxCtGlobalCodeList);

}

