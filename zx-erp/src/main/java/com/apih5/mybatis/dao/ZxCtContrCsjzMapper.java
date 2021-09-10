package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrCsjz;

public interface ZxCtContrCsjzMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrCsjz record);

    int insertSelective(ZxCtContrCsjz record);

    ZxCtContrCsjz selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrCsjz record);

    int updateByPrimaryKey(ZxCtContrCsjz record);

    List<ZxCtContrCsjz> selectByZxCtContrCsjzList(ZxCtContrCsjz record);

    int batchDeleteUpdateZxCtContrCsjz(List<ZxCtContrCsjz> recordList, ZxCtContrCsjz record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
