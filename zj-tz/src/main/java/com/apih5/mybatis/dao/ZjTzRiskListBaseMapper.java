package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRiskListBase;

public interface ZjTzRiskListBaseMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRiskListBase record);

    int insertSelective(ZjTzRiskListBase record);

    ZjTzRiskListBase selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRiskListBase record);

    int updateByPrimaryKey(ZjTzRiskListBase record);

    List<ZjTzRiskListBase> selectByZjTzRiskListBaseList(ZjTzRiskListBase record);

    int batchDeleteUpdateZjTzRiskListBase(List<ZjTzRiskListBase> recordList, ZjTzRiskListBase record);

	int batchLockUpdateZjTzRiskListBase(List<ZjTzRiskListBase> zjTzRiskListBaseList, ZjTzRiskListBase zjTzRiskListBase);

	int batchClearUpdateZjTzRiskListBase(List<ZjTzRiskListBase> zjTzRiskListBaseList,
			ZjTzRiskListBase zjTzRiskListBase);

}

