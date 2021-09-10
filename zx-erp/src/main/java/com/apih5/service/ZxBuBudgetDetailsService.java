package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuBudgetDetails;

public interface ZxBuBudgetDetailsService {

    public ResponseEntity getZxBuBudgetDetailsListByCondition(ZxBuBudgetDetails zxBuBudgetDetails);

    public ResponseEntity getZxBuBudgetDetailsDetail(ZxBuBudgetDetails zxBuBudgetDetails);

    public ResponseEntity saveZxBuBudgetDetails(ZxBuBudgetDetails zxBuBudgetDetails);

    public ResponseEntity updateZxBuBudgetDetails(ZxBuBudgetDetails zxBuBudgetDetails);

    public ResponseEntity batchDeleteUpdateZxBuBudgetDetails(List<ZxBuBudgetDetails> zxBuBudgetDetailsList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
