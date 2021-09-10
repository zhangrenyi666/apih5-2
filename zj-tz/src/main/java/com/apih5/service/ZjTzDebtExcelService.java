package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzDebtExcel;

public interface ZjTzDebtExcelService {

    public ResponseEntity getZjTzDebtExcelListByCondition(ZjTzDebtExcel zjTzDebtExcel);

    public ResponseEntity getZjTzDebtExcelDetails(ZjTzDebtExcel zjTzDebtExcel);

    public ResponseEntity saveZjTzDebtExcel(ZjTzDebtExcel zjTzDebtExcel);

    public ResponseEntity updateZjTzDebtExcel(ZjTzDebtExcel zjTzDebtExcel);

    public ResponseEntity batchDeleteUpdateZjTzDebtExcel(List<ZjTzDebtExcel> zjTzDebtExcelList);

	public ResponseEntity batchImportZjTzDebtExcel(ZjTzDebtExcel zjTzDebtExcel);

}

