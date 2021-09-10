package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseJobLevelSub;

public interface BaseJobLevelSubService {

    public ResponseEntity getBaseJobLevelSubListByCondition(BaseJobLevelSub baseJobLevelSub);

    public ResponseEntity getBaseJobLevelSubDetail(BaseJobLevelSub baseJobLevelSub);

    public ResponseEntity saveBaseJobLevelSub(BaseJobLevelSub baseJobLevelSub);

    public ResponseEntity updateBaseJobLevelSub(BaseJobLevelSub baseJobLevelSub);

    public ResponseEntity batchDeleteUpdateBaseJobLevelSub(List<BaseJobLevelSub> baseJobLevelSubList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
