package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkMmReqPlanItem;

public interface ZxSkMmReqPlanItemService {

    public ResponseEntity getZxSkMmReqPlanItemListByCondition(ZxSkMmReqPlanItem zxSkMmReqPlanItem);

    public ResponseEntity getZxSkMmReqPlanItemDetails(ZxSkMmReqPlanItem zxSkMmReqPlanItem);

    public ResponseEntity saveZxSkMmReqPlanItem(ZxSkMmReqPlanItem zxSkMmReqPlanItem);

    public ResponseEntity updateZxSkMmReqPlanItem(ZxSkMmReqPlanItem zxSkMmReqPlanItem);

    public ResponseEntity batchDeleteUpdateZxSkMmReqPlanItem(List<ZxSkMmReqPlanItem> zxSkMmReqPlanItemList);

}

