package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzAnnualAssess;

public interface ZjTzAnnualAssessMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzAnnualAssess record);

    int insertSelective(ZjTzAnnualAssess record);

    ZjTzAnnualAssess selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzAnnualAssess record);

    int updateByPrimaryKey(ZjTzAnnualAssess record);

    List<ZjTzAnnualAssess> selectByZjTzAnnualAssessList(ZjTzAnnualAssess record);

    int batchDeleteUpdateZjTzAnnualAssess(List<ZjTzAnnualAssess> recordList, ZjTzAnnualAssess record);

	int batchReleaseZjTzAnnualAssess(List<ZjTzAnnualAssess> zjTzAnnualAssessList, ZjTzAnnualAssess zjTzRules);

	int batchRecallZjTzAnnualAssess(List<ZjTzAnnualAssess> zjTzAnnualAssessList, ZjTzAnnualAssess zjTzRules);

}

