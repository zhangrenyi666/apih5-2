package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseJobLevelSub;

public interface BaseJobLevelSubMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseJobLevelSub record);

    int insertSelective(BaseJobLevelSub record);

    BaseJobLevelSub selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseJobLevelSub record);

    int updateByPrimaryKey(BaseJobLevelSub record);

    List<BaseJobLevelSub> selectByBaseJobLevelSubList(BaseJobLevelSub record);

    int batchDeleteUpdateBaseJobLevelSub(List<BaseJobLevelSub> recordList, BaseJobLevelSub record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
