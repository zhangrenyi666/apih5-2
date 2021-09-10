package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettleItem;

public interface ZxSaOtherEquipPaySettleItemService {

    public ResponseEntity getZxSaOtherEquipPaySettleItemListByCondition(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem);

    public ResponseEntity getZxSaOtherEquipPaySettleItemDetail(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem);

    public ResponseEntity saveZxSaOtherEquipPaySettleItem(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem);

    public ResponseEntity updateZxSaOtherEquipPaySettleItem(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem);

    public ResponseEntity batchDeleteUpdateZxSaOtherEquipPaySettleItem(List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
