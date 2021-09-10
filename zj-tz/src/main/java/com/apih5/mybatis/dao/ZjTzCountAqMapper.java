package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzCountAq;

public interface ZjTzCountAqMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzCountAq record);

    int insertSelective(ZjTzCountAq record);

    ZjTzCountAq selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzCountAq record);

    int updateByPrimaryKey(ZjTzCountAq record);

    List<ZjTzCountAq> selectByZjTzCountAqList(ZjTzCountAq record);

    int batchDeleteUpdateZjTzCountAq(List<ZjTzCountAq> recordList, ZjTzCountAq record);

}

