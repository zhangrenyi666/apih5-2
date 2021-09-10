package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockFee;

public interface ZxSkStockFeeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockFee record);

    int insertSelective(ZxSkStockFee record);

    ZxSkStockFee selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockFee record);

    int updateByPrimaryKey(ZxSkStockFee record);

    List<ZxSkStockFee> selectByZxSkStockFeeList(ZxSkStockFee record);

    int batchDeleteUpdateZxSkStockFee(List<ZxSkStockFee> recordList, ZxSkStockFee record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
