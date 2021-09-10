package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZxSkInvoice;
import com.apih5.mybatis.pojo.ZxSkInvoiceItem;
import com.apih5.mybatis.pojo.ZxSkStockTransItemReceiving;
import com.apih5.mybatis.pojo.ZxSkStockTransferReceiving;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

public interface ZxSkInvoiceMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkInvoice record);

    int insertSelective(ZxSkInvoice record);

    ZxSkInvoice selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkInvoice record);

    int updateByPrimaryKey(ZxSkInvoice record);

    List<ZxSkInvoice> selectByZxSkInvoiceList(ZxSkInvoice record);

    int batchDeleteUpdateZxSkInvoice(List<ZxSkInvoice> recordList, ZxSkInvoice record);

    List<ZxSkStockTransferReceiving> getZxSkInvoiceReceivableOrder(ZxSkInvoice zxSkInvoice);

    List<ZxSkStockTransferReceiving> getZxSkInvoiceOutOrg(ZxSkInvoice zxSkInvoice);

    List<ZxSkStockTransferReceiving> getZxSkInvoiceResource(ZxSkInvoice zxSkInvoice);

    List<ZxSkStockTransItemReceiving> getZxSkInvoiceQty(List<ZxSkInvoiceItem> zxSkInvoiceItemList, ZxSkInvoice zxSkInvoice);

    int checkZxSkInvoice(ZxSkInvoice zxSkInvoice);

    int getZxSkInvoiceCount(@Param("date") String date,@Param("orgID") String orgID);


    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
