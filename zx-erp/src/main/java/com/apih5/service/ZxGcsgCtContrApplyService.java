package com.apih5.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApply;

public interface ZxGcsgCtContrApplyService {

	public ResponseEntity getZxGcsgCtContrApplyListByCondition(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity getZxGcsgCtContrApplyDetail(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity saveZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity updateZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity batchDeleteUpdateZxGcsgCtContrApply(List<ZxGcsgCtContrApply> zxGcsgCtContrApplyList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity psGetZxGcsgCtContrApplyList(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity psAddZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity psUpdateZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity psCheckZxGcsgCtContrApplyBeforeFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity psCheckZxGcsgCtContrApplyInFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity psGetZxGcsgCtContrApplyContractNo(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public void psExportZxGcsgCtContrApplyExcel(ZxGcsgCtContrApply zxGcsgCtContrApply, HttpServletResponse response);

	public ResponseEntity psUpdateZxGcsgCtContrApplyFirstByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity psUpdateZxGcsgCtContrApplySecondByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity psGetZxGcsgCtContrApplyDetailsByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity bxAddZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity bxGetZxGcsgCtContrApplyList(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity bxUpdateZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity bxGetZxGcsgCtContrApplyContractNo(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public void bxExportZxGcsgCtContrApplyExcel(ZxGcsgCtContrApply zxGcsgCtContrApply, HttpServletResponse response);

	public ResponseEntity bxCheckZxGcsgCtContrApplyBeforeFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity bxCheckZxGcsgCtContrApplyInFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity bxUpdateZxGcsgCtContrApplyFirstByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity bxUpdateZxGcsgCtContrApplySecondByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

	public ResponseEntity bxGetZxGcsgCtContrApplyDetailsByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply);

}
