package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCcConstCoAlter;

public interface ZxCcConstCoAlterMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCcConstCoAlter record);

    int insertSelective(ZxCcConstCoAlter record);

    ZxCcConstCoAlter selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCcConstCoAlter record);

    int updateByPrimaryKey(ZxCcConstCoAlter record);

    List<ZxCcConstCoAlter> selectByZxCcConstCoAlterList(ZxCcConstCoAlter record);

    int batchDeleteUpdateZxCcConstCoAlter(List<ZxCcConstCoAlter> recordList, ZxCcConstCoAlter record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
