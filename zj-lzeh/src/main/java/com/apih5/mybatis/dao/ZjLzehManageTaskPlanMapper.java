package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlan;

public interface ZjLzehManageTaskPlanMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehManageTaskPlan record);

    int insertSelective(ZjLzehManageTaskPlan record);

    ZjLzehManageTaskPlan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehManageTaskPlan record);

    int updateByPrimaryKey(ZjLzehManageTaskPlan record);

    List<ZjLzehManageTaskPlan> selectByZjLzehManageTaskPlanList(ZjLzehManageTaskPlan record);

    int batchDeleteUpdateZjLzehManageTaskPlan(List<ZjLzehManageTaskPlan> recordList, ZjLzehManageTaskPlan record);

	ZjLzehManageTaskPlan getZjLzehTaskNum(ZjLzehManageTaskPlan zjLzehManageTaskPlan);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
