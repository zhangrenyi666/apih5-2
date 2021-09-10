package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRunScheme;

public interface ZjTzRunSchemeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRunScheme record);

    int insertSelective(ZjTzRunScheme record);

    ZjTzRunScheme selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRunScheme record);

    int updateByPrimaryKey(ZjTzRunScheme record);

    List<ZjTzRunScheme> selectByZjTzRunSchemeList(ZjTzRunScheme record);

    int batchDeleteUpdateZjTzRunScheme(List<ZjTzRunScheme> recordList, ZjTzRunScheme record);

	int selectRunSchemeWorkListCount(ZjTzRunScheme cycle);

	int updateZjTzRunSchemeProjectShortName(ZjTzRunScheme runScheme);


}

