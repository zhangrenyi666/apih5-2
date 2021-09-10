package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipDepreciationItem;

public interface ZxEqEquipDepreciationItemService {

    public ResponseEntity getZxEqEquipDepreciationItemListByCondition(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem);

    public ResponseEntity getZxEqEquipDepreciationItemDetails(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem);

    public ResponseEntity saveZxEqEquipDepreciationItem(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem);

    public ResponseEntity updateZxEqEquipDepreciationItem(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem);

    public ResponseEntity batchDeleteUpdateZxEqEquipDepreciationItem(List<ZxEqEquipDepreciationItem> zxEqEquipDepreciationItemList);

	public ResponseEntity getZxEqEquipDepreciationItemListForTab(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem);

}

