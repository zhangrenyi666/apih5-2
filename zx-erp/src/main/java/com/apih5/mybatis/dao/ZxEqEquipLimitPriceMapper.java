package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPrice;

public interface ZxEqEquipLimitPriceMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipLimitPrice record);

    int insertSelective(ZxEqEquipLimitPrice record);

    ZxEqEquipLimitPrice selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipLimitPrice record);

    int updateByPrimaryKey(ZxEqEquipLimitPrice record);

    List<ZxEqEquipLimitPrice> selectByZxEqEquipLimitPriceList(ZxEqEquipLimitPrice record);

    int batchDeleteUpdateZxEqEquipLimitPrice(List<ZxEqEquipLimitPrice> recordList, ZxEqEquipLimitPrice record);

	List<ZxEqEquipLimitPrice> selectZxEqEquipLimitPriceVO(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

	List<ZxEqEquipLimitPrice> getZxEqEquipLimitPriceListLess(ZxEqEquipLimitPrice price);

}

