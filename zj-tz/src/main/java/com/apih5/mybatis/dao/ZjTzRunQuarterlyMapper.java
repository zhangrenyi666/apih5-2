package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRunQuarterly;

public interface ZjTzRunQuarterlyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRunQuarterly record);

    int insertSelective(ZjTzRunQuarterly record);

    ZjTzRunQuarterly selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRunQuarterly record);

    int updateByPrimaryKey(ZjTzRunQuarterly record);

    List<ZjTzRunQuarterly> selectByZjTzRunQuarterlyList(ZjTzRunQuarterly record);

    int batchDeleteUpdateZjTzRunQuarterly(List<ZjTzRunQuarterly> recordList, ZjTzRunQuarterly record);

}

