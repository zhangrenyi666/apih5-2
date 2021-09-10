package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehYearPlanProgress;

public interface ZjLzehYearPlanProgressMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehYearPlanProgress record);

    int insertSelective(ZjLzehYearPlanProgress record);

    ZjLzehYearPlanProgress selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehYearPlanProgress record);

    int updateByPrimaryKey(ZjLzehYearPlanProgress record);

    List<ZjLzehYearPlanProgress> selectByZjLzehYearPlanProgressList(ZjLzehYearPlanProgress record);

    int batchDeleteUpdateZjLzehYearPlanProgress(List<ZjLzehYearPlanProgress> recordList, ZjLzehYearPlanProgress record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
