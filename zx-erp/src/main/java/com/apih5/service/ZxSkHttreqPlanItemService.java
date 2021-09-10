package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkHttreqPlanItem;

public interface ZxSkHttreqPlanItemService {

    public ResponseEntity getZxSkHttreqPlanItemListByCondition(ZxSkHttreqPlanItem zxSkHttreqPlanItem);

    public ResponseEntity getZxSkHttreqPlanItemDetail(ZxSkHttreqPlanItem zxSkHttreqPlanItem);

    public ResponseEntity saveZxSkHttreqPlanItem(ZxSkHttreqPlanItem zxSkHttreqPlanItem);

    public ResponseEntity updateZxSkHttreqPlanItem(ZxSkHttreqPlanItem zxSkHttreqPlanItem);

    public ResponseEntity batchDeleteUpdateZxSkHttreqPlanItem(List<ZxSkHttreqPlanItem> zxSkHttreqPlanItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
