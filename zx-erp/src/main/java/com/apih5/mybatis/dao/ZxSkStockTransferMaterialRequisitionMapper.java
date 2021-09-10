package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZxSkStockTransItemMaterialRequisition;
import com.apih5.mybatis.pojo.ZxSkStockTransItemWithdrawal;
import com.apih5.mybatis.pojo.ZxSkStockTransferMaterialRequisition;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockTransferMaterialRequisitionMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferMaterialRequisition record);

    int insertSelective(ZxSkStockTransferMaterialRequisition record);

    ZxSkStockTransferMaterialRequisition selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferMaterialRequisition record);

    int updateByPrimaryKey(ZxSkStockTransferMaterialRequisition record);

    List<ZxSkStockTransferMaterialRequisition> selectByZxSkStockTransferMaterialRequisitionList(ZxSkStockTransferMaterialRequisition record);

    int batchDeleteUpdateZxSkStockTransferMaterialRequisition(List<ZxSkStockTransferMaterialRequisition> recordList, ZxSkStockTransferMaterialRequisition record);

    int checkZxSkStockTransferMaterialRequisition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition);

    List<ZxSkStockTransferMaterialRequisition> selectByZxSkStockTransferMaterialRequisitionInOrgNameList(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition);

    List<ZxSkStockTransferMaterialRequisition> selectByZxSkStockTransferMaterialRequisitionCbsNameList(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition);

    List<ZxSkStockTransferMaterialRequisition> selectByZxSkStockTransferMaterialRequisitionResourceNameList(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition);

    List<ZxSkStockTransItemMaterialRequisition> selectByZxSkStockTransferMaterialRequisitionResNameList(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition);

    @MapKey("mainIdAndItemId")
    Map<String,ZxSkStockTransItemMaterialRequisition> getZxSkStockTransferMaterialRequisitionResName(List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList, ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition);

    int getZxSkStockTransferMaterialRequisitionCount(@Param("date") String date,@Param("orgID") String orgID);

}

