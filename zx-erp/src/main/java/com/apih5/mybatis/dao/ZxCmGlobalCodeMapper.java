package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCmGlobalCode;

public interface ZxCmGlobalCodeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCmGlobalCode record);

    int insertSelective(ZxCmGlobalCode record);

    ZxCmGlobalCode selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCmGlobalCode record);

    int updateByPrimaryKey(ZxCmGlobalCode record);

    List<ZxCmGlobalCode> selectByZxCmGlobalCodeList(ZxCmGlobalCode record);

    int batchDeleteUpdateZxCmGlobalCode(List<ZxCmGlobalCode> recordList, ZxCmGlobalCode record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
