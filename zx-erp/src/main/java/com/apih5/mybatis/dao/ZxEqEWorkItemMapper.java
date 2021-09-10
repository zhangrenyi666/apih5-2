package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEWorkItem;

public interface ZxEqEWorkItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEWorkItem record);

    int insertSelective(ZxEqEWorkItem record);

    ZxEqEWorkItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEWorkItem record);

    int updateByPrimaryKey(ZxEqEWorkItem record);

    List<ZxEqEWorkItem> selectByZxEqEWorkItemList(ZxEqEWorkItem record);

    int batchDeleteUpdateZxEqEWorkItem(List<ZxEqEWorkItem> recordList, ZxEqEWorkItem record);

	List<ZxEqEWorkItem> ureportZxEqEWorkItemListForCar(ZxEqEWorkItem zxEqEWorkItem);

}

