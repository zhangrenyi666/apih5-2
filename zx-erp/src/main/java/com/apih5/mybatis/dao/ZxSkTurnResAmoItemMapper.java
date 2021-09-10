package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnResAmoItem;

public interface ZxSkTurnResAmoItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnResAmoItem record);

    int insertSelective(ZxSkTurnResAmoItem record);

    ZxSkTurnResAmoItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnResAmoItem record);

    int updateByPrimaryKey(ZxSkTurnResAmoItem record);

    List<ZxSkTurnResAmoItem> selectByZxSkTurnResAmoItemList(ZxSkTurnResAmoItem record);

    int batchDeleteUpdateZxSkTurnResAmoItem(List<ZxSkTurnResAmoItem> recordList, ZxSkTurnResAmoItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkTurnResAmoItem> selectZxSkTurnResAmoItem(ZxSkTurnResAmoItem record);
}
