package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzCompSupContent;

public interface ZjTzCompSupContentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzCompSupContent record);

    int insertSelective(ZjTzCompSupContent record);

    ZjTzCompSupContent selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzCompSupContent record);

    int updateByPrimaryKey(ZjTzCompSupContent record);

    List<ZjTzCompSupContent> selectByZjTzCompSupContentList(ZjTzCompSupContent record);

    int batchDeleteUpdateZjTzCompSupContent(List<ZjTzCompSupContent> recordList, ZjTzCompSupContent record);

}

