package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfHazard;

public interface ZxSfHazardMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfHazard record);

    int insertSelective(ZxSfHazard record);

    ZxSfHazard selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfHazard record);

    int updateByPrimaryKey(ZxSfHazard record);

    List<ZxSfHazard> selectByZxSfHazardList(ZxSfHazard record);

    int batchDeleteUpdateZxSfHazard(List<ZxSfHazard> recordList, ZxSfHazard record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
