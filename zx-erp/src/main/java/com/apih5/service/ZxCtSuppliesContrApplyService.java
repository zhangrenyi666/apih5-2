package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApply;

import cn.hutool.json.JSONObject;

public interface ZxCtSuppliesContrApplyService {

    public ResponseEntity getZxCtSuppliesContrApplyListByCondition(ZxCtSuppliesContrApply zxCtSuppliesContrApply);

    public ResponseEntity getZxCtSuppliesContrApplyDetail(ZxCtSuppliesContrApply zxCtSuppliesContrApply);

    public ResponseEntity saveZxCtSuppliesContrApply(ZxCtSuppliesContrApply zxCtSuppliesContrApply);

    public ResponseEntity updateZxCtSuppliesContrApply(ZxCtSuppliesContrApply zxCtSuppliesContrApply);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApply(List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesContrApplyFlowDetail(ZxCtSuppliesContrApply zxCtSuppliesContrApply);
    
    public ResponseEntity getZxCtSuppliesContrNoByCode(ZxCtSuppliesContrApply zxCtSuppliesContrApply);
    
    public ResponseEntity updateZxCtSuppliesContrApplyFlow(ZxCtSuppliesContrApply zxCtSuppliesContrApply);
    
    public ResponseEntity addZxCtSuppliesContrApplyFlow(ZxCtSuppliesContrApply zxCtSuppliesContrApply);
    
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyFlow(List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList);
    
    public JSONObject checkZxCtSuppliesContrApplyByFlow(ZxCtSuppliesContrApply zxCtSuppliesContrApply);
}
