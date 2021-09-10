package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRiskAnalysis;

public interface ZjTzRiskAnalysisMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRiskAnalysis record);

    int insertSelective(ZjTzRiskAnalysis record);

    ZjTzRiskAnalysis selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRiskAnalysis record);

    int updateByPrimaryKey(ZjTzRiskAnalysis record);

    List<ZjTzRiskAnalysis> selectByZjTzRiskAnalysisList(ZjTzRiskAnalysis record);

    int batchDeleteUpdateZjTzRiskAnalysis(List<ZjTzRiskAnalysis> recordList, ZjTzRiskAnalysis record);

	int batchReleaseZjTzRiskAnalysis(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList, ZjTzRiskAnalysis zjTzRules);

	int batchRecallZjTzRiskAnalysis(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList, ZjTzRiskAnalysis zjTzRules);

}

