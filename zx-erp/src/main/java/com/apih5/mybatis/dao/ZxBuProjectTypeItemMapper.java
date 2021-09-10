package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuProjectTypeItem;

public interface ZxBuProjectTypeItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuProjectTypeItem record);

    int insertSelective(ZxBuProjectTypeItem record);

    ZxBuProjectTypeItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuProjectTypeItem record);

    int updateByPrimaryKey(ZxBuProjectTypeItem record);

    List<ZxBuProjectTypeItem> selectByZxBuProjectTypeItemList(ZxBuProjectTypeItem record);

    int batchDeleteUpdateZxBuProjectTypeItem(List<ZxBuProjectTypeItem> recordList, ZxBuProjectTypeItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
