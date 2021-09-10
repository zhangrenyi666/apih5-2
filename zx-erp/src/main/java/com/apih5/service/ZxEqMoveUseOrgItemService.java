package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqMoveUseOrgItem;

public interface ZxEqMoveUseOrgItemService {

    public ResponseEntity getZxEqMoveUseOrgItemListByCondition(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem);

    public ResponseEntity getZxEqMoveUseOrgItemDetails(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem);

    public ResponseEntity saveZxEqMoveUseOrgItem(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem);

    public ResponseEntity updateZxEqMoveUseOrgItem(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem);

    public ResponseEntity batchDeleteUpdateZxEqMoveUseOrgItem(List<ZxEqMoveUseOrgItem> zxEqMoveUseOrgItemList);

	public ResponseEntity getZxEqMoveUseOrgItemListForTab(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem);

	public List<ZxEqMoveUseOrgItem> ureportZxEqMoveUseOrgItemList(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem);

}

