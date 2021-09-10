package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzEmployeeLanguage;

public interface ZjTzEmployeeLanguageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzEmployeeLanguage record);

    int insertSelective(ZjTzEmployeeLanguage record);

    ZjTzEmployeeLanguage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzEmployeeLanguage record);

    int updateByPrimaryKey(ZjTzEmployeeLanguage record);

    List<ZjTzEmployeeLanguage> selectByZjTzEmployeeLanguageList(ZjTzEmployeeLanguage record);

    int batchDeleteUpdateZjTzEmployeeLanguage(List<ZjTzEmployeeLanguage> recordList, ZjTzEmployeeLanguage record);

}

