package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqMoveSupervisorItem;

public interface ZxEqMoveSupervisorItemService {

    public ResponseEntity getZxEqMoveSupervisorItemListByCondition(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem);

    public ResponseEntity getZxEqMoveSupervisorItemDetails(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem);

    public ResponseEntity saveZxEqMoveSupervisorItem(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem);

    public ResponseEntity updateZxEqMoveSupervisorItem(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem);

    public ResponseEntity batchDeleteUpdateZxEqMoveSupervisorItem(List<ZxEqMoveSupervisorItem> zxEqMoveSupervisorItemList);

	public ResponseEntity getZxEqMoveSupervisorItemListForTab(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem);

}

