package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransferDiskFillingStorage;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockTransferDiskFillingStorageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferDiskFillingStorage record);

    int insertSelective(ZxSkStockTransferDiskFillingStorage record);

    ZxSkStockTransferDiskFillingStorage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferDiskFillingStorage record);

    int updateByPrimaryKey(ZxSkStockTransferDiskFillingStorage record);

    List<ZxSkStockTransferDiskFillingStorage> selectByZxSkStockTransferDiskFillingStorageList(ZxSkStockTransferDiskFillingStorage record);

    int batchDeleteUpdateZxSkStockTransferDiskFillingStorage(List<ZxSkStockTransferDiskFillingStorage> recordList, ZxSkStockTransferDiskFillingStorage record);

    int checkZxSkStockTransferDiskFillingStorage(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage);

    int getzxSkStockTransferDiskFillingStorageCount(@Param("date") String date,@Param("orgID") String orgID);

}

