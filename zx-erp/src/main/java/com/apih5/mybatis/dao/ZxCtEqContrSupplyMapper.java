package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtEqContrSupply;

public interface ZxCtEqContrSupplyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtEqContrSupply record);

    int insertSelective(ZxCtEqContrSupply record);

    ZxCtEqContrSupply selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtEqContrSupply record);

    int updateByPrimaryKey(ZxCtEqContrSupply record);

    List<ZxCtEqContrSupply> selectByZxCtEqContrSupplyList(ZxCtEqContrSupply record);

    int batchDeleteUpdateZxCtEqContrSupply(List<ZxCtEqContrSupply> recordList, ZxCtEqContrSupply record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
