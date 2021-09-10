package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxPuMMReqPlan;

public interface ZxPuMMReqPlanMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxPuMMReqPlan record);

    int insertSelective(ZxPuMMReqPlan record);

    ZxPuMMReqPlan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxPuMMReqPlan record);

    int updateByPrimaryKey(ZxPuMMReqPlan record);

    List<ZxPuMMReqPlan> selectByZxPuMMReqPlanList(ZxPuMMReqPlan record);

    int batchDeleteUpdateZxPuMMReqPlan(List<ZxPuMMReqPlan> recordList, ZxPuMMReqPlan record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxPuMMReqPlan> selectZxPuMMReqPlan(ZxPuMMReqPlan record);
}
