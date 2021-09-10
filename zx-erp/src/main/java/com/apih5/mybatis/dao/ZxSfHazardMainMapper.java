package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfHazardMain;

public interface ZxSfHazardMainMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfHazardMain record);

    int insertSelective(ZxSfHazardMain record);

    ZxSfHazardMain selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfHazardMain record);

    int updateByPrimaryKey(ZxSfHazardMain record);

    List<ZxSfHazardMain> selectByZxSfHazardMainList(ZxSfHazardMain record);

    int batchDeleteUpdateZxSfHazardMain(List<ZxSfHazardMain> recordList, ZxSfHazardMain record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
