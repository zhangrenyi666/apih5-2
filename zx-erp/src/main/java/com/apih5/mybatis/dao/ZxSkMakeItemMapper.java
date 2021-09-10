package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkMakeItem;

public interface ZxSkMakeItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkMakeItem record);

    int insertSelective(ZxSkMakeItem record);

    ZxSkMakeItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkMakeItem record);

    int updateByPrimaryKey(ZxSkMakeItem record);

    List<ZxSkMakeItem> selectByZxSkMakeItemList(ZxSkMakeItem record);

    int batchDeleteUpdateZxSkMakeItem(List<ZxSkMakeItem> recordList, ZxSkMakeItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
