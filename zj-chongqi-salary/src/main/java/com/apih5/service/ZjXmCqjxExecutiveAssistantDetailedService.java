package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;

public interface ZjXmCqjxExecutiveAssistantDetailedService {

    public ResponseEntity getZjXmCqjxExecutiveAssistantDetailedListByCondition(ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed);

    public ResponseEntity getZjXmCqjxExecutiveAssistantDetailedDetails(ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed);

    public ResponseEntity saveZjXmCqjxExecutiveAssistantDetailed(ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed);

    public ResponseEntity updateZjXmCqjxExecutiveAssistantDetailed(ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed);

    public ResponseEntity batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed(List<ZjXmCqjxExecutiveAssistantDetailed> zjXmCqjxExecutiveAssistantDetailedList);

}

