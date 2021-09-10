package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtOther;

public interface ZxCtOtherMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtOther record);

    int insertSelective(ZxCtOther record);

    ZxCtOther selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtOther record);

    int updateByPrimaryKey(ZxCtOther record);

    List<ZxCtOther> selectByZxCtOtherList(ZxCtOther record);

    int batchDeleteUpdateZxCtOther(List<ZxCtOther> recordList, ZxCtOther record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxCtOther> selectByContractCodeAndCategory (ZxCtOther record);
}
