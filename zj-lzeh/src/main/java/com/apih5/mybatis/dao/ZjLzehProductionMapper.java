package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehProduction;

public interface ZjLzehProductionMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehProduction record);

    int insertSelective(ZjLzehProduction record);

    ZjLzehProduction selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehProduction record);

    int updateByPrimaryKey(ZjLzehProduction record);

    List<ZjLzehProduction> selectByZjLzehProductionList(ZjLzehProduction record);

    int batchDeleteUpdateZjLzehProduction(List<ZjLzehProduction> recordList, ZjLzehProduction record);
    
}

