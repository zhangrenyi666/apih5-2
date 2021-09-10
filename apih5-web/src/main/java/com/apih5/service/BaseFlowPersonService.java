package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseFlowPerson;

public interface BaseFlowPersonService {

    public ResponseEntity getBaseFlowPersonListByCondition(BaseFlowPerson baseFlowPerson);

    public ResponseEntity saveBaseFlowPerson(BaseFlowPerson baseFlowPerson);

    public ResponseEntity updateBaseFlowPerson(BaseFlowPerson baseFlowPerson);

    public ResponseEntity batchDeleteUpdateBaseFlowPerson(List<BaseFlowPerson> baseFlowPersonList);

}

