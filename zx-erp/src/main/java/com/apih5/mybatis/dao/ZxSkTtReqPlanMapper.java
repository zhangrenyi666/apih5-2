package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTtReqPlan;

public interface ZxSkTtReqPlanMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTtReqPlan record);

    int insertSelective(ZxSkTtReqPlan record);

    ZxSkTtReqPlan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTtReqPlan record);

    int updateByPrimaryKey(ZxSkTtReqPlan record);

    List<ZxSkTtReqPlan> selectByZxSkTtReqPlanList(ZxSkTtReqPlan record);

    int batchDeleteUpdateZxSkTtReqPlan(List<ZxSkTtReqPlan> recordList, ZxSkTtReqPlan record);

    int checkZxSkTtReqPlanList(ZxSkTtReqPlan zxSkTtReqPlan);

}

