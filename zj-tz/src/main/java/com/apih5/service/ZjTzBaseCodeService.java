package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzBaseCode;

public interface ZjTzBaseCodeService {

    public ResponseEntity getZjTzBaseCodeListByCondition(ZjTzBaseCode zjTzBaseCode);

    public ResponseEntity getZjTzBaseCodeDetails(ZjTzBaseCode zjTzBaseCode);

    public ResponseEntity saveZjTzBaseCode(ZjTzBaseCode zjTzBaseCode);

    public ResponseEntity updateZjTzBaseCode(ZjTzBaseCode zjTzBaseCode);

    public ResponseEntity batchDeleteUpdateZjTzBaseCode(List<ZjTzBaseCode> zjTzBaseCodeList);

}

