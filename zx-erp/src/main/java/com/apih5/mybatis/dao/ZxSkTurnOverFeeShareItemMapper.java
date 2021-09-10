package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShareItem;

public interface ZxSkTurnOverFeeShareItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnOverFeeShareItem record);

    int insertSelective(ZxSkTurnOverFeeShareItem record);

    ZxSkTurnOverFeeShareItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnOverFeeShareItem record);

    int updateByPrimaryKey(ZxSkTurnOverFeeShareItem record);

    List<ZxSkTurnOverFeeShareItem> selectByZxSkTurnOverFeeShareItemList(ZxSkTurnOverFeeShareItem record);

    int batchDeleteUpdateZxSkTurnOverFeeShareItem(List<ZxSkTurnOverFeeShareItem> recordList, ZxSkTurnOverFeeShareItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
