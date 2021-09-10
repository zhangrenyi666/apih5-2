package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEWorkItem;

public interface ZxEqEWorkItemService {

    public ResponseEntity getZxEqEWorkItemListByCondition(ZxEqEWorkItem zxEqEWorkItem);

    public ResponseEntity getZxEqEWorkItemDetails(ZxEqEWorkItem zxEqEWorkItem);

    public ResponseEntity saveZxEqEWorkItem(ZxEqEWorkItem zxEqEWorkItem);

    public ResponseEntity updateZxEqEWorkItem(ZxEqEWorkItem zxEqEWorkItem);

    public ResponseEntity batchDeleteUpdateZxEqEWorkItem(List<ZxEqEWorkItem> zxEqEWorkItemList);

	public List<ZxEqEWorkItem> ureportZxEqEWorkItemListForCar(ZxEqEWorkItem zxEqEWorkItem);

}

