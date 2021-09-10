package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzBaseCode;

public interface ZjTzBaseCodeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzBaseCode record);

    int insertSelective(ZjTzBaseCode record);

    ZjTzBaseCode selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzBaseCode record);

    int updateByPrimaryKey(ZjTzBaseCode record);

    List<ZjTzBaseCode> selectByZjTzBaseCodeList(ZjTzBaseCode record);

    int batchDeleteUpdateZjTzBaseCode(List<ZjTzBaseCode> recordList, ZjTzBaseCode record);

	int countBaseCodeListByCodePid(ZjTzBaseCode checkBaseCode);

	int batchUpdateBaseCodePidNameAll(ZjTzBaseCode baseCode1);

	List<ZjTzBaseCode> selectByBaseCodeListByLike(ZjTzBaseCode baseCode);

}

