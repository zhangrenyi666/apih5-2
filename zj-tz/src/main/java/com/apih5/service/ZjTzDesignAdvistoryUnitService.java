package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnit;

public interface ZjTzDesignAdvistoryUnitService {

    public ResponseEntity getZjTzDesignAdvistoryUnitListByCondition(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit);

    public ResponseEntity getZjTzDesignAdvistoryUnitDetails(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit);

    public ResponseEntity saveZjTzDesignAdvistoryUnit(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit);

    public ResponseEntity updateZjTzDesignAdvistoryUnit(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit);

    public ResponseEntity batchDeleteUpdateZjTzDesignAdvistoryUnit(List<ZjTzDesignAdvistoryUnit> zjTzDesignAdvistoryUnitList);

}

