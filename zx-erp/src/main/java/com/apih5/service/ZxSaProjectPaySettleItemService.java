package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettleItem;

public interface ZxSaProjectPaySettleItemService {

    public ResponseEntity getZxSaProjectPaySettleItemListByCondition(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem);

    public ResponseEntity getZxSaProjectPaySettleItemDetail(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem);

    public ResponseEntity saveZxSaProjectPaySettleItem(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem);

    public ResponseEntity updateZxSaProjectPaySettleItem(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem);

    public ResponseEntity batchDeleteUpdateZxSaProjectPaySettleItem(List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity deleteAllZxSaProjectPaySettleItem(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem);
}
