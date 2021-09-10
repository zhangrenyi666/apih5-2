package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzComprehensiveSup;

public interface ZjTzComprehensiveSupMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzComprehensiveSup record);

    int insertSelective(ZjTzComprehensiveSup record);

    ZjTzComprehensiveSup selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzComprehensiveSup record);

    int updateByPrimaryKey(ZjTzComprehensiveSup record);

    List<ZjTzComprehensiveSup> selectByZjTzComprehensiveSupList(ZjTzComprehensiveSup record);

    int batchDeleteUpdateZjTzComprehensiveSup(List<ZjTzComprehensiveSup> recordList, ZjTzComprehensiveSup record);

	int batchRecallZjTzComprehensiveSup(List<ZjTzComprehensiveSup> zjTzComprehensiveSupList, ZjTzComprehensiveSup zjTzRules);

}

