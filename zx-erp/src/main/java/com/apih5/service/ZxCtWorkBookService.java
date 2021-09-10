package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtWorkBook;

public interface ZxCtWorkBookService {

    public ResponseEntity getZxCtWorkBookListByCondition(ZxCtWorkBook zxCtWorkBook);

    public ResponseEntity getZxCtWorkBookDetail(ZxCtWorkBook zxCtWorkBook);

    public ResponseEntity saveZxCtWorkBook(ZxCtWorkBook zxCtWorkBook);

    public ResponseEntity updateZxCtWorkBook(ZxCtWorkBook zxCtWorkBook);

    public ResponseEntity batchDeleteUpdateZxCtWorkBook(List<ZxCtWorkBook> zxCtWorkBookList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
	public ResponseEntity deleteAllZxCtWorkBook(ZxCtWorkBook zxCtWorkBook);

	public ResponseEntity zxCtContractQuantity(ZxCtWorkBook zxCtWorkBook);

	public ResponseEntity zxCtVerificationQuantity(ZxCtWorkBook zxCtWorkBook);

}
