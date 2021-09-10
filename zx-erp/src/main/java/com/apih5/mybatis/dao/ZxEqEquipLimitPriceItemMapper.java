package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPriceItem;
import org.apache.ibatis.annotations.Param;

public interface ZxEqEquipLimitPriceItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipLimitPriceItem record);

    int insertSelective(ZxEqEquipLimitPriceItem record);

    ZxEqEquipLimitPriceItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipLimitPriceItem record);

    int updateByPrimaryKey(ZxEqEquipLimitPriceItem record);

    List<ZxEqEquipLimitPriceItem> selectByZxEqEquipLimitPriceItemList(ZxEqEquipLimitPriceItem record);

    int batchDeleteUpdateZxEqEquipLimitPriceItem(List<ZxEqEquipLimitPriceItem> recordList, ZxEqEquipLimitPriceItem record);

    List<ZxEqEquipLimitPriceItem> getLimitPriceForm();
}

