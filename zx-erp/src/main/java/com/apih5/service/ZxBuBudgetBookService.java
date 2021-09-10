package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuBudgetBook;

public interface ZxBuBudgetBookService {

    public ResponseEntity getZxBuBudgetBookListByCondition(ZxBuBudgetBook zxBuBudgetBook);

    public ResponseEntity getZxBuBudgetBookDetail(ZxBuBudgetBook zxBuBudgetBook);

    public ResponseEntity saveZxBuBudgetBook(ZxBuBudgetBook zxBuBudgetBook);

    public ResponseEntity updateZxBuBudgetBook(ZxBuBudgetBook zxBuBudgetBook);

    public ResponseEntity batchDeleteUpdateZxBuBudgetBook(List<ZxBuBudgetBook> zxBuBudgetBookList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
