package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseJobLevel;

public interface BaseJobLevelService {

    public ResponseEntity getBaseJobLevelListByCondition(BaseJobLevel baseJobLevel);

    public ResponseEntity getBaseJobLevelDetail(BaseJobLevel baseJobLevel);

    public ResponseEntity saveBaseJobLevel(BaseJobLevel baseJobLevel);

    public ResponseEntity updateBaseJobLevel(BaseJobLevel baseJobLevel);

    public ResponseEntity batchDeleteUpdateBaseJobLevel(List<BaseJobLevel> baseJobLevelList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
