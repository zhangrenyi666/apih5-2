package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkSporadic;

public interface ZxSkSporadicMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkSporadic record);

    int insertSelective(ZxSkSporadic record);

    ZxSkSporadic selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkSporadic record);

    int updateByPrimaryKey(ZxSkSporadic record);

    List<ZxSkSporadic> selectByZxSkSporadicList(ZxSkSporadic record);

    int batchDeleteUpdateZxSkSporadic(List<ZxSkSporadic> recordList, ZxSkSporadic record);

}

