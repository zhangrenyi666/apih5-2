package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaOtherEquipResSettleItem;

public interface ZxSaOtherEquipResSettleItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaOtherEquipResSettleItem record);

    int insertSelective(ZxSaOtherEquipResSettleItem record);

    ZxSaOtherEquipResSettleItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaOtherEquipResSettleItem record);

    int updateByPrimaryKey(ZxSaOtherEquipResSettleItem record);

    List<ZxSaOtherEquipResSettleItem> selectByZxSaOtherEquipResSettleItemList(ZxSaOtherEquipResSettleItem record);

    int batchDeleteUpdateZxSaOtherEquipResSettleItem(List<ZxSaOtherEquipResSettleItem> recordList, ZxSaOtherEquipResSettleItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    ZxSaOtherEquipResSettleItem selectTotalQtyAmt(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem);
}
