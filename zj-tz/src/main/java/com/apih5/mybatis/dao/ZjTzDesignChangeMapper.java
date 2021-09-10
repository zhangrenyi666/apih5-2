package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDesignChange;

public interface ZjTzDesignChangeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDesignChange record);

    int insertSelective(ZjTzDesignChange record);

    ZjTzDesignChange selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDesignChange record);

    int updateByPrimaryKey(ZjTzDesignChange record);

    List<ZjTzDesignChange> selectByZjTzDesignChangeList(ZjTzDesignChange record);

    int batchDeleteUpdateZjTzDesignChange(List<ZjTzDesignChange> recordList, ZjTzDesignChange record);

}

