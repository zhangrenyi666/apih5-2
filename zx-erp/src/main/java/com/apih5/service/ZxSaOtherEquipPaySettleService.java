package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettle;

public interface ZxSaOtherEquipPaySettleService {

    public ResponseEntity getZxSaOtherEquipPaySettleListByCondition(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle);

    public ResponseEntity getZxSaOtherEquipPaySettleDetail(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle);

    public ResponseEntity saveZxSaOtherEquipPaySettle(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle);

    public ResponseEntity updateZxSaOtherEquipPaySettle(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle);

    public ResponseEntity batchDeleteUpdateZxSaOtherEquipPaySettle(List<ZxSaOtherEquipPaySettle> zxSaOtherEquipPaySettleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
