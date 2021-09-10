package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTtReqPlanItem;

public interface ZxSkTtReqPlanItemService {

    public ResponseEntity getZxSkTtReqPlanItemListByCondition(ZxSkTtReqPlanItem zxSkTtReqPlanItem);

    public ResponseEntity getZxSkTtReqPlanItemDetails(ZxSkTtReqPlanItem zxSkTtReqPlanItem);

    public ResponseEntity saveZxSkTtReqPlanItem(ZxSkTtReqPlanItem zxSkTtReqPlanItem);

    public ResponseEntity updateZxSkTtReqPlanItem(ZxSkTtReqPlanItem zxSkTtReqPlanItem);

    public ResponseEntity batchDeleteUpdateZxSkTtReqPlanItem(List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList);

}

