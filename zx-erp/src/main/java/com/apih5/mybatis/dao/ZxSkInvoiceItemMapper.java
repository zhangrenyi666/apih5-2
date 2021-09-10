package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkInvoiceItem;

public interface ZxSkInvoiceItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkInvoiceItem record);

    int insertSelective(ZxSkInvoiceItem record);

    ZxSkInvoiceItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkInvoiceItem record);

    int updateByPrimaryKey(ZxSkInvoiceItem record);

    List<ZxSkInvoiceItem> selectByZxSkInvoiceItemList(ZxSkInvoiceItem record);

    int batchDeleteUpdateZxSkInvoiceItem(List<ZxSkInvoiceItem> recordList, ZxSkInvoiceItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
