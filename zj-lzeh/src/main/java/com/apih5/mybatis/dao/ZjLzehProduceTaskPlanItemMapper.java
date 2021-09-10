package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlanItem;
import org.apache.ibatis.annotations.Param;

public interface ZjLzehProduceTaskPlanItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehProduceTaskPlanItem record);

    int insertSelective(ZjLzehProduceTaskPlanItem record);

    ZjLzehProduceTaskPlanItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehProduceTaskPlanItem record);

    int updateByPrimaryKey(ZjLzehProduceTaskPlanItem record);

    List<ZjLzehProduceTaskPlanItem> selectByZjLzehProduceTaskPlanItemList(ZjLzehProduceTaskPlanItem record);

    int batchDeleteUpdateZjLzehProduceTaskPlanItem(List<ZjLzehProduceTaskPlanItem> recordList, ZjLzehProduceTaskPlanItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int insertProduceTaskPlanItemList(@Param("excelImportList") List<ZjLzehProduceTaskPlanItem> excelImportList);
}
