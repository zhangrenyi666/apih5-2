package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehFile;

public interface ZjLzehFileMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehFile record);

    int insertSelective(ZjLzehFile record);

    ZjLzehFile selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehFile record);

    int updateByPrimaryKey(ZjLzehFile record);

    List<ZjLzehFile> selectByZjLzehFileList(ZjLzehFile record);

    int batchDeleteUpdateZjLzehFile(List<ZjLzehFile> recordList, ZjLzehFile record);




}

