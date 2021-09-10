package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzVideoHistory;

public interface ZjTzVideoHistoryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzVideoHistory record);

    int insertSelective(ZjTzVideoHistory record);

    ZjTzVideoHistory selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzVideoHistory record);

    int updateByPrimaryKey(ZjTzVideoHistory record);

    List<ZjTzVideoHistory> selectByZjTzVideoHistoryList(ZjTzVideoHistory record);

    int batchDeleteUpdateZjTzVideoHistory(List<ZjTzVideoHistory> recordList, ZjTzVideoHistory record);
    
    ZjTzVideoHistory selectByDepartmentId(String departmentId);
}

