package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRunFile;

public interface ZjTzRunFileMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRunFile record);

    int insertSelective(ZjTzRunFile record);

    ZjTzRunFile selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRunFile record);

    int updateByPrimaryKey(ZjTzRunFile record);

    List<ZjTzRunFile> selectByZjTzRunFileList(ZjTzRunFile record);

    int batchDeleteUpdateZjTzRunFile(List<ZjTzRunFile> recordList, ZjTzRunFile record);

	int updateZjTzRunFileProjectShortName(ZjTzRunFile runFile);

}

