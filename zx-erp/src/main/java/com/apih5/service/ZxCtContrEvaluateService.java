package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrEvaluate;

public interface ZxCtContrEvaluateService {

    public ResponseEntity getZxCtContrEvaluateListByCondition(ZxCtContrEvaluate zxCtContrEvaluate);

    public ResponseEntity getZxCtContrEvaluateDetail(ZxCtContrEvaluate zxCtContrEvaluate);

    public ResponseEntity saveZxCtContrEvaluate(ZxCtContrEvaluate zxCtContrEvaluate);

    public ResponseEntity updateZxCtContrEvaluate(ZxCtContrEvaluate zxCtContrEvaluate);

    public ResponseEntity batchDeleteUpdateZxCtContrEvaluate(List<ZxCtContrEvaluate> zxCtContrEvaluateList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
