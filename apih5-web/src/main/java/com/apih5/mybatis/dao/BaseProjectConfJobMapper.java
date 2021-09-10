package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseProjectConfJob;

public interface BaseProjectConfJobMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseProjectConfJob record);

    int insertSelective(BaseProjectConfJob record);

    BaseProjectConfJob selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseProjectConfJob record);

    int updateByPrimaryKey(BaseProjectConfJob record);

    List<BaseProjectConfJob> selectByBaseProjectConfJobList(BaseProjectConfJob record);

    int batchDeleteUpdateBaseProjectConfJob(List<BaseProjectConfJob> recordList, BaseProjectConfJob record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
