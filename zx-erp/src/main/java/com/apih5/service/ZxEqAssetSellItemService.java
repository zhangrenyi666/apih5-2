package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqAssetSellItem;

public interface ZxEqAssetSellItemService {

    public ResponseEntity getZxEqAssetSellItemListByCondition(ZxEqAssetSellItem zxEqAssetSellItem);

    public ResponseEntity getZxEqAssetSellItemDetails(ZxEqAssetSellItem zxEqAssetSellItem);

    public ResponseEntity saveZxEqAssetSellItem(ZxEqAssetSellItem zxEqAssetSellItem);

    public ResponseEntity updateZxEqAssetSellItem(ZxEqAssetSellItem zxEqAssetSellItem);

    public ResponseEntity batchDeleteUpdateZxEqAssetSellItem(List<ZxEqAssetSellItem> zxEqAssetSellItemList);

	public List<ZxEqAssetSellItem> ureportZxEqAssetSellItemList(ZxEqAssetSellItem zxEqAssetSellItem);

}

