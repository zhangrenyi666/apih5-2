package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlan;

public interface ZjLzehProduceTaskPlanMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehProduceTaskPlan record);

    int insertSelective(ZjLzehProduceTaskPlan record);

    ZjLzehProduceTaskPlan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehProduceTaskPlan record);

    int updateByPrimaryKey(ZjLzehProduceTaskPlan record);

    List<ZjLzehProduceTaskPlan> selectByZjLzehProduceTaskPlanList(ZjLzehProduceTaskPlan record);

    int batchDeleteUpdateZjLzehProduceTaskPlan(List<ZjLzehProduceTaskPlan> recordList, ZjLzehProduceTaskPlan record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
