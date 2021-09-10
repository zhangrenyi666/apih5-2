package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaEquipResSettleItem;

public interface ZxSaEquipResSettleItemService {

    public ResponseEntity getZxSaEquipResSettleItemListByCondition(ZxSaEquipResSettleItem zxSaEquipResSettleItem);

    public ResponseEntity getZxSaEquipResSettleItemDetail(ZxSaEquipResSettleItem zxSaEquipResSettleItem);

    public ResponseEntity saveZxSaEquipResSettleItem(ZxSaEquipResSettleItem zxSaEquipResSettleItem);

    public ResponseEntity updateZxSaEquipResSettleItem(ZxSaEquipResSettleItem zxSaEquipResSettleItem);

    public ResponseEntity batchDeleteUpdateZxSaEquipResSettleItem(List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList);

	public List<ZxSaEquipResSettleItem> ureportZxSaEquipResSettleItem(ZxSaEquipResSettleItem zxSaEquipResSettleItem);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
