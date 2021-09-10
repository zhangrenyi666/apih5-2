package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzFinanceMonth;

public interface ZjTzFinanceMonthService {

    public ResponseEntity getZjTzFinanceMonthListByCondition(ZjTzFinanceMonth zjTzFinanceMonth);

    public ResponseEntity getZjTzFinanceMonthDetails(ZjTzFinanceMonth zjTzFinanceMonth);

    public ResponseEntity saveZjTzFinanceMonth(ZjTzFinanceMonth zjTzFinanceMonth);

    public ResponseEntity updateZjTzFinanceMonth(ZjTzFinanceMonth zjTzFinanceMonth);

    public ResponseEntity batchDeleteUpdateZjTzFinanceMonth(List<ZjTzFinanceMonth> zjTzFinanceMonthList);

	public ResponseEntity batchReleaseZjTzFinanceMonth(List<ZjTzFinanceMonth> zjTzFinanceMonthList);

	public ResponseEntity batchRecallZjTzFinanceMonth(List<ZjTzFinanceMonth> zjTzFinanceMonthList);

}

