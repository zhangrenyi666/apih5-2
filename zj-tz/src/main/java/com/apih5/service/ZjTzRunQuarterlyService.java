package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRunQuarterly;

public interface ZjTzRunQuarterlyService {

    public ResponseEntity getZjTzRunQuarterlyListByCondition(ZjTzRunQuarterly zjTzRunQuarterly);

    public ResponseEntity getZjTzRunQuarterlyDetails(ZjTzRunQuarterly zjTzRunQuarterly);

    public ResponseEntity saveZjTzRunQuarterly(ZjTzRunQuarterly zjTzRunQuarterly);

    public ResponseEntity updateZjTzRunQuarterly(ZjTzRunQuarterly zjTzRunQuarterly);

    public ResponseEntity batchDeleteUpdateZjTzRunQuarterly(List<ZjTzRunQuarterly> zjTzRunQuarterlyList);

}

