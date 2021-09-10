package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlanItem;
import org.apache.ibatis.annotations.Param;

public interface ZjLzehManageTaskPlanItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehManageTaskPlanItem record);

    int insertSelective(ZjLzehManageTaskPlanItem record);

    ZjLzehManageTaskPlanItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehManageTaskPlanItem record);

    int updateByPrimaryKey(ZjLzehManageTaskPlanItem record);

    List<ZjLzehManageTaskPlanItem> selectByZjLzehManageTaskPlanItemList(ZjLzehManageTaskPlanItem record);

    int batchDeleteUpdateZjLzehManageTaskPlanItem(List<ZjLzehManageTaskPlanItem> recordList, ZjLzehManageTaskPlanItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int insertManageTaskPlanItemList (@Param("excelImportList") List<ZjLzehManageTaskPlanItem> excelImportList);
}
