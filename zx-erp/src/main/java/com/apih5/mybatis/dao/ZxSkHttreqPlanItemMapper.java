package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkHttreqPlanItem;

public interface ZxSkHttreqPlanItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkHttreqPlanItem record);

    int insertSelective(ZxSkHttreqPlanItem record);

    ZxSkHttreqPlanItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkHttreqPlanItem record);

    int updateByPrimaryKey(ZxSkHttreqPlanItem record);

    List<ZxSkHttreqPlanItem> selectByZxSkHttreqPlanItemList(ZxSkHttreqPlanItem record);

    int batchDeleteUpdateZxSkHttreqPlanItem(List<ZxSkHttreqPlanItem> recordList, ZxSkHttreqPlanItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
