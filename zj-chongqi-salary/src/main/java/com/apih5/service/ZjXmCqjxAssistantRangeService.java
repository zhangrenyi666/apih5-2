package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantRange;

public interface ZjXmCqjxAssistantRangeService {

    public ResponseEntity getZjXmCqjxAssistantRangeListByCondition(ZjXmCqjxAssistantRange zjXmCqjxAssistantRange);

    public ResponseEntity getZjXmCqjxAssistantRangeDetails(ZjXmCqjxAssistantRange zjXmCqjxAssistantRange);

    public ResponseEntity saveZjXmCqjxAssistantRange(ZjXmCqjxAssistantRange zjXmCqjxAssistantRange);

    public ResponseEntity updateZjXmCqjxAssistantRange(ZjXmCqjxAssistantRange zjXmCqjxAssistantRange);

    public ResponseEntity batchDeleteUpdateZjXmCqjxAssistantRange(List<ZjXmCqjxAssistantRange> zjXmCqjxAssistantRangeList);

}

