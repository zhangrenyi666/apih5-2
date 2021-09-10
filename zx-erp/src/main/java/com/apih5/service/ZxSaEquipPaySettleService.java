package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettle;

public interface ZxSaEquipPaySettleService {

    public ResponseEntity getZxSaEquipPaySettleListByCondition(ZxSaEquipPaySettle zxSaEquipPaySettle);

    public ResponseEntity getZxSaEquipPaySettleDetail(ZxSaEquipPaySettle zxSaEquipPaySettle);

    public ResponseEntity saveZxSaEquipPaySettle(ZxSaEquipPaySettle zxSaEquipPaySettle);

    public ResponseEntity updateZxSaEquipPaySettle(ZxSaEquipPaySettle zxSaEquipPaySettle);

    public ResponseEntity batchDeleteUpdateZxSaEquipPaySettle(List<ZxSaEquipPaySettle> zxSaEquipPaySettleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
