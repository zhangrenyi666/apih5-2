package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtFsSideAgreementInventory;
import org.apache.ibatis.annotations.Param;

public interface ZxCtFsSideAgreementInventoryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtFsSideAgreementInventory record);

    int insertSelective(ZxCtFsSideAgreementInventory record);

    ZxCtFsSideAgreementInventory selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtFsSideAgreementInventory record);

    int updateByPrimaryKey(ZxCtFsSideAgreementInventory record);

    List<ZxCtFsSideAgreementInventory> selectByZxCtFsSideAgreementInventoryList(ZxCtFsSideAgreementInventory record);

    int batchDeleteUpdateZxCtFsSideAgreementInventory(List<ZxCtFsSideAgreementInventory> recordList, ZxCtFsSideAgreementInventory record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int batchUpdate(@Param("updateList") List<ZxCtFsSideAgreementInventory> updateList);

    ZxCtFsSideAgreementInventory selectSumAmt(@Param(value = "record") ZxCtFsSideAgreementInventory record);
}
