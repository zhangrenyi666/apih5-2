package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfOtherAddFilePress;

public interface ZxSfOtherAddFilePressMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfOtherAddFilePress record);

    int insertSelective(ZxSfOtherAddFilePress record);

    ZxSfOtherAddFilePress selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfOtherAddFilePress record);

    int updateByPrimaryKey(ZxSfOtherAddFilePress record);

    List<ZxSfOtherAddFilePress> selectByZxSfOtherAddFilePressList(ZxSfOtherAddFilePress record);

    int batchDeleteUpdateZxSfOtherAddFilePress(List<ZxSfOtherAddFilePress> recordList, ZxSfOtherAddFilePress record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
