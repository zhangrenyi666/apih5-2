package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjConsumableInventory;

public interface ZjConsumableInventoryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjConsumableInventory record);

    int insertSelective(ZjConsumableInventory record);

    ZjConsumableInventory selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjConsumableInventory record);

    int updateByPrimaryKey(ZjConsumableInventory record);

    List<ZjConsumableInventory> selectByZjConsumableInventoryList(ZjConsumableInventory record);

    int batchDeleteUpdateZjConsumableInventory(List<ZjConsumableInventory> recordList, ZjConsumableInventory record);

}

