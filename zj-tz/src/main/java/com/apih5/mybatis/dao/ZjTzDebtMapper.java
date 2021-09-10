package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDebt;

public interface ZjTzDebtMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDebt record);

    int insertSelective(ZjTzDebt record);

    ZjTzDebt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDebt record);

    int updateByPrimaryKey(ZjTzDebt record);

    List<ZjTzDebt> selectByZjTzDebtList(ZjTzDebt record);

    int batchDeleteUpdateZjTzDebt(List<ZjTzDebt> recordList, ZjTzDebt record);

	int updateZjTzDebtProjectShortName(ZjTzDebt debt);

}

