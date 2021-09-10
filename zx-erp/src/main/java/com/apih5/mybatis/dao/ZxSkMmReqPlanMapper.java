package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkMmReqPlan;

public interface ZxSkMmReqPlanMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkMmReqPlan record);

    int insertSelective(ZxSkMmReqPlan record);

    ZxSkMmReqPlan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkMmReqPlan record);

    int updateByPrimaryKey(ZxSkMmReqPlan record);

    List<ZxSkMmReqPlan> selectByZxSkMmReqPlanList(ZxSkMmReqPlan record);

    int batchDeleteUpdateZxSkMmReqPlan(List<ZxSkMmReqPlan> recordList, ZxSkMmReqPlan record);

}

