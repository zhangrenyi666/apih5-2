package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRisk;

public interface ZjTzRiskMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRisk record);

    int insertSelective(ZjTzRisk record);

    ZjTzRisk selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRisk record);

    int updateByPrimaryKey(ZjTzRisk record);

    List<ZjTzRisk> selectByZjTzRiskList(ZjTzRisk record);

    int batchDeleteUpdateZjTzRisk(List<ZjTzRisk> recordList, ZjTzRisk record);

	int batchRecallZjTzRisk(List<ZjTzRisk> zjTzRiskList, ZjTzRisk zjTzRules);

}

