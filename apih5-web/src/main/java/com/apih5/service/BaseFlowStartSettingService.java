package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseFlowStartSetting;

public interface BaseFlowStartSettingService {

    public ResponseEntity getBaseFlowStartSettingListByCondition(BaseFlowStartSetting baseFlowStartSetting);

    public ResponseEntity saveBaseFlowStartSetting(BaseFlowStartSetting baseFlowStartSetting);

    public ResponseEntity updateBaseFlowStartSetting(BaseFlowStartSetting baseFlowStartSetting);

    public ResponseEntity batchDeleteUpdateBaseFlowStartSetting(List<BaseFlowStartSetting> baseFlowStartSettingList);

    public ResponseEntity getBaseFlowStartSettingListByFlow(BaseFlowStartSetting baseFlowStartSetting);
    
    public ResponseEntity saveBaseFlowStartSettingByFlow(BaseFlowStartSetting baseFlowStartSetting);

}

