package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaOtherEquipResSettle;

public interface ZxSaOtherEquipResSettleService {

    public ResponseEntity getZxSaOtherEquipResSettleListByCondition(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle);

    public ResponseEntity getZxSaOtherEquipResSettleDetail(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle);

    public ResponseEntity saveZxSaOtherEquipResSettle(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle);

    public ResponseEntity updateZxSaOtherEquipResSettle(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle);

    public ResponseEntity batchDeleteUpdateZxSaOtherEquipResSettle(List<ZxSaOtherEquipResSettle> zxSaOtherEquipResSettleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
