package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfAccident;

public interface ZxSfAccidentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfAccident record);

    int insertSelective(ZxSfAccident record);

    ZxSfAccident selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfAccident record);

    int updateByPrimaryKey(ZxSfAccident record);

    List<ZxSfAccident> selectByZxSfAccidentList(ZxSfAccident record);

    int batchDeleteUpdateZxSfAccident(List<ZxSfAccident> recordList, ZxSfAccident record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
