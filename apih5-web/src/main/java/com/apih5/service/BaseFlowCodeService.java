package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseFlowCode;

public interface BaseFlowCodeService {

    public ResponseEntity getBaseFlowCodeListByCondition(BaseFlowCode baseFlowCode);

    public ResponseEntity saveBaseFlowCode(BaseFlowCode baseFlowCode);

    public ResponseEntity updateBaseFlowCode(BaseFlowCode baseFlowCode);

    public ResponseEntity batchDeleteUpdateBaseFlowCode(List<BaseFlowCode> baseFlowCodeList);
    
    public ResponseEntity baseFlowCodeImport(BaseFlowCode baseFlowCode);
    
    public ResponseEntity getFlowNameSelectList(BaseFlowCode baseFlowCode);
    
    public ResponseEntity getNodeNameSelectListByFlowName(BaseFlowCode baseFlowCode);
    
    
}

