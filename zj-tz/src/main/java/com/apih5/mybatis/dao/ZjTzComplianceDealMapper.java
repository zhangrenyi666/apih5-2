package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzComplianceDeal;

public interface ZjTzComplianceDealMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzComplianceDeal record);

    int insertSelective(ZjTzComplianceDeal record);

    ZjTzComplianceDeal selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzComplianceDeal record);

    int updateByPrimaryKey(ZjTzComplianceDeal record);

    List<ZjTzComplianceDeal> selectByZjTzComplianceDealList(ZjTzComplianceDeal record);

    int batchDeleteUpdateZjTzComplianceDeal(List<ZjTzComplianceDeal> recordList, ZjTzComplianceDeal record);

	int updateZjTzComplianceDealProjectShortName(ZjTzComplianceDeal complianceDeal);

}

