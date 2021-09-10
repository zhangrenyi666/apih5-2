package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseProjectConfJob;

public interface BaseProjectConfJobService {

    public ResponseEntity getBaseProjectConfJobListByCondition(BaseProjectConfJob baseProjectConfJob);

    public ResponseEntity getBaseProjectConfJobDetail(BaseProjectConfJob baseProjectConfJob);

    public ResponseEntity saveBaseProjectConfJob(BaseProjectConfJob baseProjectConfJob);

    public ResponseEntity updateBaseProjectConfJob(BaseProjectConfJob baseProjectConfJob);

    public ResponseEntity batchDeleteUpdateBaseProjectConfJob(List<BaseProjectConfJob> baseProjectConfJobList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
