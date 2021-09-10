package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrJzBase;

public interface ZxCtContrJzBaseService {

    public ResponseEntity getZxCtContrJzBaseListByCondition(ZxCtContrJzBase zxCtContrJzBase);

    public ResponseEntity getZxCtContrJzBaseDetail(ZxCtContrJzBase zxCtContrJzBase);

    public ResponseEntity saveZxCtContrJzBase(ZxCtContrJzBase zxCtContrJzBase);

    public ResponseEntity updateZxCtContrJzBase(ZxCtContrJzBase zxCtContrJzBase);

    public ResponseEntity batchDeleteUpdateZxCtContrJzBase(List<ZxCtContrJzBase> zxCtContrJzBaseList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity editZxCtContrJzBaseItem(ZxCtContrJzBase zxCtContrJzBase);
}
