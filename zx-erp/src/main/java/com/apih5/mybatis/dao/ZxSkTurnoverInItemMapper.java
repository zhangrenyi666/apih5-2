package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnoverInItem;

public interface ZxSkTurnoverInItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnoverInItem record);

    int insertSelective(ZxSkTurnoverInItem record);

    ZxSkTurnoverInItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnoverInItem record);

    int updateByPrimaryKey(ZxSkTurnoverInItem record);

    List<ZxSkTurnoverInItem> selectByZxSkTurnoverInItemList(ZxSkTurnoverInItem record);

    int batchDeleteUpdateZxSkTurnoverInItem(List<ZxSkTurnoverInItem> recordList, ZxSkTurnoverInItem record);

    void updateSettlementStatusByPrimaryKey(String id);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
