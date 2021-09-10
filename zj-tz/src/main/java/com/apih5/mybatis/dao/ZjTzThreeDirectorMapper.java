package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThreeDirector;

public interface ZjTzThreeDirectorMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThreeDirector record);

    int insertSelective(ZjTzThreeDirector record);

    ZjTzThreeDirector selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThreeDirector record);

    int updateByPrimaryKey(ZjTzThreeDirector record);

    List<ZjTzThreeDirector> selectByZjTzThreeDirectorList(ZjTzThreeDirector record);

    int batchDeleteUpdateZjTzThreeDirector(List<ZjTzThreeDirector> recordList, ZjTzThreeDirector record);

	int updateZjTzThreeDirectorProjectShortName(ZjTzThreeDirector director);

}

