package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettleItem;

public interface ZxSaOtherEquipSettleItemService {

    public ResponseEntity getZxSaOtherEquipSettleItemListByCondition(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem);

    public ResponseEntity getZxSaOtherEquipSettleItemDetail(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem);

    public ResponseEntity saveZxSaOtherEquipSettleItem(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem);

    public ResponseEntity updateZxSaOtherEquipSettleItem(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem);

    public ResponseEntity batchDeleteUpdateZxSaOtherEquipSettleItem(List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxSaOtherEquipSettleItemByContractId(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem);
}
