package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzContractCondition;

public interface ZjTzContractConditionMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzContractCondition record);

    int insertSelective(ZjTzContractCondition record);

    ZjTzContractCondition selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzContractCondition record);

    int updateByPrimaryKey(ZjTzContractCondition record);

    List<ZjTzContractCondition> selectByZjTzContractConditionList(ZjTzContractCondition record);

    int batchDeleteUpdateZjTzContractCondition(List<ZjTzContractCondition> recordList, ZjTzContractCondition record);

}

