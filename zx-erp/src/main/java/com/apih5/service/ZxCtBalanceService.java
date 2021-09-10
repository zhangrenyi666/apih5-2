package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtBalance;

public interface ZxCtBalanceService {

    public ResponseEntity getZxCtBalanceListByCondition(ZxCtBalance zxCtBalance);

    public ResponseEntity getZxCtBalanceDetail(ZxCtBalance zxCtBalance);

    public ResponseEntity saveZxCtBalance(ZxCtBalance zxCtBalance);

    public ResponseEntity updateZxCtBalance(ZxCtBalance zxCtBalance);

    public ResponseEntity batchDeleteUpdateZxCtBalance(List<ZxCtBalance> zxCtBalanceList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity zxCtBalanceAudit(ZxCtBalance zxCtBalance);
}
