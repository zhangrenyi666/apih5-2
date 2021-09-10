package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtEqContrItem;

public interface ZxCtEqContrItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtEqContrItem record);

    int insertSelective(ZxCtEqContrItem record);

    ZxCtEqContrItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtEqContrItem record);

    int updateByPrimaryKey(ZxCtEqContrItem record);

    List<ZxCtEqContrItem> selectByZxCtEqContrItemList(ZxCtEqContrItem record);

    int batchDeleteUpdateZxCtEqContrItem(List<ZxCtEqContrItem> recordList, ZxCtEqContrItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    ZxCtEqContrItem getZxCtEqContrItemContractSumTotal(String key);
}
