package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;

public interface ZjXmCqjxStaffAssistantDetailedService {

    public ResponseEntity getZjXmCqjxStaffAssistantDetailedListByCondition(ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed);

    public ResponseEntity getZjXmCqjxStaffAssistantDetailedDetails(ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed);

    public ResponseEntity saveZjXmCqjxStaffAssistantDetailed(ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed);

    public ResponseEntity updateZjXmCqjxStaffAssistantDetailed(ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed);

    public ResponseEntity batchDeleteUpdateZjXmCqjxStaffAssistantDetailed(List<ZjXmCqjxStaffAssistantDetailed> zjXmCqjxStaffAssistantDetailedList);

}

