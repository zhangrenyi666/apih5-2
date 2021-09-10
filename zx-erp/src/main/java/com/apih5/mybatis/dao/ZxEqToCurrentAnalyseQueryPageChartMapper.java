package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqToCurrentAnalyseQueryPageChart;

public interface ZxEqToCurrentAnalyseQueryPageChartMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqToCurrentAnalyseQueryPageChart record);

    int insertSelective(ZxEqToCurrentAnalyseQueryPageChart record);

    ZxEqToCurrentAnalyseQueryPageChart selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqToCurrentAnalyseQueryPageChart record);

    int updateByPrimaryKey(ZxEqToCurrentAnalyseQueryPageChart record);

    List<ZxEqToCurrentAnalyseQueryPageChart> selectByZxEqToCurrentAnalyseQueryPageChartList(ZxEqToCurrentAnalyseQueryPageChart record);

    int batchDeleteUpdateZxEqToCurrentAnalyseQueryPageChart(List<ZxEqToCurrentAnalyseQueryPageChart> recordList, ZxEqToCurrentAnalyseQueryPageChart record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqToCurrentAnalyseQueryPageChart> selectZxEqToCurrentAnalyseQueryPageChart(ZxEqToCurrentAnalyseQueryPageChart record);
    
    List<ZxEqToCurrentAnalyseQueryPageChart> selectZxEqToCurrentAnalyseQueryPage(ZxEqToCurrentAnalyseQueryPageChart record);
}
