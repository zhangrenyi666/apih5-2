package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxSkTurnoverOut;
import com.apih5.mybatis.pojo.ZxSkTurnoverOutItem;

public interface ZxSkTurnoverOutItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnoverOutItem record);

    int insertSelective(ZxSkTurnoverOutItem record);

    ZxSkTurnoverOutItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnoverOutItem record);

    int updateByPrimaryKey(ZxSkTurnoverOutItem record);

    List<ZxSkTurnoverOutItem> selectByZxSkTurnoverOutItemList(ZxSkTurnoverOutItem record);

    int batchDeleteUpdateZxSkTurnoverOutItem(List<ZxSkTurnoverOutItem> recordList, ZxSkTurnoverOutItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓


}
