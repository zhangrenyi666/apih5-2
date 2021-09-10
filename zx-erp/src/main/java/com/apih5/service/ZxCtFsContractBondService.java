package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtFsContractBond;

public interface ZxCtFsContractBondService {

    public ResponseEntity getZxCtFsContractBondListByCondition(ZxCtFsContractBond zxCtFsContractBond);

    public ResponseEntity getZxCtFsContractBondDetail(ZxCtFsContractBond zxCtFsContractBond);

    public ResponseEntity saveZxCtFsContractBond(ZxCtFsContractBond zxCtFsContractBond);

    public ResponseEntity updateZxCtFsContractBond(ZxCtFsContractBond zxCtFsContractBond);

    public ResponseEntity batchDeleteUpdateZxCtFsContractBond(List<ZxCtFsContractBond> zxCtFsContractBondList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
