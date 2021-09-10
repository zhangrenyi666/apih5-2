package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaEquipResSettleItem;

public interface ZxSaEquipResSettleItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaEquipResSettleItem record);

    int insertSelective(ZxSaEquipResSettleItem record);

    ZxSaEquipResSettleItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaEquipResSettleItem record);

    int updateByPrimaryKey(ZxSaEquipResSettleItem record);

    List<ZxSaEquipResSettleItem> selectByZxSaEquipResSettleItemList(ZxSaEquipResSettleItem record);

    int batchDeleteUpdateZxSaEquipResSettleItem(List<ZxSaEquipResSettleItem> recordList, ZxSaEquipResSettleItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
