package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaOtherEquipResSettleItem;

public interface ZxSaOtherEquipResSettleItemService {

    public ResponseEntity getZxSaOtherEquipResSettleItemListByCondition(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem);

    public ResponseEntity getZxSaOtherEquipResSettleItemDetail(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem);

    public ResponseEntity saveZxSaOtherEquipResSettleItem(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem);

    public ResponseEntity updateZxSaOtherEquipResSettleItem(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem);

    public ResponseEntity batchDeleteUpdateZxSaOtherEquipResSettleItem(List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
