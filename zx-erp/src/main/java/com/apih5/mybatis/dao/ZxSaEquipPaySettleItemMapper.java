package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettleItem;

public interface ZxSaEquipPaySettleItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaEquipPaySettleItem record);

    int insertSelective(ZxSaEquipPaySettleItem record);

    ZxSaEquipPaySettleItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaEquipPaySettleItem record);

    int updateByPrimaryKey(ZxSaEquipPaySettleItem record);

    List<ZxSaEquipPaySettleItem> selectByZxSaEquipPaySettleItemList(ZxSaEquipPaySettleItem record);

    int batchDeleteUpdateZxSaEquipPaySettleItem(List<ZxSaEquipPaySettleItem> recordList, ZxSaEquipPaySettleItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
