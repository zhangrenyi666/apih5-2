package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransItemMaterialRequisition;

public interface ZxSkStockTransItemMaterialRequisitionMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransItemMaterialRequisition record);

    int insertSelective(ZxSkStockTransItemMaterialRequisition record);

    ZxSkStockTransItemMaterialRequisition selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransItemMaterialRequisition record);

    int updateByPrimaryKey(ZxSkStockTransItemMaterialRequisition record);

    List<ZxSkStockTransItemMaterialRequisition> selectByZxSkStockTransItemMaterialRequisitionList(ZxSkStockTransItemMaterialRequisition record);

    int batchDeleteUpdateZxSkStockTransItemMaterialRequisition(List<ZxSkStockTransItemMaterialRequisition> recordList, ZxSkStockTransItemMaterialRequisition record);

    int updateZxSkStockTransItemMaterialRequisitionIsOutNumber(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisitionIsOutNumber);


}

