package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtEqContrSupply;

public interface ZxCtEqContrSupplyService {

    public ResponseEntity getZxCtEqContrSupplyListByCondition(ZxCtEqContrSupply zxCtEqContrSupply);

    public ResponseEntity getZxCtEqContrSupplyDetail(ZxCtEqContrSupply zxCtEqContrSupply);

    public ResponseEntity saveZxCtEqContrSupply(ZxCtEqContrSupply zxCtEqContrSupply);

    public ResponseEntity updateZxCtEqContrSupply(ZxCtEqContrSupply zxCtEqContrSupply);

    public ResponseEntity batchDeleteUpdateZxCtEqContrSupply(List<ZxCtEqContrSupply> zxCtEqContrSupplyList);

	public ResponseEntity getZxCtEqContrSupplyListBycontractID(ZxCtEqContrSupply zxCtEqContrSupply);

	public ResponseEntity generateZxCtEqContrSupplyContractNo(ZxCtEqContrSupply zxCtEqContrSupply);

	public ResponseEntity updateZxCtEqContrSupplyForContractTab(ZxCtEqContrSupply zxCtEqContrSupply);


    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
