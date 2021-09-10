package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzFile;

public interface ZjTzFileMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzFile record);

    int insertSelective(ZjTzFile record);

    ZjTzFile selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzFile record);

    int updateByPrimaryKey(ZjTzFile record);

    List<ZjTzFile> selectByZjTzFileList(ZjTzFile record);

    int batchDeleteUpdateZjTzFile(List<ZjTzFile> recordList, ZjTzFile record);

}

