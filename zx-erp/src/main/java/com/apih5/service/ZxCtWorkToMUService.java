package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtWorkToMU;

public interface ZxCtWorkToMUService {

    public ResponseEntity getZxCtWorkToMUListByCondition(ZxCtWorkToMU zxCtWorkToMU);

    public ResponseEntity getZxCtWorkToMUDetail(ZxCtWorkToMU zxCtWorkToMU);

    public ResponseEntity saveZxCtWorkToMU(ZxCtWorkToMU zxCtWorkToMU);

    public ResponseEntity updateZxCtWorkToMU(ZxCtWorkToMU zxCtWorkToMU);

    public ResponseEntity batchDeleteUpdateZxCtWorkToMU(List<ZxCtWorkToMU> zxCtWorkToMUList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity addAllWorkToMUByOrgID(ZxCtWorkToMU zxCtWorkToMU);
}
