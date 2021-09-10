package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtEqContrApply;

import cn.hutool.json.JSONObject;

public interface ZxCtEqContrApplyService {

    public ResponseEntity getZxCtEqContrApplyListByCondition(ZxCtEqContrApply zxCtEqContrApply);

    public ResponseEntity getZxCtEqContrApplyDetail(ZxCtEqContrApply zxCtEqContrApply);

    public ResponseEntity saveZxCtEqContrApply(ZxCtEqContrApply zxCtEqContrApply);

    public ResponseEntity updateZxCtEqContrApply(ZxCtEqContrApply zxCtEqContrApply);

    public ResponseEntity batchDeleteUpdateZxCtEqContrApply(List<ZxCtEqContrApply> zxCtEqContrApplyList);

	public ResponseEntity appZxCtEqContrApplyJudgeZxCtContractByContractStatus(ZxCtEqContrApply zxCtEqContrApply);

	public ResponseEntity generateZxCtEqContrApplyContractNo(ZxCtEqContrApply zxCtEqContrApply);

	public JSONObject checkZxCtEqContrApplyByFlow(ZxCtEqContrApply zxCtEqContrApply);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
