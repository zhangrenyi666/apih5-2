package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContract;

public interface ZxCtContractService {

    public ResponseEntity getZxCtContractListByCondition(ZxCtContract zxCtContract);

    public ResponseEntity getZxCtContractDetail(ZxCtContract zxCtContract);

    public ResponseEntity saveZxCtContract(ZxCtContract zxCtContract);

    public ResponseEntity updateZxCtContract(ZxCtContract zxCtContract);

    public ResponseEntity batchDeleteUpdateZxCtContract(List<ZxCtContract> zxCtContractList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity updateZxCtContractContrStatus(ZxCtContract zxCtContract);

	public ResponseEntity getZxCtContractListByStatus(ZxCtContract zxCtContract);
	
	public ResponseEntity updateRealEndDate(ZxCtContract zxCtContract);

	public ResponseEntity updateZxCtContractFinishInfo(ZxCtContract zxCtContract);

	public ResponseEntity getZxCtContractListByOrgId(ZxCtContract zxCtContract);

	public ResponseEntity jobSyncZxCtContractIsSettle(ZxCtContract zxCtContract) throws Exception;

    public ResponseEntity getZxCtContractListByCompanyId(ZxCtContract zxCtContract);
}
