package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEWork;

public interface ZxEqEWorkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEWork record);

    int insertSelective(ZxEqEWork record);

    ZxEqEWork selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEWork record);

    int updateByPrimaryKey(ZxEqEWork record);

    List<ZxEqEWork> selectByZxEqEWorkList(ZxEqEWork record);

    int batchDeleteUpdateZxEqEWork(List<ZxEqEWork> recordList, ZxEqEWork record);

	List<ZxEqEWork> getZxEqEWorkListForReport(ZxEqEWork zxEqEWork);

}

