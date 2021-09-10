package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqToCompareAnalyseQueryPage;

public interface ZxEqToCompareAnalyseQueryPageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqToCompareAnalyseQueryPage record);

    int insertSelective(ZxEqToCompareAnalyseQueryPage record);

    ZxEqToCompareAnalyseQueryPage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqToCompareAnalyseQueryPage record);

    int updateByPrimaryKey(ZxEqToCompareAnalyseQueryPage record);

    List<ZxEqToCompareAnalyseQueryPage> selectByZxEqToCompareAnalyseQueryPageList(ZxEqToCompareAnalyseQueryPage record);

    int batchDeleteUpdateZxEqToCompareAnalyseQueryPage(List<ZxEqToCompareAnalyseQueryPage> recordList, ZxEqToCompareAnalyseQueryPage record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqToCompareAnalyseQueryPage> selectZxEqToCompareAnalyseQueryPage(ZxEqToCompareAnalyseQueryPage record);
    
    List<ZxEqToCompareAnalyseQueryPage> selectZxEqToCompareAnalyseQueryPeriod(ZxEqToCompareAnalyseQueryPage record);
}
