package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxErpFile;

public interface ZxErpFileMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxErpFile record);

    int insertSelective(ZxErpFile record);

    ZxErpFile selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxErpFile record);

    int updateByPrimaryKey(ZxErpFile record);

    List<ZxErpFile> selectByZxErpFileList(ZxErpFile record);

    int batchDeleteUpdateZxErpFile(List<ZxErpFile> recordList, ZxErpFile record);

	int deleteAllZxErpFile(ZxErpFile zxErpFile);

}

