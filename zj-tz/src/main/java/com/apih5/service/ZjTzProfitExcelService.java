package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProfitExcel;

public interface ZjTzProfitExcelService {

    public ResponseEntity getZjTzProfitExcelListByCondition(ZjTzProfitExcel zjTzProfitExcel);

    public ResponseEntity getZjTzProfitExcelDetails(ZjTzProfitExcel zjTzProfitExcel);

    public ResponseEntity saveZjTzProfitExcel(ZjTzProfitExcel zjTzProfitExcel);

    public ResponseEntity updateZjTzProfitExcel(ZjTzProfitExcel zjTzProfitExcel);

    public ResponseEntity batchDeleteUpdateZjTzProfitExcel(List<ZjTzProfitExcel> zjTzProfitExcelList);

	public ResponseEntity batchImportZjTzProfitExcel(ZjTzProfitExcel zjTzProfitExcel);

}

