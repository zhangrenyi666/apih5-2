package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqExternalEquipmentUsage;

public interface ZxEqExternalEquipmentUsageService {

    public ResponseEntity getZxEqExternalEquipmentUsageListByCondition(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage);

    public ResponseEntity getZxEqExternalEquipmentUsageDetail(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage);

    public ResponseEntity saveZxEqExternalEquipmentUsage(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage);

    public ResponseEntity updateZxEqExternalEquipmentUsage(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage);

    public ResponseEntity batchDeleteUpdateZxEqExternalEquipmentUsage(List<ZxEqExternalEquipmentUsage> zxEqExternalEquipmentUsageList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxEqExternalEquipmentUsage> ureportZxEqExternalEquipmentUsage(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage);

    public ResponseEntity ureportZxEqExternalEquipmentUsageIdle(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage);
}
