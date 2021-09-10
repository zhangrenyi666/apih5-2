package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPppTerm;

public interface ZjTzPppTermMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPppTerm record);

    int insertSelective(ZjTzPppTerm record);

    ZjTzPppTerm selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPppTerm record);

    int updateByPrimaryKey(ZjTzPppTerm record);

    List<ZjTzPppTerm> selectByZjTzPppTermList(ZjTzPppTerm record);

    int batchDeleteUpdateZjTzPppTerm(List<ZjTzPppTerm> recordList, ZjTzPppTerm record);

	int batchRecallZjTzPppTerm(List<ZjTzPppTerm> zjTzPppTermList, ZjTzPppTerm zjTzRules);

}

