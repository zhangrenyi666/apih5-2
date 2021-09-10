package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessment;

public interface ZjXmJxQuarterlyAssessmentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmJxQuarterlyAssessment record);

    int insertSelective(ZjXmJxQuarterlyAssessment record);

    ZjXmJxQuarterlyAssessment selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmJxQuarterlyAssessment record);

    int updateByPrimaryKey(ZjXmJxQuarterlyAssessment record);

    List<ZjXmJxQuarterlyAssessment> selectByZjXmJxQuarterlyAssessmentList(ZjXmJxQuarterlyAssessment record);

    int batchDeleteUpdateZjXmJxQuarterlyAssessment(List<ZjXmJxQuarterlyAssessment> recordList, ZjXmJxQuarterlyAssessment record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
