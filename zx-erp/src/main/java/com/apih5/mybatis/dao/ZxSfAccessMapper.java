package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfAccess;

public interface ZxSfAccessMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfAccess record);

    int insertSelective(ZxSfAccess record);

    ZxSfAccess selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfAccess record);

    int updateByPrimaryKey(ZxSfAccess record);

    List<ZxSfAccess> selectByZxSfAccessList(ZxSfAccess record);

    int batchDeleteUpdateZxSfAccess(List<ZxSfAccess> recordList, ZxSfAccess record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
}
