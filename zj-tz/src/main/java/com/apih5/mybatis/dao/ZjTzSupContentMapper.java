package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSupContent;

public interface ZjTzSupContentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSupContent record);

    int insertSelective(ZjTzSupContent record);

    ZjTzSupContent selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSupContent record);

    int updateByPrimaryKey(ZjTzSupContent record);

    List<ZjTzSupContent> selectByZjTzSupContentList(ZjTzSupContent record);

    int batchDeleteUpdateZjTzSupContent(List<ZjTzSupContent> recordList, ZjTzSupContent record);

}

