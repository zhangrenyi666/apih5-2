package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipIntegratedQuery;

public interface ZxEqEquipIntegratedQueryService {

    public ResponseEntity getZxEqEquipIntegratedQueryListByCondition(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery);

    public ResponseEntity getZxEqEquipIntegratedQueryDetail(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery);

    public ResponseEntity saveZxEqEquipIntegratedQuery(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery);

    public ResponseEntity updateZxEqEquipIntegratedQuery(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery);

    public ResponseEntity batchDeleteUpdateZxEqEquipIntegratedQuery(List<ZxEqEquipIntegratedQuery> zxEqEquipIntegratedQueryList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity ureportZxEqEquipIntegratedQueryIdle(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery);
}
