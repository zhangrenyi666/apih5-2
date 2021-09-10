package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferWithdrawal;

public interface ZxSkStockTransferWithdrawalService {

    public ResponseEntity getZxSkStockTransferWithdrawalListByCondition(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal);

    public ResponseEntity getZxSkStockTransferWithdrawalDetails(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal);

    public ResponseEntity saveZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal);

    public ResponseEntity updateZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal);

    public ResponseEntity batchDeleteUpdateZxSkStockTransferWithdrawal(List<ZxSkStockTransferWithdrawal> zxSkStockTransferWithdrawalList);

    public ResponseEntity checkZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal);

    public ResponseEntity getZxSkStockTransferWithdrawalNo(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal);


}

