package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseProjectConf;

public interface BaseProjectConfService {

    public ResponseEntity getBaseProjectConfListByCondition(BaseProjectConf baseProjectConf);

    public ResponseEntity getBaseProjectConfDetail(BaseProjectConf baseProjectConf);

    public ResponseEntity saveBaseProjectConf(BaseProjectConf baseProjectConf);

    public ResponseEntity updateBaseProjectConf(BaseProjectConf baseProjectConf);

    public ResponseEntity batchDeleteUpdateBaseProjectConf(List<BaseProjectConf> baseProjectConfList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
