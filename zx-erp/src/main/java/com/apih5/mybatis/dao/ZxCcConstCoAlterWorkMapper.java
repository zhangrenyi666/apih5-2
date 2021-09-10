package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCcConstCoAlterWork;

public interface ZxCcConstCoAlterWorkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCcConstCoAlterWork record);

    int insertSelective(ZxCcConstCoAlterWork record);

    ZxCcConstCoAlterWork selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCcConstCoAlterWork record);

    int updateByPrimaryKey(ZxCcConstCoAlterWork record);

    List<ZxCcConstCoAlterWork> selectByZxCcConstCoAlterWorkList(ZxCcConstCoAlterWork record);

    int batchDeleteUpdateZxCcConstCoAlterWork(List<ZxCcConstCoAlterWork> recordList, ZxCcConstCoAlterWork record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
