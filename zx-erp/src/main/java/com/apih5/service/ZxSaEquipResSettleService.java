package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaEquipResSettle;

public interface ZxSaEquipResSettleService {

    public ResponseEntity getZxSaEquipResSettleListByCondition(ZxSaEquipResSettle zxSaEquipResSettle);

    public ResponseEntity getZxSaEquipResSettleDetail(ZxSaEquipResSettle zxSaEquipResSettle);

    public ResponseEntity saveZxSaEquipResSettle(ZxSaEquipResSettle zxSaEquipResSettle);

    public ResponseEntity updateZxSaEquipResSettle(ZxSaEquipResSettle zxSaEquipResSettle);

    public ResponseEntity batchDeleteUpdateZxSaEquipResSettle(List<ZxSaEquipResSettle> zxSaEquipResSettleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
