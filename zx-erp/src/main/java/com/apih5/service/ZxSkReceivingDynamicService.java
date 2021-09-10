package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkReceivingDynamic;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;

public interface ZxSkReceivingDynamicService {

    public ResponseEntity getZxSkReceivingDynamicListByCondition(ZxSkReceivingDynamic zxSkReceivingDynamic);

    public ResponseEntity getZxSkReceivingDynamicDetail(ZxSkReceivingDynamic zxSkReceivingDynamic);

    public ResponseEntity saveZxSkReceivingDynamic(ZxSkReceivingDynamic zxSkReceivingDynamic);

    public ResponseEntity updateZxSkReceivingDynamic(ZxSkReceivingDynamic zxSkReceivingDynamic);

    public ResponseEntity batchDeleteUpdateZxSkReceivingDynamic(List<ZxSkReceivingDynamic> zxSkReceivingDynamicList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

}
