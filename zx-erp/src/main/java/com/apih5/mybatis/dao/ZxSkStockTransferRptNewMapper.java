package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransferRptNew;

public interface ZxSkStockTransferRptNewMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferRptNew record);

    int insertSelective(ZxSkStockTransferRptNew record);

    ZxSkStockTransferRptNew selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferRptNew record);

    int updateByPrimaryKey(ZxSkStockTransferRptNew record);

    List<ZxSkStockTransferRptNew> selectByZxSkStockTransferRptNewList(ZxSkStockTransferRptNew record);

    int batchDeleteUpdateZxSkStockTransferRptNew(List<ZxSkStockTransferRptNew> recordList, ZxSkStockTransferRptNew record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkStockTransferRptNew> selectZxSkStockTransferRptNew(ZxSkStockTransferRptNew record);
}
