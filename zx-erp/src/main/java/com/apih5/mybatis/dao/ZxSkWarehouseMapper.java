package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkWarehouse;

public interface ZxSkWarehouseMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkWarehouse record);

    int insertSelective(ZxSkWarehouse record);

    ZxSkWarehouse selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkWarehouse record);

    int updateByPrimaryKey(ZxSkWarehouse record);

    List<ZxSkWarehouse> selectByZxSkWarehouseList(ZxSkWarehouse record);

    int batchDeleteUpdateZxSkWarehouse(List<ZxSkWarehouse> recordList, ZxSkWarehouse record);

}

