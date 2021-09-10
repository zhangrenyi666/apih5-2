package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTtReqPlanItem;

public interface ZxSkTtReqPlanItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTtReqPlanItem record);

    int insertSelective(ZxSkTtReqPlanItem record);

    ZxSkTtReqPlanItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTtReqPlanItem record);

    int updateByPrimaryKey(ZxSkTtReqPlanItem record);

    List<ZxSkTtReqPlanItem> selectByZxSkTtReqPlanItemList(ZxSkTtReqPlanItem record);

    int batchDeleteUpdateZxSkTtReqPlanItem(List<ZxSkTtReqPlanItem> recordList, ZxSkTtReqPlanItem record);

}

