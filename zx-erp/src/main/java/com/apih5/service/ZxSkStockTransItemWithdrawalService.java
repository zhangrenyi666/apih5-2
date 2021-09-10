package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemWithdrawal;

public interface ZxSkStockTransItemWithdrawalService {

    public ResponseEntity getZxSkStockTransItemWithdrawalListByCondition(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal);

    public ResponseEntity getZxSkStockTransItemWithdrawalDetails(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal);

    public ResponseEntity saveZxSkStockTransItemWithdrawal(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal);

    public ResponseEntity updateZxSkStockTransItemWithdrawal(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal);

    public ResponseEntity batchDeleteUpdateZxSkStockTransItemWithdrawal(List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList);

}

