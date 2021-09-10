package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnOverTransferItem;

public interface ZxSkTurnOverTransferItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnOverTransferItem record);

    int insertSelective(ZxSkTurnOverTransferItem record);

    ZxSkTurnOverTransferItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnOverTransferItem record);

    int updateByPrimaryKey(ZxSkTurnOverTransferItem record);

    List<ZxSkTurnOverTransferItem> selectByZxSkTurnOverTransferItemList(ZxSkTurnOverTransferItem record);

    int batchDeleteUpdateZxSkTurnOverTransferItem(List<ZxSkTurnOverTransferItem> recordList, ZxSkTurnOverTransferItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
