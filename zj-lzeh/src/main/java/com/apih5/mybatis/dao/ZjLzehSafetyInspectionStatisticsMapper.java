package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehSafetyInspectionStatistics;

public interface ZjLzehSafetyInspectionStatisticsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehSafetyInspectionStatistics record);

    int insertSelective(ZjLzehSafetyInspectionStatistics record);

    ZjLzehSafetyInspectionStatistics selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehSafetyInspectionStatistics record);

    int updateByPrimaryKey(ZjLzehSafetyInspectionStatistics record);

    List<ZjLzehSafetyInspectionStatistics> selectByZjLzehSafetyInspectionStatisticsList(ZjLzehSafetyInspectionStatistics record);

    int batchDeleteUpdateZjLzehSafetyInspectionStatistics(List<ZjLzehSafetyInspectionStatistics> recordList, ZjLzehSafetyInspectionStatistics record);

}

