package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnoverCheckItem;
import com.apih5.mybatis.pojo.ZxSkTurnoverInItem;

public interface ZxSkTurnoverCheckItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnoverCheckItem record);

    int insertSelective(ZxSkTurnoverCheckItem record);

    ZxSkTurnoverCheckItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnoverCheckItem record);

    int updateByPrimaryKey(ZxSkTurnoverCheckItem record);

    List<ZxSkTurnoverCheckItem> selectByZxSkTurnoverCheckItemList(ZxSkTurnoverCheckItem record);

    int batchDeleteUpdateZxSkTurnoverCheckItem(List<ZxSkTurnoverCheckItem> recordList, ZxSkTurnoverCheckItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    ZxSkTurnoverInItem getZxSkTurnoverCheckReceiveInQty(String batchNo);
}
