package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransferDishedOut;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockTransferDishedOutMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferDishedOut record);

    int insertSelective(ZxSkStockTransferDishedOut record);

    ZxSkStockTransferDishedOut selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferDishedOut record);

    int updateByPrimaryKey(ZxSkStockTransferDishedOut record);

    List<ZxSkStockTransferDishedOut> selectByZxSkStockTransferDishedOutList(ZxSkStockTransferDishedOut record);

    int batchDeleteUpdateZxSkStockTransferDishedOut(List<ZxSkStockTransferDishedOut> recordList, ZxSkStockTransferDishedOut record);

    int checkZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut);

    int getZxSkStockTransferDishedOutCount(@Param("date") String date, @Param("orgID") String orgID);
}

