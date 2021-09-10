package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaProjectFinishItem;

public interface ZxSaProjectFinishItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectFinishItem record);

    int insertSelective(ZxSaProjectFinishItem record);

    ZxSaProjectFinishItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectFinishItem record);

    int updateByPrimaryKey(ZxSaProjectFinishItem record);

    List<ZxSaProjectFinishItem> selectByZxSaProjectFinishItemList(ZxSaProjectFinishItem record);

    int batchDeleteUpdateZxSaProjectFinishItem(List<ZxSaProjectFinishItem> recordList, ZxSaProjectFinishItem record);

}

