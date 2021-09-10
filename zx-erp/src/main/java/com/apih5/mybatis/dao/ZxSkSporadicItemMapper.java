package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkSporadicItem;

public interface ZxSkSporadicItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkSporadicItem record);

    int insertSelective(ZxSkSporadicItem record);

    ZxSkSporadicItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkSporadicItem record);

    int updateByPrimaryKey(ZxSkSporadicItem record);

    List<ZxSkSporadicItem> selectByZxSkSporadicItemList(ZxSkSporadicItem record);

    int batchDeleteUpdateZxSkSporadicItem(List<ZxSkSporadicItem> recordList, ZxSkSporadicItem record);

}

