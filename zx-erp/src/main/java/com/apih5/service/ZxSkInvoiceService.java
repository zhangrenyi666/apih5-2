package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkInvoice;

public interface ZxSkInvoiceService {

    public ResponseEntity getZxSkInvoiceListByCondition(ZxSkInvoice zxSkInvoice);

    public ResponseEntity getZxSkInvoiceDetail(ZxSkInvoice zxSkInvoice);

    public ResponseEntity saveZxSkInvoice(ZxSkInvoice zxSkInvoice);

    public ResponseEntity updateZxSkInvoice(ZxSkInvoice zxSkInvoice);

    public ResponseEntity batchDeleteUpdateZxSkInvoice(List<ZxSkInvoice> zxSkInvoiceList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkInvoiceNo(ZxSkInvoice zxSkInvoice);

    public ResponseEntity getZxSkInvoiceReceivableOrder(ZxSkInvoice zxSkInvoice);

    public ResponseEntity checkZxSkInvoice(ZxSkInvoice zxSkInvoice);

    public ResponseEntity getZxSkInvoiceOutOrg(ZxSkInvoice zxSkInvoice);

    public ResponseEntity getZxSkInvoiceResource(ZxSkInvoice zxSkInvoice);
}
