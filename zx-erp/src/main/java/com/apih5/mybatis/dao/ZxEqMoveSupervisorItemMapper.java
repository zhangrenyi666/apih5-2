package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqMoveSupervisorItem;

public interface ZxEqMoveSupervisorItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqMoveSupervisorItem record);

    int insertSelective(ZxEqMoveSupervisorItem record);

    ZxEqMoveSupervisorItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqMoveSupervisorItem record);

    int updateByPrimaryKey(ZxEqMoveSupervisorItem record);

    List<ZxEqMoveSupervisorItem> selectByZxEqMoveSupervisorItemList(ZxEqMoveSupervisorItem record);

    int batchDeleteUpdateZxEqMoveSupervisorItem(List<ZxEqMoveSupervisorItem> recordList, ZxEqMoveSupervisorItem record);

	List<ZxEqMoveSupervisorItem> getZxEqMoveSupervisorItemListForTab(ZxEqMoveSupervisorItem record);

}

