package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockFeeItem;

public interface ZxSkStockFeeItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockFeeItem record);

    int insertSelective(ZxSkStockFeeItem record);

    ZxSkStockFeeItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockFeeItem record);

    int updateByPrimaryKey(ZxSkStockFeeItem record);

    List<ZxSkStockFeeItem> selectByZxSkStockFeeItemList(ZxSkStockFeeItem record);

    int batchDeleteUpdateZxSkStockFeeItem(List<ZxSkStockFeeItem> recordList, ZxSkStockFeeItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
