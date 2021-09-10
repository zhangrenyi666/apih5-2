package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzQuality;

public interface ZjTzQualityService {

    public ResponseEntity getZjTzQualityListByCondition(ZjTzQuality zjTzQuality);

    public ResponseEntity getZjTzQualityDetails(ZjTzQuality zjTzQuality);

    public ResponseEntity saveZjTzQuality(ZjTzQuality zjTzQuality);

    public ResponseEntity updateZjTzQuality(ZjTzQuality zjTzQuality);

    public ResponseEntity batchDeleteUpdateZjTzQuality(List<ZjTzQuality> zjTzQualityList);

}

