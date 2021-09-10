package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSupReport;

public interface ZjTzSupReportMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSupReport record);

    int insertSelective(ZjTzSupReport record);

    ZjTzSupReport selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSupReport record);

    int updateByPrimaryKey(ZjTzSupReport record);

    List<ZjTzSupReport> selectByZjTzSupReportList(ZjTzSupReport record);

    int batchDeleteUpdateZjTzSupReport(List<ZjTzSupReport> recordList, ZjTzSupReport record);

}

