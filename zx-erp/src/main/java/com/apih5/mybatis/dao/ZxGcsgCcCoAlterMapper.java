package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlter;

public interface ZxGcsgCcCoAlterMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxGcsgCcCoAlter record);

    int insertSelective(ZxGcsgCcCoAlter record);

    ZxGcsgCcCoAlter selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxGcsgCcCoAlter record);

    int updateByPrimaryKey(ZxGcsgCcCoAlter record);

    List<ZxGcsgCcCoAlter> selectByZxGcsgCcCoAlterList(ZxGcsgCcCoAlter record);

    int batchDeleteUpdateZxGcsgCcCoAlter(List<ZxGcsgCcCoAlter> recordList, ZxGcsgCcCoAlter record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
