package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkReturnsItem;

public interface ZxSkReturnsItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkReturnsItem record);

    int insertSelective(ZxSkReturnsItem record);

    ZxSkReturnsItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkReturnsItem record);

    int updateByPrimaryKey(ZxSkReturnsItem record);

    List<ZxSkReturnsItem> selectByZxSkReturnsItemList(ZxSkReturnsItem record);

    int batchDeleteUpdateZxSkReturnsItem(List<ZxSkReturnsItem> recordList, ZxSkReturnsItem record);

    void updateSettlementStatusByPrimaryKey(String id);


    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
