package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehQualityInspectionStatistics;

public interface ZjLzehQualityInspectionStatisticsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehQualityInspectionStatistics record);

    int insertSelective(ZjLzehQualityInspectionStatistics record);

    ZjLzehQualityInspectionStatistics selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehQualityInspectionStatistics record);

    int updateByPrimaryKey(ZjLzehQualityInspectionStatistics record);

    List<ZjLzehQualityInspectionStatistics> selectByZjLzehQualityInspectionStatisticsList(ZjLzehQualityInspectionStatistics record);

    int batchDeleteUpdateZjLzehQualityInspectionStatistics(List<ZjLzehQualityInspectionStatistics> recordList, ZjLzehQualityInspectionStatistics record);

}

