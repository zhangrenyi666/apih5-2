package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEemployee;

public interface ZxEqEemployeeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEemployee record);

    int insertSelective(ZxEqEemployee record);

    ZxEqEemployee selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEemployee record);

    int updateByPrimaryKey(ZxEqEemployee record);

    List<ZxEqEemployee> selectByZxEqEemployeeList(ZxEqEemployee record);

    int batchDeleteUpdateZxEqEemployee(List<ZxEqEemployee> recordList, ZxEqEemployee record);

    List<ZxEqEemployee> getZxEqEemployeeListForReport(ZxEqEemployee zxEqEemployee);
}

