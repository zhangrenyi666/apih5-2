package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkInvoiceItem;

public interface ZxSkInvoiceItemService {

    public ResponseEntity getZxSkInvoiceItemListByCondition(ZxSkInvoiceItem zxSkInvoiceItem);

    public ResponseEntity getZxSkInvoiceItemDetail(ZxSkInvoiceItem zxSkInvoiceItem);

    public ResponseEntity saveZxSkInvoiceItem(ZxSkInvoiceItem zxSkInvoiceItem);

    public ResponseEntity updateZxSkInvoiceItem(ZxSkInvoiceItem zxSkInvoiceItem);

    public ResponseEntity batchDeleteUpdateZxSkInvoiceItem(List<ZxSkInvoiceItem> zxSkInvoiceItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
