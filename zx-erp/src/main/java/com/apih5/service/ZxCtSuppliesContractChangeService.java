package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContractChange;

public interface ZxCtSuppliesContractChangeService {

    public ResponseEntity getZxCtSuppliesContractChangeListByCondition(ZxCtSuppliesContractChange zxCtSuppliesContractChange);

    public ResponseEntity getZxCtSuppliesContractChangeDetail(ZxCtSuppliesContractChange zxCtSuppliesContractChange);

    public ResponseEntity saveZxCtSuppliesContractChange(ZxCtSuppliesContractChange zxCtSuppliesContractChange);

    public ResponseEntity updateZxCtSuppliesContractChange(ZxCtSuppliesContractChange zxCtSuppliesContractChange);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContractChange(List<ZxCtSuppliesContractChange> zxCtSuppliesContractChangeList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
