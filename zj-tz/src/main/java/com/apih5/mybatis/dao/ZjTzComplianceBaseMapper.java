package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzComplianceBase;

public interface ZjTzComplianceBaseMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzComplianceBase record);

    int insertSelective(ZjTzComplianceBase record);

    ZjTzComplianceBase selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzComplianceBase record);

    int updateByPrimaryKey(ZjTzComplianceBase record);

    List<ZjTzComplianceBase> selectByZjTzComplianceBaseList(ZjTzComplianceBase record);

    int batchDeleteUpdateZjTzComplianceBase(List<ZjTzComplianceBase> recordList, ZjTzComplianceBase record);

}

