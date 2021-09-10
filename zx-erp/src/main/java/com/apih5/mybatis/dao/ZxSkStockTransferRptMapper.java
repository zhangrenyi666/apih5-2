package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransferRpt;

public interface ZxSkStockTransferRptMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferRpt record);

    int insertSelective(ZxSkStockTransferRpt record);

    ZxSkStockTransferRpt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferRpt record);

    int updateByPrimaryKey(ZxSkStockTransferRpt record);

    List<ZxSkStockTransferRpt> selectByZxSkStockTransferRptList(ZxSkStockTransferRpt record);

    int batchDeleteUpdateZxSkStockTransferRpt(List<ZxSkStockTransferRpt> recordList, ZxSkStockTransferRpt record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkStockTransferRpt> selectZxSkStockTransferRpt(ZxSkStockTransferRpt record);
}
