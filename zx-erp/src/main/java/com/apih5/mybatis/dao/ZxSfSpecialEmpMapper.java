package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfSpecialEmp;

public interface ZxSfSpecialEmpMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfSpecialEmp record);

    int insertSelective(ZxSfSpecialEmp record);

    ZxSfSpecialEmp selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfSpecialEmp record);

    int updateByPrimaryKey(ZxSfSpecialEmp record);

    List<ZxSfSpecialEmp> selectByZxSfSpecialEmpList(ZxSfSpecialEmp record);

    int batchDeleteUpdateZxSfSpecialEmp(List<ZxSfSpecialEmp> recordList, ZxSfSpecialEmp record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
