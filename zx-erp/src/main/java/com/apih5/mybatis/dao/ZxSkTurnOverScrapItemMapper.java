package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnOverScrapItem;

public interface ZxSkTurnOverScrapItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnOverScrapItem record);

    int insertSelective(ZxSkTurnOverScrapItem record);

    ZxSkTurnOverScrapItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnOverScrapItem record);

    int updateByPrimaryKey(ZxSkTurnOverScrapItem record);

    List<ZxSkTurnOverScrapItem> selectByZxSkTurnOverScrapItemList(ZxSkTurnOverScrapItem record);

    int batchDeleteUpdateZxSkTurnOverScrapItem(List<ZxSkTurnOverScrapItem> recordList, ZxSkTurnOverScrapItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
