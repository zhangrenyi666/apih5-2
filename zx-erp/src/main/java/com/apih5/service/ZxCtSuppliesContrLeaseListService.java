package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrLeaseList;

public interface ZxCtSuppliesContrLeaseListService {

    public ResponseEntity getZxCtSuppliesContrLeaseListListByCondition(ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList);

    public ResponseEntity getZxCtSuppliesContrLeaseListDetail(ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList);

    public ResponseEntity saveZxCtSuppliesContrLeaseList(ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList);

    public ResponseEntity updateZxCtSuppliesContrLeaseList(ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrLeaseList(List<ZxCtSuppliesContrLeaseList> zxCtSuppliesContrLeaseListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
