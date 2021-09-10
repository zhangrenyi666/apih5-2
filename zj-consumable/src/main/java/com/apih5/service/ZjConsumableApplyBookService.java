package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjConsumableApplyBook;

public interface ZjConsumableApplyBookService {

    public ResponseEntity getZjConsumableApplyBookListByCondition(ZjConsumableApplyBook zjConsumableApplyBook);

    public ResponseEntity getZjConsumableApplyBookDetails(ZjConsumableApplyBook zjConsumableApplyBook);

    public ResponseEntity saveZjConsumableApplyBook(ZjConsumableApplyBook zjConsumableApplyBook);

    public ResponseEntity updateZjConsumableApplyBook(ZjConsumableApplyBook zjConsumableApplyBook);

    public ResponseEntity batchDeleteUpdateZjConsumableApplyBook(List<ZjConsumableApplyBook> zjConsumableApplyBookList);

	public List<ZjConsumableApplyBook> ureportZjConsumableApplyBookList(ZjConsumableApplyBook zjConsumableApplyBook);

}

