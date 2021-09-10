package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqPurAccessories;

public interface ZxEqPurAccessoriesMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqPurAccessories record);

    int insertSelective(ZxEqPurAccessories record);

    ZxEqPurAccessories selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqPurAccessories record);

    int updateByPrimaryKey(ZxEqPurAccessories record);

    List<ZxEqPurAccessories> selectByZxEqPurAccessoriesList(ZxEqPurAccessories record);

    int batchDeleteUpdateZxEqPurAccessories(List<ZxEqPurAccessories> recordList, ZxEqPurAccessories record);

}

