package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzCashExcel;

public interface ZjTzCashExcelService {

    public ResponseEntity getZjTzCashExcelListByCondition(ZjTzCashExcel zjTzCashExcel);

    public ResponseEntity getZjTzCashExcelDetails(ZjTzCashExcel zjTzCashExcel);

    public ResponseEntity saveZjTzCashExcel(ZjTzCashExcel zjTzCashExcel);

    public ResponseEntity updateZjTzCashExcel(ZjTzCashExcel zjTzCashExcel);

    public ResponseEntity batchDeleteUpdateZjTzCashExcel(List<ZjTzCashExcel> zjTzCashExcelList);

	public ResponseEntity batchImportZjTzCashExcel(ZjTzCashExcel zjTzCashExcel);

}

