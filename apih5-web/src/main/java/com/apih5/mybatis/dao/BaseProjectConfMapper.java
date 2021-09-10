package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseProjectConf;

public interface BaseProjectConfMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseProjectConf record);

    int insertSelective(BaseProjectConf record);

    BaseProjectConf selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseProjectConf record);

    int updateByPrimaryKey(BaseProjectConf record);

    List<BaseProjectConf> selectByBaseProjectConfList(BaseProjectConf record);

    int batchDeleteUpdateBaseProjectConf(List<BaseProjectConf> recordList, BaseProjectConf record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
