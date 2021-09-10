package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitQualityDuty;

public interface ZjTzSuperiorUnitQualityDutyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSuperiorUnitQualityDuty record);

    int insertSelective(ZjTzSuperiorUnitQualityDuty record);

    ZjTzSuperiorUnitQualityDuty selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSuperiorUnitQualityDuty record);

    int updateByPrimaryKey(ZjTzSuperiorUnitQualityDuty record);

    List<ZjTzSuperiorUnitQualityDuty> selectByZjTzSuperiorUnitQualityDutyList(ZjTzSuperiorUnitQualityDuty record);

    int batchDeleteUpdateZjTzSuperiorUnitQualityDuty(List<ZjTzSuperiorUnitQualityDuty> recordList, ZjTzSuperiorUnitQualityDuty record);

}

