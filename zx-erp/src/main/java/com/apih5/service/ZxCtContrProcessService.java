package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrProcess;

public interface ZxCtContrProcessService {

	public ResponseEntity getZxCtContrProcessListByCondition(ZxCtContrProcess zxCtContrProcess);

	public ResponseEntity getZxCtContrProcessDetails(ZxCtContrProcess zxCtContrProcess);

	public ResponseEntity saveZxCtContrProcess(ZxCtContrProcess zxCtContrProcess);

	public ResponseEntity updateZxCtContrProcess(ZxCtContrProcess zxCtContrProcess);

	public ResponseEntity batchDeleteUpdateZxCtContrProcess(List<ZxCtContrProcess> zxCtContrProcessList);

	public ResponseEntity getZxCtContrProcessTree(ZxCtContrProcess zxCtContrProcess);

	public ResponseEntity getZxCtContrProcessItemList(ZxCtContrProcess zxCtContrProcess);

	public ResponseEntity gcsgGetZxCtContrProcessList(ZxCtContrProcess zxCtContrProcess);

}
