package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesMarginRatio;

public interface ZxCtSuppliesMarginRatioMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesMarginRatio record);

    int insertSelective(ZxCtSuppliesMarginRatio record);

    ZxCtSuppliesMarginRatio selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesMarginRatio record);

    int updateByPrimaryKey(ZxCtSuppliesMarginRatio record);

    List<ZxCtSuppliesMarginRatio> selectByZxCtSuppliesMarginRatioList(ZxCtSuppliesMarginRatio record);

    int batchDeleteUpdateZxCtSuppliesMarginRatio(List<ZxCtSuppliesMarginRatio> recordList, ZxCtSuppliesMarginRatio record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
