package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantRange;

public interface ZjXmCqjxAssistantRangeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxAssistantRange record);

    int insertSelective(ZjXmCqjxAssistantRange record);

    ZjXmCqjxAssistantRange selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxAssistantRange record);

    int updateByPrimaryKey(ZjXmCqjxAssistantRange record);

    List<ZjXmCqjxAssistantRange> selectByZjXmCqjxAssistantRangeList(ZjXmCqjxAssistantRange record);

    int batchDeleteUpdateZjXmCqjxAssistantRange(List<ZjXmCqjxAssistantRange> recordList, ZjXmCqjxAssistantRange record);

}

