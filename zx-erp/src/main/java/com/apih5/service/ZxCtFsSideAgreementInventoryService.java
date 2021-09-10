package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtFsSideAgreementInventory;

public interface ZxCtFsSideAgreementInventoryService {

    public ResponseEntity getZxCtFsSideAgreementInventoryListByCondition(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory);

    public ResponseEntity getZxCtFsSideAgreementInventoryDetail(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory);

    public ResponseEntity saveZxCtFsSideAgreementInventory(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory);

    public ResponseEntity updateZxCtFsSideAgreementInventory(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory);

    public ResponseEntity batchDeleteUpdateZxCtFsSideAgreementInventory(List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventoryList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 批量修改或新增附属合同补充协议清单信息
     * @auther suncg
     * @Param zxCtFsSideAgreementInventoryList
     * */
    public ResponseEntity batchUpdateOrAdd(List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventoryList);
}
