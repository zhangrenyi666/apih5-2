package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkSporadicItem;

public interface ZxSkSporadicItemService {

    public ResponseEntity getZxSkSporadicItemListByCondition(ZxSkSporadicItem zxSkSporadicItem);

    public ResponseEntity getZxSkSporadicItemDetails(ZxSkSporadicItem zxSkSporadicItem);

    public ResponseEntity saveZxSkSporadicItem(ZxSkSporadicItem zxSkSporadicItem);

    public ResponseEntity updateZxSkSporadicItem(ZxSkSporadicItem zxSkSporadicItem);

    public ResponseEntity batchDeleteUpdateZxSkSporadicItem(List<ZxSkSporadicItem> zxSkSporadicItemList);

}

