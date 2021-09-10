package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfEAccident;

public interface ZxSfEAccidentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfEAccident record);

    int insertSelective(ZxSfEAccident record);

    ZxSfEAccident selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfEAccident record);

    int updateByPrimaryKey(ZxSfEAccident record);

    List<ZxSfEAccident> selectByZxSfEAccidentList(ZxSfEAccident record);

    int batchDeleteUpdateZxSfEAccident(List<ZxSfEAccident> recordList, ZxSfEAccident record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
