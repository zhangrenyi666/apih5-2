package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectSetItem;

public interface ZxSaProjectSetItemService {

    public ResponseEntity getZxSaProjectSetItemListByCondition(ZxSaProjectSetItem zxSaProjectSetItem);

    public ResponseEntity getZxSaProjectSetItemDetails(ZxSaProjectSetItem zxSaProjectSetItem);

    public ResponseEntity saveZxSaProjectSetItem(ZxSaProjectSetItem zxSaProjectSetItem);

    public ResponseEntity updateZxSaProjectSetItem(ZxSaProjectSetItem zxSaProjectSetItem);

    public ResponseEntity batchDeleteUpdateZxSaProjectSetItem(List<ZxSaProjectSetItem> zxSaProjectSetItemList);

}

