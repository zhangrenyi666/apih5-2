package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfJuExam;

public interface ZxSfJuExamMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfJuExam record);

    int insertSelective(ZxSfJuExam record);

    ZxSfJuExam selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfJuExam record);

    int updateByPrimaryKey(ZxSfJuExam record);

    List<ZxSfJuExam> selectByZxSfJuExamList(ZxSfJuExam record);

    int batchDeleteUpdateZxSfJuExam(List<ZxSfJuExam> recordList, ZxSfJuExam record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
