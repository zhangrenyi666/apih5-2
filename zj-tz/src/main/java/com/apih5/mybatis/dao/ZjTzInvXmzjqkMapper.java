package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzInvXmzjqk;

public interface ZjTzInvXmzjqkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzInvXmzjqk record);

    int insertSelective(ZjTzInvXmzjqk record);

    ZjTzInvXmzjqk selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzInvXmzjqk record);

    int updateByPrimaryKey(ZjTzInvXmzjqk record);

    List<ZjTzInvXmzjqk> selectByZjTzInvXmzjqkList(ZjTzInvXmzjqk record);

    int batchDeleteUpdateZjTzInvXmzjqk(List<ZjTzInvXmzjqk> recordList, ZjTzInvXmzjqk record);
    
    List<ZjTzInvXmzjqk> selectZjTzInvXmzjqkMonthlyReportListByPeriodValue(ZjTzInvXmzjqk record, List<String> periodList);
    
    List<ZjTzInvXmzjqk> selectZjTzInvXmzjqkMonthlyReportListBasicData(ZjTzInvXmzjqk record);

    List<ZjTzInvXmzjqk> selectHomeChartCapitalStatus(ZjTzInvXmzjqk record);
    
    ZjTzInvXmzjqk selectConstructPageCapital(ZjTzInvXmzjqk record);
    //项目页面资金占用预警
  	ZjTzInvXmzjqk selectProjectPageCapitalWarning(ZjTzInvXmzjqk zjTzInvXmzjqk);
}

