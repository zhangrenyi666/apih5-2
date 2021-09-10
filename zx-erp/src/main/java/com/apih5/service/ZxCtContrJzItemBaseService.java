package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrJzItemBase;

public interface ZxCtContrJzItemBaseService {

    public ResponseEntity getZxCtContrJzItemBaseListByCondition(ZxCtContrJzItemBase zxCtContrJzItemBase);

    public ResponseEntity getZxCtContrJzItemBaseDetail(ZxCtContrJzItemBase zxCtContrJzItemBase);

    public ResponseEntity saveZxCtContrJzItemBase(ZxCtContrJzItemBase zxCtContrJzItemBase);

    public ResponseEntity updateZxCtContrJzItemBase(ZxCtContrJzItemBase zxCtContrJzItemBase);

    public ResponseEntity batchDeleteUpdateZxCtContrJzItemBase(List<ZxCtContrJzItemBase> zxCtContrJzItemBaseList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
