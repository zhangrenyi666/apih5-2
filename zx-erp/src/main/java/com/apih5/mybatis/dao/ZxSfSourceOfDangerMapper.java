package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfSourceOfDanger;

public interface ZxSfSourceOfDangerMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfSourceOfDanger record);

    int insertSelective(ZxSfSourceOfDanger record);

    ZxSfSourceOfDanger selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfSourceOfDanger record);

    int updateByPrimaryKey(ZxSfSourceOfDanger record);

    List<ZxSfSourceOfDanger> selectByZxSfSourceOfDangerList(ZxSfSourceOfDanger record);

    int batchDeleteUpdateZxSfSourceOfDanger(List<ZxSfSourceOfDanger> recordList, ZxSfSourceOfDanger record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
