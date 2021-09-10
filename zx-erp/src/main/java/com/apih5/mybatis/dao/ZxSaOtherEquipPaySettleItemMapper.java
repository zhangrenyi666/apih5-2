package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettleItem;

public interface ZxSaOtherEquipPaySettleItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaOtherEquipPaySettleItem record);

    int insertSelective(ZxSaOtherEquipPaySettleItem record);

    ZxSaOtherEquipPaySettleItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaOtherEquipPaySettleItem record);

    int updateByPrimaryKey(ZxSaOtherEquipPaySettleItem record);

    List<ZxSaOtherEquipPaySettleItem> selectByZxSaOtherEquipPaySettleItemList(ZxSaOtherEquipPaySettleItem record);

    int batchDeleteUpdateZxSaOtherEquipPaySettleItem(List<ZxSaOtherEquipPaySettleItem> recordList, ZxSaOtherEquipPaySettleItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZxSaOtherEquipPaySettleItem selectUpQtyAndAmt(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem);

    List<ZxSaOtherEquipPaySettleItem> repeatOtherEquipPaySettleItemCheck(ZxSaOtherEquipPaySettleItem record);

}
