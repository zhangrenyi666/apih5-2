package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRunSchemeOpinion;

public interface ZjTzRunSchemeOpinionMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRunSchemeOpinion record);

    int insertSelective(ZjTzRunSchemeOpinion record);

    ZjTzRunSchemeOpinion selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRunSchemeOpinion record);

    int updateByPrimaryKey(ZjTzRunSchemeOpinion record);

    List<ZjTzRunSchemeOpinion> selectByZjTzRunSchemeOpinionList(ZjTzRunSchemeOpinion record);

    int batchDeleteUpdateZjTzRunSchemeOpinion(List<ZjTzRunSchemeOpinion> recordList, ZjTzRunSchemeOpinion record);

}

