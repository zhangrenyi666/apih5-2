package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettleItem;

public interface ZxSaEquipPaySettleItemService {

    public ResponseEntity getZxSaEquipPaySettleItemListByCondition(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem);

    public ResponseEntity getZxSaEquipPaySettleItemDetail(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem);

    public ResponseEntity saveZxSaEquipPaySettleItem(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem);

    public ResponseEntity updateZxSaEquipPaySettleItem(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem);

    public ResponseEntity batchDeleteUpdateZxSaEquipPaySettleItem(List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemList);

	public List<ZxSaEquipPaySettleItem> ureportZxSaEquipPaySettleItemList(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
