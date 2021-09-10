package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkMmReqPlanItem;

public interface ZxSkMmReqPlanItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkMmReqPlanItem record);

    int insertSelective(ZxSkMmReqPlanItem record);

    ZxSkMmReqPlanItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkMmReqPlanItem record);

    int updateByPrimaryKey(ZxSkMmReqPlanItem record);

    List<ZxSkMmReqPlanItem> selectByZxSkMmReqPlanItemList(ZxSkMmReqPlanItem record);

    int batchDeleteUpdateZxSkMmReqPlanItem(List<ZxSkMmReqPlanItem> recordList, ZxSkMmReqPlanItem record);

}

