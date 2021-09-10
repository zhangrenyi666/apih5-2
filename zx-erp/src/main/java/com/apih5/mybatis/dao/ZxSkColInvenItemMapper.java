package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkColInvenItem;

public interface ZxSkColInvenItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkColInvenItem record);

    int insertSelective(ZxSkColInvenItem record);

    ZxSkColInvenItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkColInvenItem record);

    int updateByPrimaryKey(ZxSkColInvenItem record);

    List<ZxSkColInvenItem> selectByZxSkColInvenItemList(ZxSkColInvenItem record);

    int batchDeleteUpdateZxSkColInvenItem(List<ZxSkColInvenItem> recordList, ZxSkColInvenItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
