package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaProjectSetItem;

public interface ZxSaProjectSetItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectSetItem record);

    int insertSelective(ZxSaProjectSetItem record);

    ZxSaProjectSetItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectSetItem record);

    int updateByPrimaryKey(ZxSaProjectSetItem record);

    List<ZxSaProjectSetItem> selectByZxSaProjectSetItemList(ZxSaProjectSetItem record);

    int batchDeleteUpdateZxSaProjectSetItem(List<ZxSaProjectSetItem> recordList, ZxSaProjectSetItem record);

}

