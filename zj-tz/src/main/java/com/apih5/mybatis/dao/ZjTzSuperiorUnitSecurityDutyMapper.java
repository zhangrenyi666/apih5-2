package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitSecurityDuty;

public interface ZjTzSuperiorUnitSecurityDutyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSuperiorUnitSecurityDuty record);

    int insertSelective(ZjTzSuperiorUnitSecurityDuty record);

    ZjTzSuperiorUnitSecurityDuty selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSuperiorUnitSecurityDuty record);

    int updateByPrimaryKey(ZjTzSuperiorUnitSecurityDuty record);

    List<ZjTzSuperiorUnitSecurityDuty> selectByZjTzSuperiorUnitSecurityDutyList(ZjTzSuperiorUnitSecurityDuty record);

    int batchDeleteUpdateZjTzSuperiorUnitSecurityDuty(List<ZjTzSuperiorUnitSecurityDuty> recordList, ZjTzSuperiorUnitSecurityDuty record);

}

