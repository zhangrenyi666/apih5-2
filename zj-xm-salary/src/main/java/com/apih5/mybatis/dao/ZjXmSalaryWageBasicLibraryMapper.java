package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryWageBasicLibrary;

public interface ZjXmSalaryWageBasicLibraryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmSalaryWageBasicLibrary record);

    int insertSelective(ZjXmSalaryWageBasicLibrary record);

    ZjXmSalaryWageBasicLibrary selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmSalaryWageBasicLibrary record);

    int updateByPrimaryKey(ZjXmSalaryWageBasicLibrary record);

    List<ZjXmSalaryWageBasicLibrary> selectByZjXmSalaryWageBasicLibraryList(ZjXmSalaryWageBasicLibrary record);

    int batchDeleteUpdateZjXmSalaryWageBasicLibrary(List<ZjXmSalaryWageBasicLibrary> recordList, ZjXmSalaryWageBasicLibrary record);

}

