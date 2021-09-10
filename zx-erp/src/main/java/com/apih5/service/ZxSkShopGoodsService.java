package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkShopGoods;

public interface ZxSkShopGoodsService {

    public ResponseEntity getZxSkShopGoodsListByCondition(ZxSkShopGoods zxSkShopGoods);

    public ResponseEntity getZxSkShopGoodsDetail(ZxSkShopGoods zxSkShopGoods);

    public ResponseEntity saveZxSkShopGoods(ZxSkShopGoods zxSkShopGoods);

    public ResponseEntity updateZxSkShopGoods(ZxSkShopGoods zxSkShopGoods);

    public ResponseEntity batchDeleteUpdateZxSkShopGoods(List<ZxSkShopGoods> zxSkShopGoodsList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
