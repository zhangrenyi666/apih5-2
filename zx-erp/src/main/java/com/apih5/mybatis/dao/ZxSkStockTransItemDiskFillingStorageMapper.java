package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransItemDiskFillingStorage;
import com.apih5.mybatis.pojo.ZxSkStockTransferDiskFillingStorage;

public interface ZxSkStockTransItemDiskFillingStorageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransItemDiskFillingStorage record);

    int insertSelective(ZxSkStockTransItemDiskFillingStorage record);

    ZxSkStockTransItemDiskFillingStorage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransItemDiskFillingStorage record);

    int updateByPrimaryKey(ZxSkStockTransItemDiskFillingStorage record);

    List<ZxSkStockTransItemDiskFillingStorage> selectByZxSkStockTransItemDiskFillingStorageList(ZxSkStockTransItemDiskFillingStorage record);

    int batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(List<ZxSkStockTransItemDiskFillingStorage> recordList, ZxSkStockTransItemDiskFillingStorage record);

    int checkZxSkStockTransferDiskFillingStorage(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage);

}

