package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecordItem;

public interface ZxEqRentOutEqRecordItemService {

    public ResponseEntity getZxEqRentOutEqRecordItemListByCondition(ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem);

    public ResponseEntity getZxEqRentOutEqRecordItemDetails(ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem);

    public ResponseEntity saveZxEqRentOutEqRecordItem(ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem);

    public ResponseEntity updateZxEqRentOutEqRecordItem(ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem);

    public ResponseEntity batchDeleteUpdateZxEqRentOutEqRecordItem(List<ZxEqRentOutEqRecordItem> zxEqRentOutEqRecordItemList);

}

