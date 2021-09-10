package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZxSkStockTransItemMaterialRequisition;
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;
import com.apih5.mybatis.pojo.ZxSkStockTransferSalesReturn;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockTransferSalesReturnMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferSalesReturn record);

    int insertSelective(ZxSkStockTransferSalesReturn record);

    ZxSkStockTransferSalesReturn selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferSalesReturn record);

    int updateByPrimaryKey(ZxSkStockTransferSalesReturn record);

    List<ZxSkStockTransferSalesReturn> selectByZxSkStockTransferSalesReturnList(ZxSkStockTransferSalesReturn record);

    int batchDeleteUpdateZxSkStockTransferSalesReturn(List<ZxSkStockTransferSalesReturn> recordList, ZxSkStockTransferSalesReturn record);

    List<ZxSkStockTransferSalesReturn> selectByZxSkStockTransferSalesReturnOutOrgNameList(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    List<ZxSkStockTransferSalesReturn> selectByZxSkStockTransferSalesReturnResourceNameList(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    List<ZxSkStockTransItemSalesReturn> selectByZxSkStockTransferSalesReturnResNameList(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    @MapKey("mainIdAndItemId")
    Map<String, ZxSkStockTransItemSalesReturn> getZxSkStockTransferSalesReturnResName(@Param("zxSkStockTransItemSalesReturnList") List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList);

    int checkZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    int getZxSkStockTransferSalesReturnCount(@Param("date") String date,@Param("orgID") String orgID);


}

