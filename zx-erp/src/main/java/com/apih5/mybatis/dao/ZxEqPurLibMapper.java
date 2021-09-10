package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqPurLib;

public interface ZxEqPurLibMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqPurLib record);

    int insertSelective(ZxEqPurLib record);

    ZxEqPurLib selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqPurLib record);

    int updateByPrimaryKey(ZxEqPurLib record);

    List<ZxEqPurLib> selectByZxEqPurLibList(ZxEqPurLib record);

    int batchDeleteUpdateZxEqPurLib(List<ZxEqPurLib> recordList, ZxEqPurLib record);

}

