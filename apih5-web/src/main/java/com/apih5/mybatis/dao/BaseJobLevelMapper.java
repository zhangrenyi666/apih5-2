package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseJobLevel;

public interface BaseJobLevelMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseJobLevel record);

    int insertSelective(BaseJobLevel record);

    BaseJobLevel selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseJobLevel record);

    int updateByPrimaryKey(BaseJobLevel record);

    List<BaseJobLevel> selectByBaseJobLevelList(BaseJobLevel record);

    int batchDeleteUpdateBaseJobLevel(List<BaseJobLevel> recordList, BaseJobLevel record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
