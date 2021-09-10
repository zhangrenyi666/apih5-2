package com.apih5.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCtContract;

public interface ZxGcsgCtContractService {

	public ResponseEntity getZxGcsgCtContractListByCondition(ZxGcsgCtContract zxGcsgCtContract);

	public ResponseEntity getZxGcsgCtContractDetail(ZxGcsgCtContract zxGcsgCtContract);

	public ResponseEntity saveZxGcsgCtContract(ZxGcsgCtContract zxGcsgCtContract);

	public ResponseEntity updateZxGcsgCtContract(ZxGcsgCtContract zxGcsgCtContract);

	public ResponseEntity batchDeleteUpdateZxGcsgCtContract(List<ZxGcsgCtContract> zxGcsgCtContractList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity getZxGcsgCtContractDetails(ZxGcsgCtContract zxGcsgCtContract);

	public ResponseEntity saveZxGcsgCtContractInfo(ZxGcsgCtContract zxGcsgCtContract);

	public ResponseEntity resumeZxGcsgCtContractStatus(ZxGcsgCtContract zxGcsgCtContract);

	public void glExportZxGcsgCtContractExcel(ZxGcsgCtContract zxGcsgCtContract, HttpServletResponse response);

}
