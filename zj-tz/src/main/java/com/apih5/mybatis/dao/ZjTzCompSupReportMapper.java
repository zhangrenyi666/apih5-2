package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzCompSupReport;

public interface ZjTzCompSupReportMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzCompSupReport record);

    int insertSelective(ZjTzCompSupReport record);

    ZjTzCompSupReport selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzCompSupReport record);

    int updateByPrimaryKey(ZjTzCompSupReport record);

    List<ZjTzCompSupReport> selectByZjTzCompSupReportList(ZjTzCompSupReport record);

    int batchDeleteUpdateZjTzCompSupReport(List<ZjTzCompSupReport> recordList, ZjTzCompSupReport record);

	int batchRecallZjTzCompSupReport(List<ZjTzCompSupReport> zjTzCompSupReportList, ZjTzCompSupReport zjTzRules);

}

