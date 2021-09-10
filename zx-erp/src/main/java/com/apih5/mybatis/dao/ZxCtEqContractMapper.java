package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtEqContract;

public interface ZxCtEqContractMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtEqContract record);

    int insertSelective(ZxCtEqContract record);

    ZxCtEqContract selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtEqContract record);

    int updateByPrimaryKey(ZxCtEqContract record);

    List<ZxCtEqContract> selectByZxCtEqContractList(ZxCtEqContract record);

    int batchDeleteUpdateZxCtEqContract(List<ZxCtEqContract> recordList, ZxCtEqContract record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
