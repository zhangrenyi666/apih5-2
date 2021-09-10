package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrApplyWorks;

public interface ZxCtContrApplyWorksMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrApplyWorks record);

    int insertSelective(ZxCtContrApplyWorks record);

    ZxCtContrApplyWorks selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrApplyWorks record);

    int updateByPrimaryKey(ZxCtContrApplyWorks record);

    List<ZxCtContrApplyWorks> selectByZxCtContrApplyWorksList(ZxCtContrApplyWorks record);

    int batchDeleteUpdateZxCtContrApplyWorks(List<ZxCtContrApplyWorks> recordList, ZxCtContrApplyWorks record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
