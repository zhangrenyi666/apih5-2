package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrDetailAttr;

public interface ZxCtContrDetailAttrMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrDetailAttr record);

    int insertSelective(ZxCtContrDetailAttr record);

    ZxCtContrDetailAttr selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrDetailAttr record);

    int updateByPrimaryKey(ZxCtContrDetailAttr record);

    List<ZxCtContrDetailAttr> selectByZxCtContrDetailAttrList(ZxCtContrDetailAttr record);

    int batchDeleteUpdateZxCtContrDetailAttr(List<ZxCtContrDetailAttr> recordList, ZxCtContrDetailAttr record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int deleteAllZxCtContrDetailAttr(ZxCtContrDetailAttr zxCtContrDetailAttr);
}
