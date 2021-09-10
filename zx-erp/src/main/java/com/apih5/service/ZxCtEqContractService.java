package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtEqContract;

public interface ZxCtEqContractService {

    public ResponseEntity getZxCtEqContractListByCondition(ZxCtEqContract zxCtEqContract);

    public ResponseEntity getZxCtEqContractDetail(ZxCtEqContract zxCtEqContract);

    public ResponseEntity saveZxCtEqContract(ZxCtEqContract zxCtEqContract);

    public ResponseEntity updateZxCtEqContract(ZxCtEqContract zxCtEqContract);

    public ResponseEntity batchDeleteUpdateZxCtEqContract(List<ZxCtEqContract> zxCtEqContractList);

	public ResponseEntity getZxCtEqContractListByOrgId(ZxCtEqContract zxCtEqContract);

	public ResponseEntity recoverZxCtEqContract(ZxCtEqContract zxCtEqContract);

	public ResponseEntity getZxCtEqContractListForEqMan(ZxCtEqContract zxCtEqContract);

	public ResponseEntity getZxCtEqContractListForOuterEquip(ZxCtEqContract zxCtEqContract);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
