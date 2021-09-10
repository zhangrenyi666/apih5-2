package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtEqContrItem;

public interface ZxCtEqContrItemService {

    public ResponseEntity getZxCtEqContrItemListByCondition(ZxCtEqContrItem zxCtEqContrItem);

    public ResponseEntity getZxCtEqContrItemDetail(ZxCtEqContrItem zxCtEqContrItem);

    public ResponseEntity saveZxCtEqContrItem(ZxCtEqContrItem zxCtEqContrItem);

    public ResponseEntity updateZxCtEqContrItem(ZxCtEqContrItem zxCtEqContrItem);

    public ResponseEntity batchDeleteUpdateZxCtEqContrItem(List<ZxCtEqContrItem> zxCtEqContrItemList);

	public ResponseEntity getZxCtEqContrItemForLimitPriceList(ZxCtEqContrItem zxCtEqContrItem);

	public ResponseEntity importZxCtEqContrItem(ZxCtEqContrItem zxCtEqContrItem);

	public ResponseEntity getZxCtEqContrItemListForContract(ZxCtEqContrItem zxCtEqContrItem);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
