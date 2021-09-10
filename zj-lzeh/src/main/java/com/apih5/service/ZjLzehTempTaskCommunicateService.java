package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTempTaskCommunicate;

public interface ZjLzehTempTaskCommunicateService {

    public ResponseEntity getZjLzehTempTaskCommunicateListByCondition(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate);

    public ResponseEntity getZjLzehTempTaskCommunicateDetail(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate);

    public ResponseEntity saveZjLzehTempTaskCommunicate(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate);

    public ResponseEntity updateZjLzehTempTaskCommunicate(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate);

    public ResponseEntity batchDeleteUpdateZjLzehTempTaskCommunicate(List<ZjLzehTempTaskCommunicate> zjLzehTempTaskCommunicateList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZjLzehTempTaskCommunicateTreeByCondition(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate);

}
