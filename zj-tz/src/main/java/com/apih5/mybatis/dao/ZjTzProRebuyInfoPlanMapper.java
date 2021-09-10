package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProRebuyInfoPlan;

public interface ZjTzProRebuyInfoPlanMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProRebuyInfoPlan record);

    int insertSelective(ZjTzProRebuyInfoPlan record);

    ZjTzProRebuyInfoPlan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProRebuyInfoPlan record);

    int updateByPrimaryKey(ZjTzProRebuyInfoPlan record);

    List<ZjTzProRebuyInfoPlan> selectByZjTzProRebuyInfoPlanList(ZjTzProRebuyInfoPlan record);

    int batchDeleteUpdateZjTzProRebuyInfoPlan(List<ZjTzProRebuyInfoPlan> recordList, ZjTzProRebuyInfoPlan record);

}

