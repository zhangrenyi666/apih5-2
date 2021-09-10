package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectFinishItem;

public interface ZxSaProjectFinishItemService {

    public ResponseEntity getZxSaProjectFinishItemListByCondition(ZxSaProjectFinishItem zxSaProjectFinishItem);

    public ResponseEntity getZxSaProjectFinishItemDetails(ZxSaProjectFinishItem zxSaProjectFinishItem);

    public ResponseEntity saveZxSaProjectFinishItem(ZxSaProjectFinishItem zxSaProjectFinishItem);

    public ResponseEntity updateZxSaProjectFinishItem(ZxSaProjectFinishItem zxSaProjectFinishItem);

    public ResponseEntity batchDeleteUpdateZxSaProjectFinishItem(List<ZxSaProjectFinishItem> zxSaProjectFinishItemList);

}

