package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehMonthPlanProgress;

public interface ZjLzehMonthPlanProgressMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehMonthPlanProgress record);

    int insertSelective(ZjLzehMonthPlanProgress record);

    ZjLzehMonthPlanProgress selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehMonthPlanProgress record);

    int updateByPrimaryKey(ZjLzehMonthPlanProgress record);

    List<ZjLzehMonthPlanProgress> selectByZjLzehMonthPlanProgressList(ZjLzehMonthPlanProgress record);

    int batchDeleteUpdateZjLzehMonthPlanProgress(List<ZjLzehMonthPlanProgress> recordList, ZjLzehMonthPlanProgress record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
