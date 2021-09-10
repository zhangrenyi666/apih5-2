package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqAbcMachinery;

public interface ZxEqAbcMachineryService {

    public ResponseEntity getZxEqAbcMachineryListByCondition(ZxEqAbcMachinery zxEqAbcMachinery);

    public ResponseEntity getZxEqAbcMachineryDetail(ZxEqAbcMachinery zxEqAbcMachinery);

    public ResponseEntity saveZxEqAbcMachinery(ZxEqAbcMachinery zxEqAbcMachinery);

    public ResponseEntity updateZxEqAbcMachinery(ZxEqAbcMachinery zxEqAbcMachinery);

    public ResponseEntity batchDeleteUpdateZxEqAbcMachinery(List<ZxEqAbcMachinery> zxEqAbcMachineryList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxEqAbcMachinery> ureportZxEqAbcMachinery(ZxEqAbcMachinery zxEqAbcMachinery);
    
    public ResponseEntity ureportZxEqAbcMachineryIdle(ZxEqAbcMachinery zxEqAbcMachinery);
}
