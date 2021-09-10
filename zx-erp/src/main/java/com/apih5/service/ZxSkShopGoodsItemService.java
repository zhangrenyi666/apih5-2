package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkShopGoodsItem;

public interface ZxSkShopGoodsItemService {

    public ResponseEntity getZxSkShopGoodsItemListByCondition(ZxSkShopGoodsItem zxSkShopGoodsItem);

    public ResponseEntity getZxSkShopGoodsItemDetail(ZxSkShopGoodsItem zxSkShopGoodsItem);

    public ResponseEntity saveZxSkShopGoodsItem(ZxSkShopGoodsItem zxSkShopGoodsItem);

    public ResponseEntity updateZxSkShopGoodsItem(ZxSkShopGoodsItem zxSkShopGoodsItem);

    public ResponseEntity batchDeleteUpdateZxSkShopGoodsItem(List<ZxSkShopGoodsItem> zxSkShopGoodsItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
