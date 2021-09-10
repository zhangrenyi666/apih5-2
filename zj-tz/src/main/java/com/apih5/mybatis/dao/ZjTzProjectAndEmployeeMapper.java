package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProjectAndEmployee;
import com.apih5.mybatis.pojo.ZjTzProjectEmployee;

public interface ZjTzProjectAndEmployeeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProjectAndEmployee record);

    int insertSelective(ZjTzProjectAndEmployee record);

    ZjTzProjectAndEmployee selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProjectAndEmployee record);

    int updateByPrimaryKey(ZjTzProjectAndEmployee record);

    List<ZjTzProjectAndEmployee> selectByZjTzProjectAndEmployeeList(ZjTzProjectAndEmployee record);

    int batchDeleteUpdateZjTzProjectAndEmployee(List<ZjTzProjectAndEmployee> recordList, ZjTzProjectAndEmployee record);

    int DeleteUpdateZjTzProjectAndEmployee(ZjTzProjectEmployee zjTzProjectEmployee, ZjTzProjectAndEmployee zjTzProjectAndEmployee);

    ZjTzProjectAndEmployee selectByProjectIdAndEmployeeInfoId(ZjTzProjectAndEmployee zjTzProjectAndEmployee);

}

