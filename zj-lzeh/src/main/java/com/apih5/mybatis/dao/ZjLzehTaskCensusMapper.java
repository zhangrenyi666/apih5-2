package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTaskCensus;

public interface ZjLzehTaskCensusMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTaskCensus record);

    int insertSelective(ZjLzehTaskCensus record);

    ZjLzehTaskCensus selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTaskCensus record);

    int updateByPrimaryKey(ZjLzehTaskCensus record);

    List<ZjLzehTaskCensus> selectByZjLzehTaskCensusList(ZjLzehTaskCensus record);

    int batchDeleteUpdateZjLzehTaskCensus(List<ZjLzehTaskCensus> recordList, ZjLzehTaskCensus record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
