package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettleItem;

public interface ZxSaOtherEquipSettleItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaOtherEquipSettleItem record);

    int insertSelective(ZxSaOtherEquipSettleItem record);

    ZxSaOtherEquipSettleItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaOtherEquipSettleItem record);

    int updateByPrimaryKey(ZxSaOtherEquipSettleItem record);

    List<ZxSaOtherEquipSettleItem> selectByZxSaOtherEquipSettleItemList(ZxSaOtherEquipSettleItem record);

    int batchDeleteUpdateZxSaOtherEquipSettleItem(List<ZxSaOtherEquipSettleItem> recordList, ZxSaOtherEquipSettleItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSaOtherEquipSettleItem> getZxSaOtherEquipSettleItemBeforeTotalList(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem);

    ZxSaOtherEquipSettleItem selectTotalAmtUpAmt(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem);

}
