package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzDebt;

public interface ZjTzDebtService {

    public ResponseEntity getZjTzDebtListByCondition(ZjTzDebt zjTzDebt);

    public ResponseEntity getZjTzDebtDetails(ZjTzDebt zjTzDebt);

    public ResponseEntity saveZjTzDebt(ZjTzDebt zjTzDebt);

    public ResponseEntity updateZjTzDebt(ZjTzDebt zjTzDebt);

    public ResponseEntity batchDeleteUpdateZjTzDebt(List<ZjTzDebt> zjTzDebtList);

}

